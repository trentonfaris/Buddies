package me.Man_cub.Buddies.world.generator.biome.biomes;

import java.awt.Color;

import me.Man_cub.Buddies.data.Climate;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiome;

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