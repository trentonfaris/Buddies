package me.man_cub.buddies.world.generator.biome.biomes;

import java.awt.Color;

import me.man_cub.buddies.data.Climate;
import me.man_cub.buddies.world.generator.biome.BuddiesBiome;

public class TundraBiome extends BuddiesBiome {
	
	public TundraBiome(int id) {
		super(id);
		setGrassColorMultiplier(new Color(128, 180, 151));
		setClimate(Climate.COLD);
	}
	
	@Override
	public String getName() {
		return "Tundra";
	}

}
