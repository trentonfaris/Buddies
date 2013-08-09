package me.man_cub.buddies.world.generator.biome;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.spout.cereal.config.Configuration;
import org.spout.cereal.config.ConfigurationException;
import org.spout.cereal.config.annotated.AnnotatedObjectConfiguration;
import org.spout.cereal.config.serialization.Serialization;

import me.man_cub.buddies.util.ColorSerializer;
import me.man_cub.buddies.world.generator.biome.biomes.ForestBiome;
import me.man_cub.buddies.world.generator.biome.biomes.PlainsBiome;
import me.man_cub.buddies.world.generator.biome.biomes.TundraBiome;

public class BuddiesBiomes {
	public static short id = 0;
	
	//public static final OceanBiome OCEAN = new OceanBiome(0);
	public static final PlainsBiome PLAINS = new PlainsBiome(id++);
	/*public static final DesertBiome DESERT = new DesertBiome(2);
	public static final DesertHillsBiome DESERT_HILLS = new DesertHillsBiome(17);
	public static final MountainsBiome MOUNTAINS = new MountainsBiome(3);*/
	public static final ForestBiome FOREST = new ForestBiome(id++);
	/*public static final ForestHillsBiome FOREST_HILLS = new ForestHillsBiome(18);
	public static final TaigaBiome TAIGA = new TaigaBiome(5);
	public static final TaigaHillsBiome TAIGA_HILLS = new TaigaHillsBiome(19);
	public static final SwampBiome SWAMP = new SwampBiome(6);
	public static final RiverBiome RIVER = new RiverBiome(7);
	public static final FrozenRiverBiome FROZEN_RIVER = new FrozenRiverBiome(11);
	public static final NetherrackBiome NETHERRACK = new NetherrackBiome(8);*/
	public static final TundraBiome TUNDRA = new TundraBiome(id++);
	/*public static final TundraHillsBiome TUNDRA_HILLS = new TundraHillsBiome(13);
	public static final MushroomBiome MUSHROOM = new MushroomBiome(14);
	public static final MushroomShoreBiome MUSHROOM_SHORE = new MushroomShoreBiome(15);
	public static final BeachBiome BEACH = new BeachBiome(16);
	public static final SmallMountainsBiome SMALL_MOUNTAINS = new SmallMountainsBiome(20);
	public static final JungleBiome JUNGLE = new JungleBiome(21);
	public static final JungleHillsBiome JUNGLE_HILLS = new JungleHillsBiome(22);
	public static final FrozenOceanBiome FROZEN_OCEAN = new FrozenOceanBiome(10);
	public static final EndStoneBiome ENDSTONE = new EndStoneBiome(22);*/
	private static final Map<String, BuddiesBiome> BY_NAME = new HashMap<String, BuddiesBiome>();

	static {
		for (Field objectField : BuddiesBiomes.class.getDeclaredFields()) {
			objectField.setAccessible(true);
			try {
				final Object object = objectField.get(null);
				if (object instanceof BuddiesBiome) {
					BY_NAME.put(objectField.getName().toLowerCase(), (BuddiesBiome) object);
				}
			} catch (Exception ex) {
				System.out.println("Could not properly reflect VanillaBiomes! Unexpected behaviour may occur, please report to http://issues.spout.org!");
				ex.printStackTrace();
			}
		}
		Serialization.registerSerializer(new ColorSerializer());
	}

	public static void load(Configuration config) {
		final AnnotatedObjectConfiguration biomes =
				new AnnotatedObjectConfiguration(config);
		for (Entry<String, BuddiesBiome> entry : BY_NAME.entrySet()) {
			biomes.add(entry.getValue(), entry.getKey());
		}
		try {
			biomes.load();
		} catch (ConfigurationException ex) {
			ex.printStackTrace();
		}
	}

	public static void save(Configuration config) {
		final AnnotatedObjectConfiguration biomes =
				new AnnotatedObjectConfiguration(config);
		for (Entry<String, BuddiesBiome> entry : BY_NAME.entrySet()) {
			biomes.add(entry.getValue(), entry.getKey());
		}
		try {
			biomes.save();
		} catch (ConfigurationException ex) {
			ex.printStackTrace();
		}
	}

	public static BuddiesBiome byName(String name) {
		return BY_NAME.get(name.toLowerCase());
	}

	public static Collection<BuddiesBiome> getBiomes() {
		return BY_NAME.values();
	}

}
