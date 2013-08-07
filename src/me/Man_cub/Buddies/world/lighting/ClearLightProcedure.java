package me.Man_cub.Buddies.world.lighting;

import org.spout.api.util.cuboid.ChunkCuboidLightBufferWrapper;
import org.spout.api.util.set.TInt10Procedure;

public class ClearLightProcedure extends TInt10Procedure {
	private final ChunkCuboidLightBufferWrapper<BuddiesCuboidLightBuffer> light;
	private final BuddiesLightingManager manager;

	public ClearLightProcedure(BuddiesLightingManager manager, ChunkCuboidLightBufferWrapper<BuddiesCuboidLightBuffer> light) {
		this.light = light;
		this.manager = manager;
	}

	@Override
	public boolean execute(int x, int y, int z) {
		return execute(x, y, z, false);
	}

	public boolean execute(int x, int y, int z, boolean root) {
		// Spout.getLogger().info("Clearing for " + x + ", " + y + ", " + z);
		manager.setLightLevel(light, x, y, z, 0);
		return true;
	}

}
