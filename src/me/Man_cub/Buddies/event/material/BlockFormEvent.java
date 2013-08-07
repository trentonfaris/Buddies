package me.Man_cub.Buddies.event.material;

import org.spout.api.event.Cancellable;
import org.spout.api.event.Cause;
import org.spout.api.event.HandlerList;
import org.spout.api.event.block.BlockChangeEvent;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockSnapshot;

public class BlockFormEvent extends BlockChangeEvent implements Cancellable {
	/**
	 * The different causes why a Block is formed in the world.
	 */
	public static enum FormCause {
		/**
		 * Block spread randomly
		 */
		SPREAD_RANDOM,
		/**
		 * Block spread
		 */
		SPREAD,
		/**
		 * Block formed due to world conditions (for example Snow)
		 */
		FORMING,
	}

	private static final HandlerList handlers = new HandlerList();
	private final FormCause formCause;

	public BlockFormEvent(Block block, BlockSnapshot newState, Cause<?> reason, FormCause formCause) {
		super(block, newState, reason);
		this.formCause = formCause;
	}

	/**
	 * The reason why the block formed
	 *
	 * @return FormCause
	 */
	public FormCause getFormCause() {
		return formCause;
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
