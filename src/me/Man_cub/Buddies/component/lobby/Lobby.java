package me.Man_cub.Buddies.component.lobby;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.world.misc.Sky;
import me.Man_cub.Buddies.component.world.misc.Timer;
import me.Man_cub.Buddies.world.generator.BuddiesGenerator;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiomes;
import me.Man_cub.Buddies.world.generator.maps.BattleHillGenerator;
import me.Man_cub.Buddies.world.lighting.BuddiesLighting;

import org.spout.api.Server;
import org.spout.api.entity.Player;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.Session;

public class Lobby {
	private List<Player> players = new ArrayList<Player>();
	private String name = null;
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();
		
	public Lobby() {
	}
	
	public void playerJoin(Player playerJoined) {
		plugin.getEngine().getLogger().info("Player joined the lobby!");
		//sendPlayerList(playerJoined.getSession());
		//sendGameList(playerJoined.getSession());
		players.add(playerJoined);
	}
	
	public void createNewGame() {
		if (!plugin.getEngine().getWorlds().isEmpty()) {
			Collection<? extends World> worlds = plugin.getEngine().getWorlds();
			for (int i = 0; i <= worlds.size() - 1; i++) {
				for (World world : worlds) {
					String worldName = world.getName();
					if (worldName.equals("map" + new Integer(i).toString())) {
						continue;
					} else {
						name = "map" + new Integer(i).toString();
					}
				}
			}
			if (name == null) {
				name = "map" + new Integer(worlds.size()).toString();
			}
		} else {
			name = "map0";
		}
		BattleHillGenerator generator = new BattleHillGenerator(BuddiesBiomes.PLAINS, 128);
		World world = ((Server) plugin.getEngine()).loadWorld(name, generator);
		world.addLightingManager(BuddiesLighting.BLOCK_LIGHT);
		world.addLightingManager(BuddiesLighting.SKY_LIGHT);
		
		if (world.getAge() <= 0) {
			Point spawn = ((BuddiesGenerator) world.getGenerator()).getSafeSpawn(world);
			world.setSpawnPoint(new Transform(spawn, Quaternion.IDENTITY, Vector3.ONE));
		}
		
		world.add(Timer.class);
		world.add(Sky.class);
		
		name = null;
		
	}
	
	public void deleteFolder(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File subFile : file.listFiles()) {
					deleteFolder(subFile);
				}
			}
			file.delete();
		}
	}
	
	public void sendPlayerList(Session session) {
	}
	
	public void sendGameList(Session session) {
	}
	
	public List<Player> getPlayers() {
		return players;
	}

}
