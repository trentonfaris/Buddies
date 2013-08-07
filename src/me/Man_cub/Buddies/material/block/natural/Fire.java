package me.Man_cub.Buddies.material.block.natural;

import java.util.Random;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.material.BuddiesBlockMaterial;
import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.material.Burnable;

import org.spout.api.event.Cause;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.BlockMaterial;
import org.spout.api.material.DynamicMaterial;
import org.spout.api.material.block.BlockFace;
import org.spout.api.material.block.BlockFaces;
import org.spout.api.material.range.CuboidEffectRange;
import org.spout.api.material.range.EffectRange;
import org.spout.api.math.IntVector3;

public class Fire extends BuddiesBlockMaterial implements DynamicMaterial {
	private static final EffectRange SPREAD_RANGE = new CuboidEffectRange(-1, -1, -1, 1, 4, 1);
	
	@SuppressWarnings("unchecked")
	public Fire(String name, int id) {
		super(name, id, BuddiesMaterialModels.FIRE, null);
		this.setTransparent();
	}
	
	@Override
	public boolean hasPhysics() {
		return true;
	}
	
	@Override
	public byte getLightLevel(short data) {
		return 15;
	}
	
	@Override
	public void onUpdate(BlockMaterial oldMaterial, Block block) {
		super.onUpdate(oldMaterial, block);
		Cause<?> cause = toCause(block);
		if (!this.canCreate(block, block.getBlockData(), cause)) {
			this.onDestroy(block, cause);
		}
	}

	@Override
	public boolean canCreate(Block block, short data, Cause<?> cause) {
		if (super.canCreate(block, data, cause)) {
			BlockMaterial mat;
			for (BlockFace face : BlockFaces.BTNSWE) {
				mat = block.translate(face).getMaterial();
				if (mat instanceof BuddiesBlockMaterial) {
					if (((BuddiesBlockMaterial) mat).canSupport(this, face.getOpposite())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isPlacementObstacle() {
		return false;
	}

	@Override
	public EffectRange getDynamicRange() {
		return SPREAD_RANGE;
	}

	/**
	 * Checks if the given fire block has a burn source at a certain face<br>
	 * It checks if the fire has a {@link org.spout.vanilla.api.material.Burnable} block at the face
	 * @param block of the fire
	 * @param to the face the source is
	 * @return True if it has a source there, False if not
	 */
	public boolean hasBurningSource(Block block, BlockFace to) {
		BlockMaterial material = block.translate(to).getMaterial();
		return material instanceof Burnable && ((Burnable) material).getBurnPower() > 0;
	}

	/**
	 * Checks if the given fire block has a burn source<br>
	 * It checks if the fire has a {@link org.spout.vanilla.api.material.Burnable} block nearby
	 * @param block of the fire
	 * @return True if it has a source, False if not
	 */
	public boolean hasBurningSource(Block block) {
		for (BlockFace face : BlockFaces.NESWBT) {
			if (hasBurningSource(block, face)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onFirstUpdate(Block block, long currentTime) {
		block.dynamicUpdate(currentTime + 1000 + (new Random()).nextInt(2000), true);
	}

	@Override
	public void onDynamicUpdate(Block b, long updateTime, int data) {
		Random rand = new Random();

		// Make fire strength increase over time
		short blockData = b.getBlockData();
		if (blockData < 15) {
			blockData += rand.nextInt(4) / 3;
			b.setData(blockData);
		}

		// Fires without source burn less long, rain fades out fire
		if (BuddiesBlockMaterial.hasRainNearby(b) || (!hasBurningSource(b) && blockData > 3)) {
			this.onDestroy(b, null);
			return;
		}
			
		// If fire is done with and the block below can not fuel fire, destroy
		if (blockData == 15 && rand.nextInt(4) == 0 && !hasBurningSource(b, BlockFace.BOTTOM)) {
			this.onDestroy(b, null);
			return;
		}

		// Try to instantly combust surrounding blocks
		Block sBlock;
		for (BlockFace face : BlockFaces.NESWBT) {
			int chance = BlockFaces.TB.contains(face) ? 250 : 300;
			sBlock = b.translate(face);
			BlockMaterial mat = sBlock.getMaterial();
			if (mat instanceof Burnable && rand.nextInt(chance) < ((Burnable) mat).getCombustChance()) {
				// Destroy the old block
				/*
				if (mat instanceof TntBlock) {
					((TntBlock) mat).onIgnite(sBlock, toCause(b)); // Ignite TntBlock
				} else {*/
				sBlock.setMaterial(BuddiesMaterials.AIR); // prevent drops
				//}
				// Put fire in it's place?
				if (rand.nextInt(blockData + 10) < 5 && hasBurningSource(sBlock) && !BuddiesBlockMaterial.isRaining(sBlock)) {
					sBlock.setMaterial(this, Math.min(15, blockData + rand.nextInt(5) / 4));
				}
			}
		}

		// Spreading component
		int chanceFactor, firePower, netChance;
		for (IntVector3 offset : SPREAD_RANGE) {

			// Don't spread to the middle or to non-air and other fire blocks
			if (offset.getX() == 0 && offset.getY() == 0 && offset.getZ() == 0) {
				continue;
			}
			sBlock = b.translate(offset);
			if (!sBlock.isMaterial(BuddiesMaterials.AIR)) {
				continue;
			}

			// Get power level for this fire
			firePower = 0;
			for (BlockFace face : BlockFaces.NESWBT) {
				BlockMaterial mat = sBlock.translate(face).getMaterial();
				if (mat instanceof Burnable) {
					firePower = Math.max(firePower, ((Burnable) mat).getBurnPower());
				}
			}
			if (firePower == 0) {
				continue;
			}

			// Calculate and use the net chance of spreading
			// Higher blocks have a lower chance of spreading
			// Don't spread if it has rain falling nearby
			if (offset.getY() > 1) {
				chanceFactor = (int) (offset.getY() * 100);
			} else {
				chanceFactor = 100;
			}
			netChance = (firePower + 40) / (blockData + 30);
			if (netChance <= 0 || rand.nextInt(chanceFactor) > netChance || BuddiesBlockMaterial.hasRainNearby(sBlock)) {
				continue;
			}

			// Set new fire block with randomly increasing power (1/4 chance for +1 for fire power)
			sBlock.setMaterial(this, Math.min(15, blockData + rand.nextInt(5) / 4));
		}

		// Schedule for a next update
		b.dynamicUpdate(b.getWorld().getAge() + 2000, true);
	}
}
