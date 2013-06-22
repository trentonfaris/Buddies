package me.Man_cub.Buddies.material.block;

import org.spout.api.event.Cause;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockFace;

public class GroundAttachable extends AbstractAttachable {
	
	public GroundAttachable(String name, int id, String model) {
		super(name, id, model);
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
