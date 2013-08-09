package me.man_cub.buddies.world.generator;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.man_cub.buddies.BuddiesPlugin;
import me.man_cub.buddies.world.generator.biome.BuddiesBiomes;
import me.man_cub.buddies.world.generator.maps.BattleHillGenerator;
import me.man_cub.buddies.world.generator.maps.LobbyGenerator;

public class BuddiesGenerators {
	public static final LobbyGenerator LOBBY = new LobbyGenerator();
	public static final BattleHillGenerator BATTLEHILL = new BattleHillGenerator(BuddiesBiomes.PLAINS, 128);
	private static final Map<String, BuddiesGenerator> BY_NAME = new HashMap<String, BuddiesGenerator>();

	static {
		for (Field objectField : BuddiesGenerators.class.getDeclaredFields()) {
			objectField.setAccessible(true);
			try {
				final Object object = objectField.get(null);
				if (object instanceof BuddiesGenerator) {
					BY_NAME.put(objectField.getName().toLowerCase(), (BuddiesGenerator) object);
				}
			} catch (Exception ex) {
				BuddiesPlugin.getInstance().getEngine().getLogger().info("Could not properly reflect BuddiesGenerators! Unexpected behavior may occur.");
				ex.printStackTrace();
			}
		}
	}
	
	public static BuddiesGenerator byName(String name) {
		return BY_NAME.get(name.toLowerCase());
	}
	
	public static Collection<BuddiesGenerator> getGenerators() {
		return BY_NAME.values();
	}
}
