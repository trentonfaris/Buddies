package me.Man_cub.Buddies.protocol;

import gnu.trove.set.TIntSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.data.configuration.BuddiesConfig;
import me.Man_cub.Buddies.event.scoreboard.ObjectiveActionEvent;
import me.Man_cub.Buddies.event.scoreboard.ObjectiveDisplayEvent;
import me.Man_cub.Buddies.event.scoreboard.ScoreUpdateEvent;
import me.Man_cub.Buddies.event.scoreboard.TeamActionEvent;
import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.protocol.container.BuddiesContainer;
import me.Man_cub.Buddies.protocol.message.BuddiesBlockDataChannelMessage;
import me.Man_cub.Buddies.protocol.message.player.position.PlayerPositionLookMessage;
import me.Man_cub.Buddies.protocol.message.scoreboard.ScoreboardDisplayMessage;
import me.Man_cub.Buddies.protocol.message.scoreboard.ScoreboardObjectiveMessage;
import me.Man_cub.Buddies.protocol.message.scoreboard.ScoreboardScoreMessage;
import me.Man_cub.Buddies.protocol.message.scoreboard.ScoreboardTeamMessage;
import me.Man_cub.Buddies.protocol.message.world.block.BlockChangeMessage;
import me.Man_cub.Buddies.protocol.message.world.chunk.ChunkDataMessage;
import me.Man_cub.Buddies.protocol.reposition.BuddiesRepositionManager;
import me.Man_cub.Buddies.scoreboard.Objective;
import me.Man_cub.Buddies.scoreboard.Team;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiome;
import me.Man_cub.Buddies.world.lighting.BuddiesCuboidLightBuffer;
import me.Man_cub.Buddies.world.lighting.BuddiesLighting;

import org.spout.api.entity.Entity;
import org.spout.api.event.EventHandler;
import org.spout.api.generator.biome.Biome;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.material.BlockMaterial;
import org.spout.api.math.IntVector3;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.EntityProtocol;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.NetworkSynchronizer;
import org.spout.api.protocol.Session;
import org.spout.api.protocol.event.ProtocolEvent;
import org.spout.api.protocol.event.ProtocolEventListener;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.FlatIterator;
import org.spout.api.util.hashing.IntPairHashed;
import org.spout.api.util.map.concurrent.TSyncIntPairObjectHashMap;
import org.spout.api.util.set.concurrent.TSyncIntHashSet;
import org.spout.api.util.set.concurrent.TSyncIntPairHashSet;

import static me.Man_cub.Buddies.material.BuddiesMaterials.getBuddiesId;

public class BuddiesNetworkSynchronizer extends NetworkSynchronizer implements ProtocolEventListener {
	private static final int SOLID_BLOCK_ID = 1; //Initializer block ID
	private static final byte[] SOLID_CHUNK_DATA = new byte[Chunk.BLOCKS.HALF_VOLUME * 5];
	private static final byte[] AIR_CHUNK_DATA = new byte[Chunk.BLOCKS.HALF_VOLUME * 5];
	private static final double STANCE = 1.62001D;
	private static final int FORCE_MASK = 0xFF; //force an update to be sent every 5 seconds
	private static final int HASH_SEED = 0xB346D76A;
	public static final int WORLD_HEIGHT = 128;
	private final TSyncIntPairObjectHashMap<TSyncIntHashSet> initializedChunks = new TSyncIntPairObjectHashMap<TSyncIntHashSet>();
	private final ConcurrentLinkedQueue<Long> emptyColumns = new ConcurrentLinkedQueue<Long>();
	private final TSyncIntPairHashSet activeChunks = new TSyncIntPairHashSet();
	private final Object initChunkLock = new Object();
	private final ChunkInit chunkInit;
	private int minY = 0; //Height
	private int maxY = 128;
	private int stepY = 80;
	private int offsetY = 0;
	private final BuddiesRepositionManager bpm = new BuddiesRepositionManager();
	
