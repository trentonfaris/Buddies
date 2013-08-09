package me.man_cub.buddies.material.block;

import me.man_cub.buddies.component.entity.substance.object.FallingBlock;
import me.man_cub.buddies.material.BuddiesMaterials;

import org.spout.api.entity.Entity;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.BlockMaterial;
import org.spout.api.material.block.BlockFace;
import org.spout.api.util.cuboid.CuboidBlockMaterialBuffer;

public class SolidMoving extends Solid {

	public SolidMoving(String name, int id, String model) {
		super(name, id, model);
	}

	@Override
	public boolean hasPhysics() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onUpdate(BlockMaterial oldMaterial, Block block) {
		super.onUpdate(oldMaterial, block);
		if (!block.translate(BlockFace.BOTTOM).getMaterial().isPlacementObstacle()) {
			// turn this block into a mobile block
			Entity e = block.getWorld().createAndSpawnEntity(block.getPosition(), LoadOption.NO_LOAD, FallingBlock.class);
			e.add(FallingBlock.class).setMaterial(this);
			block.setMaterial(BuddiesMaterials.AIR);
		}
	}

	/**
	 * Simulates the result of a blocks falling inside a buffer. If the blocks
	 * reach the bottom of the buffer without hitting any obstacles they may
	 * either be removed or stopped at the bottom. If the block at (x, y, z) is
	 * not a SolidMoving, nothing will happen, else it and the blocks above will
	 * be subject to the simulation. If the blocks fall on a placement obstacle
	 * (such as a torch) they will be removed.
	 *
	 * @param buffer The buffer in which to simulate the fall.
	 * @param x The x coordinate of the block.
	 * @param y The y coordinate of the block.
	 * @param z The Z coordinate of the block.
	 * @param remove If the blocks should be removed after reaching the bottom
	 * of the buffer, or stopped.
	 */
	public static void simulateFall(CuboidBlockMaterialBuffer buffer, int x, int y, int z, boolean remove) {
		//if (!buffer.isInside(x, y, z)) {
		//	return;
		//}
		BlockMaterial falling = buffer.get(x, y, z);
		if (!(falling instanceof SolidMoving)) {
			return;
		}
		int baseY = buffer.getBase().getFloorY();
		for (int obstacleY = y; --obstacleY >= baseY;) {
			if (FallingBlock.isFallingObstacle(buffer.get(x, obstacleY, z))) {
				// obstacle found
				if (obstacleY == y - 1) {
					// right underneath, nowhere to fall
					return;
				}
				if (buffer.get(x, ++obstacleY, z).isPlacementObstacle()) {
					// blocks can't stay here. Delete them
					remove = true;
					break;
				}
				do {
					// move the blocks above the obstacle
					buffer.set(x, obstacleY++, z, falling);
					buffer.set(x, y++, z, BuddiesMaterials.AIR);
				} while ((falling = buffer.get(x, y, z)) instanceof SolidMoving);
				return;
			}
		}
		// no obstacle found
		if (remove) {
			// delete the blocks
			final int topY = buffer.getTop().getFloorY() - 1;
			do {
				buffer.set(x, y++, z, BuddiesMaterials.AIR);
			} while (y <= topY && buffer.get(x, y, z) instanceof SolidMoving);
		} else {
			// move the blocks to the bottom of the buffer
			do {
				buffer.set(x, baseY++, z, falling);
				buffer.set(x, y++, z, BuddiesMaterials.AIR);
			} while ((falling = buffer.get(x, y, z)) instanceof SolidMoving);
		}
	}

}
