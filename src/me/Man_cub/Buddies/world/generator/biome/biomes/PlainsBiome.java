package me.Man_cub.Buddies.world.generator.biome.biomes;

import java.awt.Color;

import me.Man_cub.Buddies.data.Climate;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiome;

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
