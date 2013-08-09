package me.man_cub.buddies.world.lighting;

import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.material.BlockMaterial;
import org.spout.api.math.IntVector3;
import org.spout.api.math.Vector3;
import org.spout.api.util.IntVector3Array;
import org.spout.api.util.IntVector3CuboidArray;
import org.spout.api.util.cuboid.ChunkCuboidLightBufferWrapper;
import org.spout.api.util.cuboid.ImmutableCuboidBlockMaterialBuffer;
import org.spout.api.util.cuboid.ImmutableHeightMapBuffer;
import org.spout.api.util.cuboid.procedure.CuboidBlockMaterialProcedure;

public class BuddiesBlocklightLightingManager extends BuddiesLightingManager {
	public BuddiesBlocklightLightingManager(String name) {
		super(name);
	}

	@Override
	public void resolve(ChunkCuboidLightBufferWrapper<BuddiesCuboidLightBuffer> light, ImmutableCuboidBlockMaterialBuffer material, ImmutableHeightMapBuffer height, int[] x, int[] y, int[] z, int changedBlocks) {
		Iterable<IntVector3> coords = new IntVector3Array(x, y, z, changedBlocks);
		super.resolve(light, material, height, coords, false);
	}

	@Override
	public void resolveChunks(ChunkCuboidLightBufferWrapper<BuddiesCuboidLightBuffer> light, ImmutableCuboidBlockMaterialBuffer material, ImmutableHeightMapBuffer height, int[] bx, int[] by, int[] bz, int[] tx, int[] ty, int[] tz, int changedCuboids) {
		Iterable<IntVector3> coords = new IntVector3CuboidArray(bx, by, bz, tx, ty, tz, changedCuboids);
		super.resolve(light, material, height, coords, false);
	}

	@Override
	protected int getEmittedLight(ImmutableCuboidBlockMaterialBuffer material, ImmutableHeightMapBuffer height, int x, int y, int z) {
		BlockMaterial m = material.get(x, y, z);
		short data = material.getData(x, y, z);
		return m.getLightLevel(data);
	}

	@Override
	public void updateEmittingBlocks(int[][][] emittedLight, ChunkCuboidLightBufferWrapper<BuddiesCuboidLightBuffer> light, ImmutableCuboidBlockMaterialBuffer material, ImmutableHeightMapBuffer height, int x, int y, int z) {
		int size = Chunk.BLOCKS.SIZE;
		for (int xx = x; xx < x + size; xx++) {
			for (int yy = y; yy < y + size; yy++) {
				int xIndex = xx - x + 1;
				int zIndex = 1;
				int yIndex = yy - y + 1;
				int[] zArray = emittedLight[xIndex][yIndex];
				for (int zz = z; zz < z + size; zz++) {
					BlockMaterial m = material.get(xx, yy, zz);
					zArray[zIndex++] = m.getLightLevel(m.getData());
				}
			}
		}
	}

	@Override
	public void bulkEmittingInitialize(ImmutableCuboidBlockMaterialBuffer buffer, final int[][][] light, int[][] height) {
		Vector3 base = buffer.getBase();

		final int baseX = base.getFloorX();
		final int baseY = base.getFloorY();
		final int baseZ = base.getFloorZ();

		buffer.forEach(new CuboidBlockMaterialProcedure() {
			@Override
			public boolean execute(int x, int y, int z, short id, short data) {
				x -= baseX;
				z -= baseZ;

				BlockMaterial m = BlockMaterial.get(id, data);

				int lightLevel = m.getLightLevel(m.getData());
				if (lightLevel > 0) {
					light[x + 1][y + 1][z + 1] = lightLevel;
				}
				return true;
			}
		});
	}

}
