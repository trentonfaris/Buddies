package me.Man_cub.Buddies.command;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.world.misc.Sky;
import me.Man_cub.Buddies.component.world.misc.Timer;
import me.Man_cub.Buddies.data.configuration.BuddiesConfig;
import me.Man_cub.Buddies.data.configuration.WorldConfigurationNode;
import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.material.block.crate.Crate;
import me.Man_cub.Buddies.material.block.crate.MegaCrate;
import me.Man_cub.Buddies.material.block.pad.blue.BluePadCenter;
import me.Man_cub.Buddies.util.thread.SpawnLoader;
import me.Man_cub.Buddies.world.generator.BuddiesGenerator;
import me.Man_cub.Buddies.world.generator.BuddiesGenerators;
import me.Man_cub.Buddies.world.lighting.BuddiesLighting;

import org.spout.api.Client;
import org.spout.api.Spout;
import org.spout.api.command.CommandArguments;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Binding;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.Filter;
import org.spout.api.command.filter.PlayerFilter;
import org.spout.api.component.entity.InteractComponent;
import org.spout.api.component.entity.ObserverComponent;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.input.Keyboard;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.api.util.FlatIterator;

public class LobbyInputCommands {
	private final Client client;
	private final BuddiesPlugin plugin;
	
	public LobbyInputCommands(BuddiesPlugin plugin) {
		this.plugin = plugin;
		client = (Client) plugin.getEngine();
	}
	
	//Test command.
	@Command(aliases = "start_game", desc = "Creates a new map", min = 1, max = 1)
	@Binding(Keyboard.KEY_V)
	@Filter(PlayerFilter.class)
	public void beginGame(CommandSource source, CommandArguments args) throws CommandException {
		Spout.getLogger().info("Tried to use command.");
		Player player = (Player) source;
		if (!args.getString(0).equalsIgnoreCase("+")) {
			Spout.getLogger().info("Something went wrong.");
			return;
		}
		
		Spout.getLogger().info("You pressed 'e'.");
		
		World world = Spout.getEngine().loadWorld("battlehill", BuddiesGenerators.BATTLEHILL);
		Spout.getLogger().info("Loaded battlehill.");
		
		world.addLightingManager(BuddiesLighting.BLOCK_LIGHT);
		world.addLightingManager(BuddiesLighting.SKY_LIGHT);
		
		BuddiesPlugin.getInstance().getWorlds().add(world);
		Spout.getLogger().info("Added the world to worlds.");
		
		final int radius = BuddiesConfig.SPAWN_RADIUS.getInt();
		//final int protectioRadius = BuddiesConfig.SPAWN_PROTECTION_RADIUS.getInt();
		SpawnLoader loader = new SpawnLoader(1);
		
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
			loader.load(world, cx, cz, effectiveRadius, newWorld);
			
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
			
			world.add(Timer.class);
			world.add(Sky.class);
		}
		Spout.getLogger().info("Successfully created another world.");
	}
	
	// TODO : Finish this. Need to test
		@Command(aliases = "breakCrate", desc = "Breaks a crate at target location.", min = 1, max = 1)
		@Binding(Keyboard.KEY_Q)
		@Filter(PlayerFilter.class)
		public void breakCrate(CommandSource source, CommandArguments args) throws CommandException {
			Spout.getLogger().info("Break crate.");
			Player player = (Player) source;
			if (!args.getString(0).equalsIgnoreCase("+")) {
				return;
			}
			if (player.get(BuddyInventory.class).getHeldItem().getMaterial() == BuddiesMaterials.CRATE || player.get(BuddyInventory.class).getHeldItem().getMaterial() == BuddiesMaterials.MEGACRATE) {
				BuddiesPlugin.getInstance().getEngine().getLogger().info("You can't break a crate while holding one.");
			}
			InteractComponent interact = player.get(InteractComponent.class);
			if (interact != null) {
				final Block target = interact.getTargetBlock();
				// Make them be next to it.
				if (target.getMaterial() instanceof Crate) {
					if (player.getWorld().getBlockMaterial(target.getX(), target.getY() - 1, target.getZ()) instanceof BluePadCenter) {
						//Point point = checkPad(player, target.getX(), target.getY() - 1, target.getZ());
						client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
							@Override
							public void run() {
								target.setMaterial(BuddiesMaterials.AIR);
							}
						});
					} else {
						client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
							@Override
							public void run() {
								target.setMaterial(BuddiesMaterials.AIR);
							}
						});
					}
				} else if (target.getMaterial() instanceof MegaCrate) {
					if (player.getWorld().getBlockMaterial(target.getX(), target.getY() - 1, target.getZ()) instanceof BluePadCenter) {
						//Point point = checkPad(player, target.getX(), target.getY() - 1, target.getZ());
						client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
							@Override
							public void run() {
								target.setMaterial(BuddiesMaterials.AIR);
							}
						});
					} else {
						client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
							@Override
							public void run() {
								target.setMaterial(BuddiesMaterials.AIR);
							}
						});
					}
				} else if (!(target.getMaterial() instanceof Crate) && !(target.getMaterial() instanceof MegaCrate)){
				}
			}
		}

}
