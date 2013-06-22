package me.Man_cub.Buddies.world.lighting;

import org.spout.api.material.BlockMaterial;
import org.spout.api.material.block.BlockFace;
import org.spout.api.material.block.BlockFaces;
import org.spout.api.math.IntVector3;
import org.spout.api.util.bytebit.ByteBitSet;
import org.spout.api.util.cuboid.ChunkCuboidLightBufferWrapper;
import org.spout.api.util.cuboid.ImmutableCuboidBlockMaterialBuffer;
import org.spout.api.util.cuboid.ImmutableHeightMapBuffer;
import org.spout.api.util.set.TInt10Procedure;
import org.spout.api.util.set.TInt10TripleSet;

public class ResolveHigherProcedure extends TInt10Procedure {
	
	private final static BlockFace[] allFaces = BlockFaces.NESWBT.toArray();
	private final TInt10TripleSet[] dirtySets;
	private final ChunkCuboidLightBufferWrapper<BuddiesCuboidLightBuffer> light;
	private final ImmutableCuboidBlockMaterialBuffer material;
	private final BuddiesLightingManager manager;
	private int targetLevel;

	public ResolveHigherProcedure(BuddiesLightingManager manager, ChunkCuboidLightBufferWrapper<BuddiesCuboidLightBuffer> light, ImmutableCuboidBlockMaterialBuffer material, ImmutableHeightMapBuffer height, TInt10TripleSet[] dirtySets) {
		this.dirtySets = dirtySets;
		this.light = light;
		this.material = material;
		this.manager = manager;
		this.targetLevel = 15;
	}
	
	public void setTargetLevel(int level) {
		this.targetLevel = level;
	}

	@Override
	public boolean execute(int x, int y, int z) {
		return execute(x, y, z, false, false);
	}

	public boolean execute(int x, int y, int z, boolean root, boolean root2) {
		short id = material.getId(x, y, z);
		if (id == BlockMaterial.UNGENERATED.getId()) {
			return true;
		}
		
		int lightLevel = manager.getLightLevel(light, x, y, z);
		
		if (lightLevel < targetLevel) {
			throw new IllegalStateException("Light level was not raised to " + targetLevel + " (" + lightLevel + ") at " + x + ", " + y + ", " + z);
		} else if (lightLevel > targetLevel) {
			return true;
		}
		
		BlockMaterial m = material.get(x, y, z);
		
		ByteBitSet centerOcclusionSet = m.getOcclusion(m.getData());
		
		for (int f = 0; f < allFaces.length; f++) {
			BlockFace face = allFaces[f];
			
			if (centerOcclusionSet.get(face)) {
				continue;
			}
			
			IntVector3 offset = face.getIntOffset();
			int nx = x + offset.getX();
			int ny = y + offset.getY();
			int nz = z + offset.getZ();
			
			short nId = material.getId(nx, ny, nz);
			if (nId == BlockMaterial.UNGENERATED.getId()) {
				continue;
			}
			
			int neighborLight = manager.getLightLevel(light, nx, ny, nz, true);
			if (neighborLight >= lightLevel - 1) {
				continue;
			}
			
			short nData = material.getData(nx, ny, nz);
			BlockMaterial nMaterial = BlockMaterial.get(nId, nData);
			
			ByteBitSet occlusionSet = nMaterial.getOcclusion(nData);
			if (occlusionSet.get(face.getOpposite())) {
				continue;
			}
			
			int newLight = targetLevel - nMaterial.getOpacity() - 1;
			if (newLight > neighborLight) {
				dirtySets[newLight].add(nx, ny, nz);
			}
		}
		return true;
	}

}
