package me.Man_cub.Buddies.event.material;

import org.spout.api.event.Cancellable;
import org.spout.api.event.Cause;
import org.spout.api.event.HandlerList;
import org.spout.api.event.block.BlockChangeEvent;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockSnapshot;

public class BlockDisappearEvent extends BlockChangeEvent implements Cancellable {
	/**
	 * The different causes why a Block disappears from the world.
	 */
	public static enum DisappearCause {
		/**
		 * Block faded
		 */
		FADE,
		/**
		 * Block melted
		 */
		MELT,
		/**
		 * Block disappeared
		 */
		DISAPPEAR,
		/**
		 * Block decayed
		 */
		DECAY,
	}

	private static final HandlerList handlers = new HandlerList();
	private final DisappearCause disappearCause;

	public BlockDisappearEvent(Block block, BlockSnapshot newState, Cause<?> reason, DisappearCause disappearCause) {
		super(block, newState, reason);
		this.disappearCause = disappearCause;
	}

	/**
	 * The reason why the block has disappeared
	 *
	 * @return DisappearCause
	 */
	public DisappearCause getDisappearCause() {
		return disappearCause;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
