package me.man_cub.buddies.data;

import me.man_cub.buddies.world.generator.biome.BuddiesBiome;

import org.spout.api.generator.biome.Biome;
import org.spout.api.geo.cuboid.Block;

public enum Climate {
	WARM(true, false, false, true),
	MODERATE(true, false, false, true),
	COLD(false, true, true, false);
	private final boolean melting, freezing, snow, rain;

	private Climate(boolean melting, boolean freezing, boolean snow, boolean rain) {
		this.melting = melting;
		this.freezing = freezing;
		this.snow = snow;
		this.rain = rain;
	}

	/**
	 * Gets whether snow and ice melts in this Climate
	 * @return True if ice and snow melts, False if not
	 */
	public boolean isMelting() {
		return this.melting;
	}

	/**
	 * Gets whether water freezes in this Climate
	 * @return True if water freezes, False if not
	 */
	public boolean isFreezing() {
		return this.freezing;
	}

	/**
	 * Gets whether there is rainfall in this Climate
	 * @return True if it rains, False if not
	 */
	public boolean hasRainfall() {
		return this.rain;
	}

	/**
	 * Gets whether there is snowfall in this Climate
	 * @return True if it snows, False if not
	 */
	public boolean hasSnowfall() {
		return this.snow;
	}
	
	/**
	 * Gets the Climate at the Block specified
	 * @param block position
	 * @return the Climate
	 */
	public static Climate get(Block block) {
		Biome biome = block.getBiomeType();
		if (biome instanceof BuddiesBiome) {
			return ((BuddiesBiome) biome).getClimate();
		} else {
			return Climate.MODERATE;
		}
	}

}
