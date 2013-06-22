package me.Man_cub.Buddies.world.generator.maps;

import java.util.Random;

import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiome;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiomeGenerator;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiomes;

import org.spout.api.generator.biome.BiomeManager;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.geo.discrete.Point;
import org.spout.api.math.Vector3;
import org.spout.api.util.cuboid.CuboidBlockMaterialBuffer;

public class BattleHillGenerator extends BuddiesBiomeGenerator {
	
	public BattleHillGenerator(BuddiesBiome biome, int length) {
		super(biome, length);
	}
	
	@Override
	public void registerBiomes() {
		super.registerBiomes();
		register(BuddiesBiomes.PLAINS);
	}

	@Override
	public String getName() {
		return "BattleHill";
	}

	@Override
	protected void generateTerrain(CuboidBlockMaterialBuffer blockData, int chunkX, int chunkY, int chunkZ, BiomeManager biomeManager, long seed) {
		if (!isChunkInMap(chunkX, chunkY, chunkZ)) {
			return;
		}
		
		final Vector3 size = blockData.getSize();
		final int sizeX = size.getFloorX();
		final int sizeY = size.getFloorY();
		final int sizeZ = size.getFloorZ();
		for (int xx = 0; xx < sizeX; xx++) {
			for (int zz = 0; zz < sizeZ; zz++) {
				for (int yy = 0; yy < sizeY; yy++) {
					if (yy == 0) {
						blockData.set(chunkX + xx, chunkY + yy, chunkZ + zz, BuddiesMaterials.STONE);
					} else if (yy == 1) {
						blockData.set(chunkX + xx, chunkY + yy, chunkZ + zz, BuddiesMaterials.DIRT);
					} else if (yy == 2) {
						blockData.set(chunkX + xx, chunkY + yy, chunkZ + zz, BuddiesMaterials.GRASS);
					} else {
						blockData.set(chunkX + xx, chunkY + yy, chunkZ + zz, BuddiesMaterials.AIR);
					}
				}
			}
		}
	}
	
	public boolean isChunkInMap(int chunkX, int chunkY, int chunkZ) {
		if (chunkX < 0 || chunkY < 0 || chunkZ < 0 || chunkX >= getLength() || chunkY >= getLength() || chunkZ >= getLength()) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public Point getSafeSpawn(World world) {
		final Random random = new Random();
		for (byte attempts = 0; attempts < 32; attempts++) {
			final int x = random.nextInt(128);
			final int z = random.nextInt(128);
			final int y = getHighestSolidBlock(world, x, z);
			if (y != -1) {
				return new Point(world, 0, 5, 0);
			}
		}
		return new Point(world, 0, 5, 0);
	}

	private int getHighestSolidBlock(World world, int x, int z) {
		int y = getLength() - 1;
		while (world.getBlockMaterial(x, y, z) == BuddiesMaterials.AIR) {
			if (--y == 0) {
				return -1;
			}
		}
		return ++y;
	}

	
	@Override
	public int[][] getSurfaceHeight(World world, int chunkX, int chunkZ) {
		int height = getLength() - 1;
		int[][] heights = new int[Chunk.BLOCKS.SIZE][Chunk.BLOCKS.SIZE];
		for (int x = 0; x < Chunk.BLOCKS.SIZE; x++) {
			for (int z = 0; z < Chunk.BLOCKS.SIZE; z++) {
				heights[x][z] = height;
			}
		}
		return heights;
	}

}