	static {
		int i = 0;
		for (int c = 0; c < Chunk.BLOCKS.VOLUME; c++) { // blocks
			SOLID_CHUNK_DATA[i] = SOLID_BLOCK_ID;
			AIR_CHUNK_DATA[i++] = 0;
		}
		for (int c = 0; c < Chunk.BLOCKS.HALF_VOLUME; c++) { // block data
			SOLID_CHUNK_DATA[i] = 0x00;
			AIR_CHUNK_DATA[i++] = 0x00;
		}
		for (int c = 0; c < Chunk.BLOCKS.HALF_VOLUME; c++) { // block light
			SOLID_CHUNK_DATA[i] = 0x00;
			AIR_CHUNK_DATA[i++] = 0x00;
		}
		for (int c = 0; c < Chunk.BLOCKS.HALF_VOLUME; c++) { // sky light
			SOLID_CHUNK_DATA[i] = 0x00;
			AIR_CHUNK_DATA[i++] = (byte) 0xFF;
		}
	}
	
	public BuddiesNetworkSynchronizer(Session session) {
		//The minimum block distance is a radius for sending chunks before login/respawn
		//It needs to be > 0 for reliable login and preventing falling through the world
		super(session, 2);
		registerProtocolEvents(this);
		chunkInit = ChunkInit.getChunkInit(BuddiesConfig.CHUNK_INIT.getString("client"));
		setRepositionManager(bpm);
	}
	
	@Override
	protected void freeChunk(Point p) {
		int x = (int) p.getX() >> Chunk.BLOCKS.BITS;
		int y = (int) p.getY() >> Chunk.BLOCKS.BITS;
		int z = (int) p.getZ() >> Chunk.BLOCKS.BITS;
		
		RepositionManager rm = getRepositionManager();
		
		int cY = rm.convertChunkY(y);
		
		if (cY < 0 || cY >= WORLD_HEIGHT >> Chunk.BLOCKS.BITS) {
			return;
		}
		
		TIntSet column = initializedChunks.get(x, z);
		if (column != null) {
			column.remove(y);
			if (column.isEmpty()) {
				emptyColumns.add(IntPairHashed.key(x, z));
			}
		}
	}
	
	@Override
	protected void initChunk(Point p) {
	}
	
	private void initChunkRaw(Point p) {
		int x = p.getChunkX();
		int y = p.getChunkY();
		int z = p.getChunkZ();
		
		RepositionManager rm = getRepositionManager();
		
		int cY = rm.convertChunkY(y);
		
		if (cY < 0 || cY >= WORLD_HEIGHT >> Chunk.BLOCKS.BITS) {
			return;
		}
		
		TSyncIntHashSet column = initializedChunks.get(x, z);
		if (column == null) {
			column = new TSyncIntHashSet();
			synchronized (initChunkLock) {
				TSyncIntHashSet oldColumn = initializedChunks.putIfAbsent(x, z, column);
				if (oldColumn != null) {
					column = oldColumn;
				}
			}
		}
		column.add(y);
	}
	
	private static BlockMaterial[][] getColumnTopmostMaterials(Point p) {
		BlockMaterial[][] materials = new BlockMaterial[Chunk.BLOCKS.SIZE][Chunk.BLOCKS.SIZE];

		World w = p.getWorld();

		for (int xx = 0; xx < Chunk.BLOCKS.SIZE; xx++) {
			for (int zz = 0; zz < Chunk.BLOCKS.SIZE; zz++) {
				materials[xx][zz] = w.getTopmostBlock(p.getBlockX() + xx, p.getBlockZ() + zz, LoadOption.LOAD_GEN);
			}
		}
		return materials;
	}

	private static int[][] getColumnHeights(Point p) {
		int[][] heights = new int[Chunk.BLOCKS.SIZE][Chunk.BLOCKS.SIZE];

		World w = p.getWorld();

		for (int xx = 0; xx < Chunk.BLOCKS.SIZE; xx++) {
			for (int zz = 0; zz < Chunk.BLOCKS.SIZE; zz++) {
				heights[xx][zz] = w.getSurfaceHeight(p.getBlockX() + xx, p.getBlockZ() + zz, LoadOption.LOAD_GEN);
			}
		}
		return heights;
	}

