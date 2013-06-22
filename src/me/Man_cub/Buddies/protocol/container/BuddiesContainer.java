package me.Man_cub.Buddies.protocol.container;

import org.spout.api.component.BlockComponentOwner;
import org.spout.api.geo.cuboid.BlockComponentContainer;
import org.spout.api.geo.cuboid.BlockContainer;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.geo.cuboid.ContainerFillOrder;
import org.spout.api.material.BlockMaterial;
import org.spout.api.material.block.BlockFullState;
import org.spout.api.util.cuboid.CuboidNibbleLightBuffer;

import static me.Man_cub.Buddies.material.BuddiesMaterials.getBuddiesId;

public class BuddiesContainer implements BlockContainer, BlockComponentContainer {
	private static final int HALF_VOLUME = Chunk.BLOCKS.HALF_VOLUME;
	private static final int VOLUME = Chunk.BLOCKS.VOLUME;
	private int index;
	private final byte[] fullChunkData;
	private BlockMaterial material1;
	private BlockComponentOwner[] components;
	private int[] componentX;
	private int[] componentY;
	private int[] componentZ;

	public BuddiesContainer() {
		fullChunkData = new byte[HALF_VOLUME * 5];
		index = 0;
	}

	@Override
	public ContainerFillOrder getOrder() {
		return ContainerFillOrder.XZY;
	}

	@Override
	public void setBlockFullState(int state) {
		short id = BlockFullState.getId(state);
		short data = BlockFullState.getData(state);
		if ((index & 1) == 0) {
			material1 = BlockMaterial.get(id, data);
		} else {
			BlockMaterial material2 = BlockMaterial.get(id, data);
			fullChunkData[index - 1] = (byte) (getBuddiesId(material1) & 0xFF);
			fullChunkData[index] = (byte) (getBuddiesId(material2) & 0xFF);
			fullChunkData[(index >> 1) + VOLUME] = (byte) 0;
		}
		index++;
	}

	public void setLightMode(boolean blockLight) {
		if (blockLight) {
			index = HALF_VOLUME * 3;
		} else {
			index = HALF_VOLUME * 4;
		}
	}

	public void copyLight(boolean blockLight, CuboidNibbleLightBuffer buffer) {
		int index = blockLight ? HALF_VOLUME * 3 : HALF_VOLUME * 4;
		buffer.copyToArray(fullChunkData, index);
	}
	
	public byte[] getChunkFullData() {
		return fullChunkData;
	}

	@Override
	public void setBlockComponent(int x, int y, int z, BlockComponentOwner component) {
		components[index] = component;
		componentX[index] = x;
		componentY[index] = y;
		componentZ[index] = z;
		index++;
	}

	@Override
	public void setBlockComponentCount(int count) {
		components = new BlockComponentOwner[count];
		componentX = new int[count];
		componentY = new int[count];
		componentZ = new int[count];

		index = 0;
	}

	public BlockComponentOwner[] getBlockComponent() {
		return components;
	}

	public int[] getXArray() {
		return componentX;
	}

	public int[] getYArray() {
		return componentY;
	}

	public int[] getZArray() {
		return componentZ;
	}

	@Override
	public int getBlockComponentCount() {
		return components.length;
	}
	
}
