package me.Man_cub.Buddies.world.generator.biome.biomes;

import java.awt.Color;

import me.Man_cub.Buddies.data.Climate;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiome;

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