	private static final byte[] emptySkyChunkData;
	private static final byte[] emptyGroundChunkData;

	static {
		emptySkyChunkData = new byte[Chunk.BLOCKS.HALF_VOLUME * 5];
		emptyGroundChunkData = new byte[Chunk.BLOCKS.HALF_VOLUME * 5];

		int j = Chunk.BLOCKS.VOLUME << 1;
		// Sky light = F
		for (int i = 0; i < Chunk.BLOCKS.HALF_VOLUME; i++) {
			emptySkyChunkData[j] = (byte) 0xFF;
		}
	}

	@Override
	protected boolean canSendChunk(Chunk c) {
		if (activeChunks.contains(c.getX(), c.getZ())) {
			return true;
		}
		Collection<Chunk> chunks = chunkInit.getChunks(c);
		if (chunks == null) {
			return false;
		}
		return true;
	}

	@Override
	protected Collection<Chunk> sendChunk(Chunk c, boolean force) {

		if (!force) {
			return sendChunk(c);
		}

		int x = c.getX();
		int y = c.getY();// + SEALEVEL_CHUNK;
		int z = c.getZ();

		RepositionManager rm = getRepositionManager();

		int cY = rm.convertChunkY(y);
		
		if (cY < 0 || cY >= WORLD_HEIGHT >> Chunk.BLOCKS.BITS) {
			return null;
		}

		initChunkRaw(c.getBase());

		Collection<Chunk> chunks = null;

		List<ProtocolEvent> events = new ArrayList<ProtocolEvent>();

		if (activeChunks.add(x, z)) {
			Point p = c.getBase();
			int[][] heights = getColumnHeights(p);
			BlockMaterial[][] materials = getColumnTopmostMaterials(p);

			byte[][] packetChunkData = new byte[16][];

			for (int cube = 0; cube < 16; cube++) {
				int serverCube = rm.getInverse().convertChunkY(cube);
				Point pp = new Point(c.getWorld(), x << Chunk.BLOCKS.BITS, serverCube << Chunk.BLOCKS.BITS, z << Chunk.BLOCKS.BITS);
				packetChunkData[cube] = chunkInit.getChunkData(heights, materials, pp, events);
			}

			Chunk chunk = p.getWorld().getChunkFromBlock(p, LoadOption.LOAD_ONLY);
			byte[] biomeData = new byte[Chunk.BLOCKS.AREA];
			for (int dx = x; dx < x + Chunk.BLOCKS.SIZE; ++dx) {
				for (int dz = z; dz < z + Chunk.BLOCKS.SIZE; ++dz) {
					Biome biome = chunk.getBiome(dx & Chunk.BLOCKS.MASK, 0, dz & Chunk.BLOCKS.MASK);
					if (biome instanceof BuddiesBiome) {
						biomeData[(dz & Chunk.BLOCKS.MASK) << 4 | (dx & Chunk.BLOCKS.MASK)] = (byte) ((BuddiesBiome) biome).getBiomeId();
					}
				}
			}

			ChunkDataMessage CCMsg = new ChunkDataMessage(x, z, true, new boolean[16], packetChunkData, biomeData, player.getSession(), getRepositionManager());
			player.getSession().send(false, CCMsg);

			chunks = chunkInit.getChunks(c);
		}

		if (chunks == null || !chunks.contains(c)) {

			byte[] fullChunkData = ChunkInit.getChunkFullData(c, events);

			byte[][] packetChunkData = new byte[16][];
			packetChunkData[cY] = fullChunkData;
			ChunkDataMessage CCMsg = new ChunkDataMessage(x, z, false, new boolean[16], packetChunkData, null, player.getSession(), getRepositionManager());
			player.getSession().send(false, CCMsg);

			if (chunks == null) {
				chunks = new ArrayList<Chunk>(1);
			}
			chunks.add(c);
		}

		for (ProtocolEvent e : events) {
			this.callProtocolEvent(e);
		}

		return chunks;
	}
	
