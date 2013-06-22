package me.Man_cub.Buddies.world.lighting;

import org.spout.api.util.cuboid.ChunkCuboidLightBufferWrapper;
import org.spout.api.util.set.TInt10Procedure;

public class IncreaseLightProcedure extends TInt10Procedure {
  
	private int targetLevel;
	private final ChunkCuboidLightBufferWrapper<BuddiesCuboidLightBuffer> light;
	private final BuddiesLightingManager manager;
  
	public IncreaseLightProcedure(BuddiesLightingManager manager, ChunkCuboidLightBufferWrapper<BuddiesCuboidLightBuffer> light) {
		this.light = light;
		this.manager = manager;
		this.targetLevel = 16;
	}

	public void setTargetLevel(int level) {
		this.targetLevel = level;
	}

	@Override
	public boolean execute(int x, int y, int z) {
		int lightLevel = manager.getLightLevel(light, x, y, z);
    	
		if (lightLevel < targetLevel) {
			manager.setLightLevel(light, x, y, z, targetLevel);
		}
		return true;
	}
}
