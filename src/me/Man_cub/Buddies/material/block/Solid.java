package me.Man_cub.Buddies.material.block;

import me.Man_cub.Buddies.material.BuddiesBlockMaterial;
import me.Man_cub.Buddies.material.BuddiesMaterials;

import org.spout.api.material.BlockMaterial;
import org.spout.api.material.block.BlockFace;
import org.spout.physics.collision.shape.BoxShape;

public class Solid extends BuddiesBlockMaterial {
	
	public Solid(String name, int id, String model) {
		this((short) 0, name, id, model);
		setOpaque();
	}
	
	@SuppressWarnings("unchecked")
	public Solid(short dataMask, String name, int id, String model) {
		super(dataMask, name, id, model, new BoxShape(1, 1, 1));
		setOpaque();
	}

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
