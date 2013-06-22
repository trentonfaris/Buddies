package me.Man_cub.Buddies.material.block;

import me.Man_cub.Buddies.material.BuddiesBlockMaterial;
import me.Man_cub.Buddies.material.BuddiesMaterials;

import org.spout.api.collision.CollisionStrategy;
import org.spout.api.material.BlockMaterial;
import org.spout.api.material.block.BlockFace;

public class Solid extends BuddiesBlockMaterial {
	
	public Solid(String name, int id, String model) {
		super(name, id, model);
		this.setCollision(CollisionStrategy.SOLID).setOpaque();
	}

	/* Look into this
	@Override
	public MoveReaction getMoveReaction(Block block) {
		return MoveReaction.ALLOW;
	}*/

	@Override
	public boolean canSupport(BlockMaterial material, BlockFace face) {
		// Solids only support fire on top
		if (material.isMaterial(BuddiesMaterials.FIRE)) {
			return face == BlockFace.TOP;
		}

		// Anything else is supported to all sides
		return true;
	}

}
