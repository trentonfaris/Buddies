package me.Man_cub.Buddies.render;

import java.awt.Color;

import me.Man_cub.Buddies.world.generator.biome.BuddiesBiome;

public class BiomeGrassColorBufferEffect extends BiomeColorBufferEffect {
	@Override
	protected Color getBiomeColor(BuddiesBiome biome) {
		return biome.getGrassColorMultiplier();
	}
}
