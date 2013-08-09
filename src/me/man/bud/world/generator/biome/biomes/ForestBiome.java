package me.man_cub.buddies.world.generator.biome.biomes;

import java.awt.Color;

import me.man_cub.buddies.data.Climate;
import me.man_cub.buddies.world.generator.biome.BuddiesBiome;

public class ForestBiome extends BuddiesBiome {
	
	public ForestBiome(int id) {
		super(id);
		setGrassColorMultiplier(new Color(121, 192, 90));
		setClimate(Climate.WARM);
	}
	
	@Override
	public String getName() {
		return "Forest";
	}
}