	public void sendPosition() {
		sendPosition(player.getScene().getPosition(), player.getScene().getRotation());
	}

	@Override
	protected void sendPosition(Point p, Quaternion rot) {
		PlayerPositionLookMessage PPLMsg = new PlayerPositionLookMessage(p.getX(), p.getY() + STANCE, p.getZ(), p.getY(), rot.getYaw(), rot.getPitch(), true, BuddiesBlockDataChannelMessage.CHANNEL_ID, getRepositionManager());
		session.send(false, PPLMsg);
	}
	
	/* TODO : Fix this method.
	@Override
	protected void worldChanged(World world) {
		WorldConfigurationNode node = BuddiesConfig.WORLDS.get(world);
		maxY = node.MAX_Y.getInt() & (~Chunk.BLOCKS.MASK);
		minY = node.MIN_Y.getInt() & (~Chunk.BLOCKS.MASK);
		stepY = node.STEP_Y.getInt() & (~Chunk.BLOCKS.MASK);
		int lowY = maxY - stepY;
		int highY = minY + stepY;
		lastY = Integer.MAX_VALUE;

		final DatatableComponent data = world.getData();
		final Buddy buddy = player.add(Buddy.class);
		
		int entityId = player.getId();

		if (first) {
			first = false;
			if (human != null && human.getAttachedCount() > 1) {
				gamemode = human.getGameMode();
			} else {
				gamemode = data.get(VanillaData.GAMEMODE);
				if (human != null) {
					human.setGamemode(gamemode);
				}
			}

			Server server = (Server) session.getEngine();
			PlayerLoginRequestMessage idMsg = new PlayerLoginRequestMessage(entityId, worldType.toString(), gamemode.getId(), (byte) dimension.getId(), difficulty.getId(), (byte) server.getMaxPlayers());
			player.getSession().send(false, true, idMsg);
			player.getSession().setState(State.GAME);
		} else {
			if (human != null) {
				gamemode = human.getGameMode();
			}
			player.getSession().send(false, new PlayerRespawnMessage(0, difficulty.getId(), gamemode.getId(), 256, worldType.toString()));
			player.getSession().send(false, new PlayerRespawnMessage(1, difficulty.getId(), gamemode.getId(), 256, worldType.toString()));
			player.getSession().send(false, new PlayerRespawnMessage(dimension.getId(), difficulty.getId(), gamemode.getId(), 256, worldType.toString()));
		}

		if (human != null) {
			if (first) {
				human.setGamemode(gamemode, false);
			}
			human.updateAbilities();
		}

		PlayerInventory inv = player.get(PlayerInventory.class);
		if (inv != null) {
			inv.updateAll();
		}

		Point pos = world.getSpawnPoint().getPosition();
		PlayerSpawnPositionMessage SPMsg = new PlayerSpawnPositionMessage((int) pos.getX(), (int) pos.getY(), (int) pos.getZ(), getRepositionManager());
		player.getSession().send(false, SPMsg);
		session.send(false, new PlayerHeldItemChangeMessage(player.add(PlayerInventory.class).getQuickbar().getSelectedSlot().getIndex()));
		Sky sky;

		switch (dimension) {
			case NETHER:
				sky = world.add(NetherSky.class);
				break;
			case THE_END:
				sky = world.add(TheEndSky.class);
				break;
			default:
				sky = world.add(NormalSky.class);
		}
		sky.updatePlayer(player);
	}*/
	
	@Override
	protected void resetChunks() {
		super.resetChunks();
		this.emptyColumns.clear();
		this.activeChunks.clear();
		this.initializedChunks.clear();
	}
	
	private int lastY = Integer.MIN_VALUE;

