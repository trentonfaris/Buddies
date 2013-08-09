package me.man_cub.buddies.world.generator.biome.biomes;

import java.awt.Color;

import me.man_cub.buddies.data.Climate;
import me.man_cub.buddies.world.generator.biome.BuddiesBiome;

public class PlainsBiome extends BuddiesBiome {
	
	public PlainsBiome(int id) {
		super(id);
		setGrassColorMultiplier(new Color(145, 189, 89));
		setClimate(Climate.WARM);
	}
	
	@Override
	public String getName() {
		return "Plains";
	}
}
