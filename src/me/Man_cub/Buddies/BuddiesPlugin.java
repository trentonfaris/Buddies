package me.Man_cub.Buddies;

import java.util.ArrayList;
import java.util.logging.Level;

import me.Man_cub.Buddies.command.AdministrationCommands;
import me.Man_cub.Buddies.command.AdministrationCommands.TPSMonitor;
import me.Man_cub.Buddies.component.world.misc.Base;
import me.Man_cub.Buddies.component.world.misc.Border;
import me.Man_cub.Buddies.component.world.misc.Dropper;
import me.Man_cub.Buddies.component.world.misc.Hill;
import me.Man_cub.Buddies.component.world.misc.Sky;
import me.Man_cub.Buddies.component.world.misc.Timer;
import me.Man_cub.Buddies.data.configuration.BuddiesConfig;
import me.Man_cub.Buddies.data.configuration.WorldConfigurationNode;
import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.scoreboard.ScoreboardListener;
import me.Man_cub.Buddies.world.generator.BuddiesGenerator;
import me.Man_cub.Buddies.world.generator.BuddiesGenerators;
import me.Man_cub.Buddies.world.lighting.BuddiesLighting;

import org.spout.api.Spout;
import org.spout.api.command.annotated.AnnotatedCommandExecutorFactory;
import org.spout.api.component.entity.NetworkComponent;
import org.spout.api.component.entity.ObserverComponent;
import org.spout.api.entity.Entity;
import org.spout.api.event.EventManager;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.api.plugin.Plugin;
import org.spout.api.plugin.PluginDescriptionFile;
import org.spout.api.plugin.PluginLogger;
import org.spout.api.protocol.Protocol;
import org.spout.api.util.FlatIterator;

public class BuddiesPlugin extends Plugin {
	private PluginDescriptionFile pdf;
	private static BuddiesPlugin instance;
	private BuddiesConfig config;
	public static final int BUDDIES_PROTOCOL_ID = NetworkComponent.getProtocolId("me.Man_cub.Buddies.plugin.protocol");
	//Client only
	//TODO Remove, seriously not secure.
	private String username = "";
	private String sessionId = "";
	public static ArrayList<World> worlds = new ArrayList<World>();
	
	@Override
	public void onLoad() {
		pdf = getDescription();
		instance = this;
		config = new BuddiesConfig(getDataFolder());
		config.load();
		((PluginLogger) getLogger()).setTag(ChatStyle.RESET + "[" + ChatStyle.AQUA + "Buddies" + ChatStyle.RESET + "] ");
		getLogger().info("Loading " + pdf.getName() + "...");
		Protocol.registerProtocol(new BuddiesProtocol());
		BuddiesMaterials.initialize();
		BuddiesLighting.initialize();
		getLogger().info(pdf.getFullName() + " loaded.");
	}
	@Override
	public void onDisable() {
		instance = null;
		getLogger().info(pdf.getFullName() + " disabled.");
		
	}

	@Override
	public void onEnable() {
				
		//Commands
		AnnotatedCommandExecutorFactory.create(new AdministrationCommands(this));
				
		EventManager em = getEngine().getEventManager();
		em.registerEvents(new BuddiesListener(this), this);
		em.registerEvents(new ScoreboardListener(), this);
				
		switch (getEngine().getPlatform()) {
			case CLIENT:
				//final Client client = (Client) getEngine();
				//final InputManager input = client.getInputManager();
				// TODO : Bind basic input commands here. See VanillaPlugin.java.
				
				if (getEngine().debugMode()) {
					setupLobby();
				}
				
				break;
			case PROXY:
				break;
			case SERVER:
				setupLobby();
				break;
			default:
				break;
		}
		
		//TODO: Remove this check when the null world bug is fixed
		for (World world : getEngine().getWorlds()) {
			if (world == null) {
				getLogger().log(Level.SEVERE, "A World element in Engine.getWorlds() is null!");
			}
		}
		
		getLogger().info("v" + pdf.getVersion() + " enabled. Protocol: " + pdf.getData("protocol"));
	}
	
	private void setupLobby() {		
		for (WorldConfigurationNode worldNode : BuddiesConfig.WORLDS.getAll()) {
			if (worldNode.LOAD.getBoolean()) {
				String generatorName = worldNode.GENERATOR.getString();
				BuddiesGenerator generator = BuddiesGenerators.byName(generatorName);
				if (generator == null) {
					throw new IllegalArgumentException("Invalid generator name for world '" + worldNode.getWorldName() + "': " + generatorName);
				}
				World world = Spout.getEngine().loadWorld("battlehill", BuddiesGenerators.BATTLEHILL);
				
				world.addLightingManager(BuddiesLighting.BLOCK_LIGHT);
				world.addLightingManager(BuddiesLighting.SKY_LIGHT);
				
				BuddiesPlugin.getInstance().getWorlds().add(world);
				
				final int radius = BuddiesConfig.SPAWN_RADIUS.getInt();
				//final int protectioRadius = BuddiesConfig.SPAWN_PROTECTION_RADIUS.getInt();
				
				WorldConfigurationNode worldConfig = BuddiesConfig.WORLDS.get(world);
				boolean newWorld = world.getAge() <= 0;
				if (worldConfig.LOADED_SPAWN.getBoolean() || newWorld) {
					// Initialize the first chunks
					Point point = world.getSpawnPoint().getPosition();
					int cx = point.getBlockX() >> Chunk.BLOCKS.BITS;
					int cz = point.getBlockZ() >> Chunk.BLOCKS.BITS;
					
					// TODO : More spawn protection
					//((BuddiesProtectionService) getEngine().getServiceManager().getRegistration(ProtectionService.class).getProvider()).addProtection(new SpawnProtection(world.getName() + " Spawn Protection", world, point, protectionRadius));
					
					//Load or generate spawn area
					int effectiveRadius = newWorld ? (2 * radius) : radius;
					
					if (worldConfig.LOADED_SPAWN.getBoolean()) {
						@SuppressWarnings("unchecked")
						Entity e = world.createAndSpawnEntity(point, LoadOption.LOAD_GEN, ObserverComponent.class);
						e.setObserver(new FlatIterator(cx, 0, cz, 16, effectiveRadius));
					}
					
					//Grab safe spawn if newly created world.
					if (newWorld && world.getGenerator() instanceof BuddiesGenerator) {
						Point spawn = ((BuddiesGenerator) world.getGenerator()).getSafeSpawn(world);
						world.setSpawnPoint(new Transform(spawn, Quaternion.IDENTITY, Vector3.ONE));
					}
					
					world.add(Hill.class);
					world.add(Timer.class);
					world.add(Sky.class);
					world.add(Border.class);
					world.add(Base.class);
					world.add(Dropper.class);
				}
			}
		}
	}
	
	public BuddiesConfig getConfig() {
		return config;
	}
	
	public void setClientAuthInfos(String username, String sessionId) {
		this.username = username;
		this.sessionId = sessionId;
	}

	public String getUsername() {
		return username;
	}

	public String getSessionId() {
		return sessionId;
	}
	
	public static BuddiesPlugin getInstance() {
		return instance;
	}
	
	public String getPrefix() {
		return ((PluginLogger) getLogger()).getTag();
	}
	
	private TPSMonitor monitor;
	public TPSMonitor getTPSMonitor() {
		return monitor;
	}
	
	public void setTPSMonitor(TPSMonitor monitor) {
		this.monitor = monitor;
	}
	
	public ArrayList<World> getWorlds() {
		return worlds;
	}
	
}
