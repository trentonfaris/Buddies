package me.Man_cub.Buddies;

import java.util.ArrayList;
import java.util.logging.Level;

import me.Man_cub.Buddies.command.AdministrationCommands;
import me.Man_cub.Buddies.command.AdministrationCommands.TPSMonitor;
import me.Man_cub.Buddies.command.GameInputCommands;
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

import org.spout.api.Server;
import org.spout.api.command.annotated.AnnotatedCommandExecutorFactory;
import org.spout.api.component.entity.NetworkComponent;
import org.spout.api.entity.Entity;
import org.spout.api.event.EventManager;
import org.spout.api.generator.WorldGenerator;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.api.plugin.Plugin;
import org.spout.api.plugin.PluginLogger;
import org.spout.api.util.FlatIterator;

public class BuddiesPlugin extends Plugin {
	private static BuddiesPlugin instance;
	private BuddiesConfig config;
	
	@Override
	public void onDisable() {
		instance = null;
		getLogger().info(getDescription().getFullName() + " disabled.");
	}

	@Override
	public void onEnable() {
		instance = this;
		
		//Config
		config = new BuddiesConfig(getDataFolder());
		config.load();
		
		//Logger
		((PluginLogger) getLogger()).setTag(ChatStyle.RESET + "[" + ChatStyle.AQUA + "Buddies" + ChatStyle.RESET + "] ");
		
		BuddiesMaterials.initialize();
		BuddiesLighting.initialize();
				
		//Commands
		AnnotatedCommandExecutorFactory.create(new AdministrationCommands(this));
				
		EventManager em = getEngine().getEventManager();
		em.registerEvents(new BuddiesListener(this), this);
		em.registerEvents(new ScoreboardListener(), this);
				
		switch (getEngine().getPlatform()) {
			case CLIENT:
				AnnotatedCommandExecutorFactory.create(new GameInputCommands(this));
				
				if (getEngine().debugMode()) {
					//setupLobby();
				}	
				
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
		
		getLogger().info("v" + getDescription().getVersion() + " enabled.");
	}
	
	private void setupLobby() {		
		ArrayList<World> worlds = new ArrayList<World>();
		
		for (WorldConfigurationNode worldNode : BuddiesConfig.WORLDS.getAll()) {
			if (worldNode.LOAD.getBoolean()) {
				//Obtain generator and start generating world
				String generatorName = worldNode.GENERATOR.getString();
				BuddiesGenerator generator = BuddiesGenerators.byName(generatorName);
				if (generator == null) {
					throw new IllegalArgumentException("Invalid generator name for world '" + worldNode.getWorldName() + "': " + generatorName);
				}
				World world = ((Server) getEngine()).loadWorld("battlehill", BuddiesGenerators.BATTLEHILL);
				
				world.addLightingManager(BuddiesLighting.BLOCK_LIGHT);
				world.addLightingManager(BuddiesLighting.SKY_LIGHT);
				
				worlds.add(world);
			}
		}
		
		if (worlds.isEmpty()) {
			return;
		}
				
		for (World world : worlds) {
			//Keep spawn loaded
			WorldConfigurationNode worldConfig = BuddiesConfig.WORLDS.get(world);
			final WorldGenerator generator = world.getGenerator();
			boolean newWorld = world.getAge() <= 0;
			
			if (worldConfig.LOADED_SPAWN.getBoolean() || newWorld) {
				final Point spawn;
				
				//Grab safe spawn if newly created world and generator is BuddiesGenerator, else get old one.
				if (newWorld && generator instanceof BuddiesGenerator) {
					spawn = ((BuddiesGenerator) generator).getSafeSpawn(world);
					world.setSpawnPoint(new Transform(spawn, Quaternion.IDENTITY, Vector3.ONE));
				} else {
					spawn = world.getSpawnPoint().getPosition();
				}
								
				// IChunks coords of spawn
				int cx = spawn.getBlockX() >> Chunk.BLOCKS.BITS;
				int cz = spawn.getBlockZ() >> Chunk.BLOCKS.BITS;
				
				//Add observer to spawn to keep loaded if desired
				if (worldConfig.LOADED_SPAWN.getBoolean()) {
					@SuppressWarnings("unchecked")
					Entity e = world.createAndSpawnEntity(spawn, LoadOption.LOAD_GEN, NetworkComponent.class);
					e.get(NetworkComponent.class).setObserver(new FlatIterator(cx, 0, cz, 16, 0));
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
	
	public BuddiesConfig getConfig() {
		return config;
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
	
}
