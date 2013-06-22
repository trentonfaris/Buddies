package me.Man_cub.Buddies.component.entity.substance.object;

import me.Man_cub.Buddies.material.BuddiesBlockMaterial;

import org.spout.api.collision.CollisionStrategy;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.discrete.Point;
import org.spout.api.inventory.ItemStack;
import org.spout.api.material.BlockMaterial;
import org.spout.api.math.GenericMath;

public class FallingBlock extends Substance {
	private static float FALL_INCREMENT = -0.04F;
	private static float FALL_MULTIPLIER = 0.98F;
	private BuddiesBlockMaterial material;
	private float fallSpeed = 0F;
	
	@Override
	public void onAttached() {
		//getOwner().getNetwork().setEntityProtocol(BuddiesPlugin.BUDDIES_PROTOCOL_ID, new FallingBlockProtocol(ObjectType.FALLING_OBJECT));
	}
	
	public void setMaterial(BuddiesBlockMaterial material) {
		this.material = material;
	}
	
	public BuddiesBlockMaterial getMaterial() {
		return material;
	}
	
	@Override
	public void onTick(float dt) {
		Point pos = this.getOwner().getScene().getPosition();
		World world = pos.getWorld();
		int x = pos.getBlockX();
		int y = pos.getBlockY();
		int z = pos.getBlockZ();
		int fallAmt = Math.max(1, GenericMath.floor(Math.abs(fallSpeed)));
		for (int dy = 0; dy < fallAmt; dy++) {
			BlockMaterial below = world.getBlockMaterial(x, y - dy, z);
			if (isFallingObstacle(below)) {
				// Place block on top of this obstacle, if possible
				Block current = world.getBlock(x, y - dy + 1, z);
				BlockMaterial currentMat = current.getMaterial();
				if (!(currentMat instanceof BuddiesBlockMaterial) || !currentMat.isPlacementObstacle()) {
					// Place in the world
					current.setMaterial(getMaterial(), getMaterial().toCause(pos));
				} else {
					// Spawn drops
					Item.dropNaturally(pos, new ItemStack(getMaterial(), 1));
				}
				this.getOwner().remove();
			}
		}
		if (!this.getOwner().isRemoved()) {
			fallSpeed += (FALL_INCREMENT * dt * 20);
			this.getOwner().getScene().setPosition(pos.add(0, fallSpeed, 0F));
			fallSpeed *= FALL_MULTIPLIER;
		}
	}

	public float getFallingSpeed() {
		return fallSpeed;
	}

	/**
	 * Whether the material can stop a falling block from falling, acting as the new ground block
	 * @param material
	 * @return True if it obstructs further falling, False if not
	 */
	public static boolean isFallingObstacle(BlockMaterial material) {
		if (material == BlockMaterial.AIR) {
			return false;
		}
		if (material.getVolume().getStrategy() != CollisionStrategy.SOLID) {
			return false;
		}
		if (material instanceof BuddiesBlockMaterial) {
			BuddiesBlockMaterial vbm = (BuddiesBlockMaterial) material;
			if (!vbm.isPlacementObstacle()) {
				return false;
			}
		}
		return true;
	}

}