	@Override
	public void finalizeTick() {
		Point currentPosition = player.getScene().getPosition();

		int y = currentPosition.getBlockY();

		if (y != lastY && !isTeleportPending()) {

			lastY = y;
			int cY = getRepositionManager().convertY(y);

			if (cY >= maxY || cY < minY) {
				int steps = (cY - ((maxY + minY) >> 1)) / stepY;

				offsetY -= steps * stepY;
				bpm.setOffset(offsetY);
				cY = getRepositionManager().convertY(y);

				if (cY >= maxY) {
					offsetY -= stepY;
				} else if (cY < minY) {
					offsetY += stepY;
				}

				bpm.setOffset(offsetY);
				setRespawned();
			}
		}

		super.finalizeTick();
	}
	
	@Override
	public void preSnapshot() {
		super.preSnapshot();

		Long key;
		while ((key = this.emptyColumns.poll()) != null) {
			int x = IntPairHashed.key1(key);
			int z = IntPairHashed.key2(key);
			TIntSet column = initializedChunks.get(x, z);
			if (column != null && column.isEmpty()) {
				column = initializedChunks.remove(x, z);
				activeChunks.remove(x, z);
				session.send(false, new ChunkDataMessage(x, z, true, null, null, null, true, player.getSession(), getRepositionManager()));
			}
		}
	}

	@Override
	public void updateBlock(Chunk chunk, int x, int y, int z, BlockMaterial material, short data) {
		short id = getBuddiesId(material);
		x += chunk.getBlockX();
		y += chunk.getBlockY();
		z += chunk.getBlockZ();
		BlockChangeMessage BCM = new BlockChangeMessage(x, y, z, id, (short) 0, getRepositionManager());
		session.send(false, BCM);
	}
	
	@Override
	public void syncEntity(Entity e, Transform liveTransform, boolean spawn, boolean destroy, boolean update) {
		super.syncEntity(e, liveTransform, spawn, destroy, update);
		EntityProtocol ep = e.getNetwork().getEntityProtocol(BuddiesPlugin.BUDDIES_PROTOCOL_ID);
		if (ep != null) {
			List<Message> messages = new ArrayList<Message>();
			// Sync using vanilla protocol
			if (destroy) {
				messages.addAll(ep.getDestroyMessages(e));
			}
			if (spawn) {
				messages.addAll(ep.getSpawnMessages(e, getRepositionManager()));
			}
			if (update) {
				boolean force = shouldForce(e.getId());
				messages.addAll(ep.getUpdateMessages(e, liveTransform, getRepositionManager(), force));
			}
			for (Message message : messages) {
				this.session.send(false, message);
			}
		}
	}
	
	private boolean shouldForce(int entityId) {
		int hash = HASH_SEED;
		hash += (hash << 5) + entityId;
		hash += (hash << 5) + tickCounter;
		return (hash & FORCE_MASK) == 0;
	}
	
	@Override
	public Iterator<IntVector3> getViewableVolume(int cx, int cy, int cz, int viewDistance) {
		RepositionManager rmI = this.getRepositionManager().getInverse();

		int convertY = rmI.convertChunkY(0);

		return new FlatIterator(cx, convertY, cz, 16, viewDistance);
	}
	
	@Override
	public boolean isInViewVolume(Point playerChunkBase, Point testChunkBase, int viewDistance) {
		if (playerChunkBase == null) {
			return false;
		}
		int distance = Math.abs(playerChunkBase.getChunkX() - testChunkBase.getChunkX()) + Math.abs(playerChunkBase.getChunkZ() - testChunkBase.getChunkZ());
		return distance <= viewDistance;
	}
	
	@EventHandler
	public Message onObjectiveAction(ObjectiveActionEvent event) {
		Objective obj = event.getObjective();
		return new ScoreboardObjectiveMessage(obj.getName(), obj.getDisplayName(), event.getAction());
	}

	@EventHandler
	public Message onObjectiveDisplay(ObjectiveDisplayEvent event) {
		return new ScoreboardDisplayMessage((byte) event.getSlot().ordinal(), event.getObjectiveName());
	}

	@EventHandler
	public Message onScoreUpdate(ScoreUpdateEvent event) {
		return new ScoreboardScoreMessage(event.getKey(), event.isRemove(), event.getObjectiveName(), event.getValue());
	}
	
