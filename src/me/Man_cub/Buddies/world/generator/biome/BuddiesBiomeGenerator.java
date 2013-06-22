package me.Man_cub.Buddies.world.generator.biome;

import java.util.Arrays;

import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.world.generator.BuddiesGenerator;

import org.spout.api.generator.biome.BiomeGenerator;
import org.spout.api.generator.biome.BiomeManager;
import org.spout.api.generator.biome.Simple2DBiomeManager;
import org.spout.api.generator.biome.selector.PerBlockBiomeSelector;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.util.cuboid.CuboidBlockMaterialBuffer;

public abstract class BuddiesBiomeGenerator extends BiomeGenerator implements BuddiesGenerator {
	private boolean voidBelowZero = true;
	private final BuddiesBiome biome;
	public int LENGTH;
	
	public BuddiesBiomeGenerator(BuddiesBiome biome, int length) {
		this.biome = biome;
		this.LENGTH = length;
	}
	
	@Override
	public void registerBiomes() {
		setSelector(new PerBlockBiomeSelector(biome));
	}
	
	@Override
	public void generate(CuboidBlockMaterialBuffer blockData, int chunkX, int chunkY, int chunkZ, World world) {
		if (chunkY < 0) {
			if (voidBelowZero) {
				blockData.flood(BuddiesMaterials.AIR);
			} else {
				blockData.flood(BuddiesMaterials.STONE);
			}
		} else {
			super.generate(blockData, chunkX, chunkY, chunkZ, world);
		}
	}
	
	protected void hasVoidBelowZero(boolean voidBelowZero) {
		this.voidBelowZero = voidBelowZero;
	}
	
	@Override
	public BiomeManager generateBiomes(int chunkX, int chunkZ, World world) {
		final BiomeManager biomeManager = new Simple2DBiomeManager(chunkX, chunkZ);
		final byte[] biomeData = new byte[Chunk.BLOCKS.AREA];
		Arrays.fill(biomeData, (byte) biome.getId());
		biomeManager.deserialize(biomeData);
		return biomeManager;
	}
	
	public int getLength() {
		return LENGTH;
	}

}
