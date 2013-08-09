package me.man_cub.buddies.render;

import java.awt.Color;

import me.man_cub.buddies.world.generator.biome.BuddiesBiome;

public class BiomeWaterColorBufferEffect extends BiomeColorBufferEffect {
	@Override
	protected Color getBiomeColor(BuddiesBiome biome) {
		return biome.getWaterColorMultiplier();
	}
}
