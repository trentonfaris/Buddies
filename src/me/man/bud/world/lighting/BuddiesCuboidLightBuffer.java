package me.man_cub.buddies.world.lighting;

import org.spout.api.lighting.Modifiable;
import org.spout.api.util.cuboid.AlignedCuboidNibbleLightBuffer;

public class BuddiesCuboidLightBuffer extends AlignedCuboidNibbleLightBuffer {
	protected BuddiesCuboidLightBuffer(BuddiesCuboidLightBuffer buffer) {
		super(buffer);
	}

	protected BuddiesCuboidLightBuffer(Modifiable holder, short id, int baseX, int baseY, int baseZ, int sizeX, int sizeY, int sizeZ) {
		super(holder, id, baseX, baseY, baseZ, sizeX, sizeY, sizeZ);
	}

	protected BuddiesCuboidLightBuffer(Modifiable holder, short id, int baseX, int baseY, int baseZ, int sizeX, int sizeY, int sizeZ, byte[] data) {
		super(holder, id, baseX, baseY, baseZ, sizeX, sizeY, sizeZ, data);
	}

	@Override
	public BuddiesCuboidLightBuffer copy() {
		return new BuddiesCuboidLightBuffer(this);
	}

}