	@EventHandler
	public Message onTeamAction(TeamActionEvent event) {
		Team team = event.getTeam();
		return new ScoreboardTeamMessage(
				team.getName(), event.getAction(),
				team.getDisplayName(),
				team.getPrefix(), team.getSuffix(),
				team.isFriendlyFire(), event.getPlayers()
		);
	}
	
	public enum ChunkInit {
		CLIENT_SEL, FULL_COLUMN, HEIGHTMAP, EMPTY_COLUMN;

		public static ChunkInit getChunkInit(String init) {
			if (init == null) {
				return CLIENT_SEL;
			} else if (isEqual(init, "auto", "client", "client_sel")) {
				return CLIENT_SEL;
			} else if (isEqual(init, "full", "fullcol", "full_column")) {
				return FULL_COLUMN;
			} else if (isEqual(init, "empty", "emptycol", "empty_column")) {
				return EMPTY_COLUMN;
			} else if (isEqual(init, "heightmap", "height_map")) {
				return HEIGHTMAP;
			} else {
				BuddiesPlugin.getInstance().getEngine().getLogger().info("Invalid chunk init setting, " + init + ", using default setting auto");
				BuddiesPlugin.getInstance().getEngine().getLogger().info("Valid settings are:");
				BuddiesPlugin.getInstance().getEngine().getLogger().info("client_sel Allows client selection, defaults to full columns");
				BuddiesPlugin.getInstance().getEngine().getLogger().info("fullcol    Sends full columns");
				BuddiesPlugin.getInstance().getEngine().getLogger().info("heightmap  Sends a heightmap including the topmost block");
				BuddiesPlugin.getInstance().getEngine().getLogger().info("empty      Sends empty columns");

				return CLIENT_SEL;
			}
		}

		public Collection<Chunk> getChunks(final Chunk c) {
			if (this.sendColumn()) {
				final int x = c.getX();
				final int z = c.getZ();
				final int height = WORLD_HEIGHT >> Chunk.BLOCKS.BITS;
				List<Chunk> chunks = new ArrayList<Chunk>(height);
				List<Vector3> ungenerated = new ArrayList<Vector3>(height);
				for (int y = 0; y < height; y++) {
					Chunk cc = c.getWorld().getChunk(x, y, z, LoadOption.LOAD_ONLY);
					if (cc == null) {
						c.getRegion().getTaskManager().scheduleSyncDelayedTask(BuddiesPlugin.getInstance(), new Runnable() {
							public void run() {
								for (int y = 0; y < height; y++) {
									c.getWorld().getChunk(x, y, z, LoadOption.LOAD_GEN);
								}
							}
						});
						ungenerated.add(new Vector3(x, y, z));
					} else {
						chunks.add(cc);
					}
				}
				if (ungenerated.isEmpty()) {
					return chunks;
				} else {
					c.getWorld().queueChunksForGeneration(ungenerated);
					return null;
				}
			} else {
				List<Chunk> chunks = new ArrayList<Chunk>(1);
				chunks.add(c);
				return chunks;
			}
		}

		public boolean sendColumn() {
			return this == CLIENT_SEL || this == FULL_COLUMN;
		}

		public byte[] getChunkData(int[][] heights, BlockMaterial[][] materials, Point p, List<ProtocolEvent> updateEvents) {
			switch (this) {
				case CLIENT_SEL:
					return getChunkFullColumn(heights, materials, p, updateEvents);
				case FULL_COLUMN:
					return getChunkFullColumn(heights, materials, p, updateEvents);
				case HEIGHTMAP:
					return getChunkHeightMap(heights, materials, p);
				case EMPTY_COLUMN:
					return getEmptyChunk(heights, materials, p);
				default:
					return getChunkFullColumn(heights, materials, p, updateEvents);
			}
		}

