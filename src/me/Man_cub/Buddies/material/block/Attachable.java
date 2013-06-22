package me.Man_cub.Buddies.material.block;

import org.spout.api.event.Cause;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockFace;

public interface Attachable {
	/**
	 * Checks if this material can be attached to certain face of a block material
	 * @param block to attach to
	 * @param face of the material to attach to
	 * @return if this material can be attached to face of the block material given
	 */
	public boolean canAttachTo(Block block, BlockFace face);

	/**
	 * Sets the face the block is attached to
	 * @param block to set
	 * @param attachedFace to set the block to
	 * @param cause of the attachment
	 */
	public void setAttachedFace(Block block, BlockFace attachedFace, Cause<?> cause);

	/**
	 * Gets the face the block is attached to
	 * @param block to get it of
	 * @return to which face the block is attached to
	 */
	public BlockFace getAttachedFace(Block block);

	/**
	 * Gets the face the block is attached to
	 * @param data of the block
	 * @return to which face the block is attached to
	 */
	public BlockFace getAttachedFace(short data);

	/**
	 * Returns the block that this attachable is attached to
	 * @param block of this attachable
	 * @return the block
	 */
	public Block getBlockAttachedTo(Block block);
}
