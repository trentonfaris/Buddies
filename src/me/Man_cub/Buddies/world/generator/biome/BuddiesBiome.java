package me.Man_cub.Buddies.world.generator.biome;

import java.awt.Color;

import me.Man_cub.Buddies.data.Climate;

import org.spout.api.generator.biome.Biome;
import org.spout.cereal.config.annotated.Setting;

public abstract class BuddiesBiome extends Biome {
	private final int biomeId;
	@Setting
	private Climate climate = Climate.MODERATE;
	@Setting({"color-multiplier", "grass"})
	private Color grassColorMultiplier = new Color(255, 255, 255);
	@Setting({"color-multiplier", "water"})
	private Color waterColorMultiplier = new Color(255, 255, 255);
	
	protected BuddiesBiome(int biomeId) {
		this.biomeId = biomeId;
	}
	
	public int getBiomeId() {
		return biomeId;
	}

	/**
	 * Gets the Climate of this Biome
	 * @return the climate
	 */
	public Climate getClimate() {
		return this.climate;
	}

	/**
	 * Sets the Climate for this Biome
	 * @param climate to set to
	 */
	public void setClimate(Climate climate) {
		this.climate = climate;
	}

	public Color getGrassColorMultiplier() {
		return grassColorMultiplier;
	}

	public void setGrassColorMultiplier(Color grassColorMultiplier) {
		this.grassColorMultiplier = grassColorMultiplier;
	}

	public Color getWaterColorMultiplier() {
		return waterColorMultiplier;
	}

	public void setWaterColorMultiplier(Color waterColorMultiplier) {
		this.waterColorMultiplier = waterColorMultiplier;
	}
}