		private static byte[] getChunkFullColumn(int[][] heights, BlockMaterial[][] materials, Point p, List<ProtocolEvent> updateEvents) {
			Chunk c = p.getWorld().getChunkFromBlock(p, LoadOption.LOAD_ONLY);
			return getChunkFullData(c, updateEvents);
		}

		public static byte[] getChunkFullData(Chunk c, List<ProtocolEvent> updateEvents) {

			BuddiesContainer container = new BuddiesContainer();
			c.fillBlockContainer(container);
			c.fillBlockComponentContainer(container);
			
			BuddiesCuboidLightBuffer blockLight = (BuddiesCuboidLightBuffer) c.getLightBuffer(BuddiesLighting.BLOCK_LIGHT.getId());
			BuddiesCuboidLightBuffer skyLight = (BuddiesCuboidLightBuffer) c.getLightBuffer(BuddiesLighting.SKY_LIGHT.getId());
			
			container.copyLight(true, blockLight);
			container.copyLight(false, skyLight);

			/* TODO: This is for complex blocks (pretty sure I don't have those)
			int[] componentX = container.getXArray();
			int[] componentY = container.getYArray();
			int[] componentZ = container.getZArray();

			for (int i = 0; i < container.getBlockComponentCount(); i++) {
				BlockMaterial bm = c.getBlockMaterial(componentX[i], componentY[i], componentZ[i]);
				if (bm instanceof VanillaComplexMaterial) {
					ProtocolEvent event = ((VanillaComplexMaterial) bm).getUpdate(c.getWorld(), componentX[i], componentY[i], componentZ[i]);
					if (event != null) {
						updateEvents.add(event);
					}
				}
			}*/

			return container.getChunkFullData();
		}

		private static byte[] getEmptyChunk(int[][] heights, BlockMaterial[][] materials, Point p) {
			int chunkY = p.getChunkY();
			return chunkY <= 4 ? emptyGroundChunkData : emptySkyChunkData;
		}

		private static byte[] getChunkHeightMap(int[][] heights, BlockMaterial[][] materials, Point p) {
			int chunkY = p.getChunkY();
			byte[] packetChunkData = new byte[Chunk.BLOCKS.HALF_VOLUME * 5];
			int baseY = chunkY << Chunk.BLOCKS.BITS;

			for (int xx = 0; xx < Chunk.BLOCKS.SIZE; xx++) {
				for (int zz = 0; zz < Chunk.BLOCKS.SIZE; zz++) {
					int dataOffset = xx | (zz << Chunk.BLOCKS.BITS);
					int threshold = heights[xx][zz] - baseY;
					if (chunkY == 0 && threshold < 0) {
						threshold = 0;
					}
					int yy;
					// Set blocks below height to the solid block
					for (yy = 0; yy < Chunk.BLOCKS.SIZE && yy <= threshold; yy++) {
						if (yy == threshold) {
							BlockMaterial bm = materials[xx][zz];
							if (bm == null) {
								bm = BuddiesMaterials.STONE;
							}
							int converted = getBuddiesId(bm.getId());
							packetChunkData[dataOffset] = (byte) converted;
						} else {
							packetChunkData[dataOffset] = SOLID_BLOCK_ID;
						}
						dataOffset += Chunk.BLOCKS.AREA;
					}
					// Set sky light of blocks above height to 15
					// Use half of start offset and add the block id and data length (2 volumes)
					byte mask = (xx & 0x1) == 0 ? (byte) 0x0F : (byte) 0xF0;
					dataOffset = Chunk.BLOCKS.DOUBLE_VOLUME + (dataOffset >> 1);
					for (; yy < Chunk.BLOCKS.SIZE; yy++) {
						packetChunkData[dataOffset] |= mask;
						dataOffset += Chunk.BLOCKS.HALF_AREA;
					}
				}
			}
			return packetChunkData;
		}

		private static boolean isEqual(String in, String... args) {

			if (in == null) {
				return false;
			}
			for (String arg : args) {
				if (arg.toLowerCase().equals(in.toLowerCase())) {
					return true;
				}
			}
			return false;
		}
	}

}
