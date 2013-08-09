package me.man_cub.buddies.material.block;

import org.spout.api.event.Cause;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockFace;
import org.spout.physics.collision.shape.CollisionShape;

public class GroundAttachable extends AbstractAttachable {
	
	@SuppressWarnings("unchecked")
	public GroundAttachable(short dataMask, String name, int id, String model, CollisionShape shape) {
		super(dataMask, name, id, model, shape);
		this.setAttachable(BlockFace.BOTTOM);
	}
	
	@SuppressWarnings("unchecked")
	public GroundAttachable(String name, int id, String model, CollisionShape shape) {
		super(name, id, model, shape);
		this.setAttachable(BlockFace.BOTTOM);
	}

	@Override
	public boolean canSeekAttachedAlternative() {
		return true;
	}

	@Override
	public void setAttachedFace(Block block, BlockFace attachedFace, Cause<?> cause) {
	}

	@Override
	public BlockFace getAttachedFace(short data) {
		return BlockFace.BOTTOM;
	}

}
