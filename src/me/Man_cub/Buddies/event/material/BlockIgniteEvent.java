package me.Man_cub.Buddies.event.material;

import org.spout.api.event.Cancellable;
import org.spout.api.event.Cause;
import org.spout.api.event.HandlerList;
import org.spout.api.event.block.BlockChangeEvent;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockSnapshot;

public class BlockIgniteEvent extends BlockChangeEvent implements Cancellable {
	/**
	 * The different causes why a Block was ignited.
	 */
	public static enum IgniteCause {
		/**
		 * Block ignition caused by Lava
		 */
		LAVA,
		/**
		 * Block ignition caused by using the Lightener
		 */
		FLINT_AND_STEEL,
		/**
		 * Block ignition caused by dynamic spread of fire
		 */
		SPREAD,
		/**
		 * Block ignition caused by lightning
		 */
		LIGHTING,
		/**
		 * Block ignition caused by a fireball
		 */
		FIREBALL,
	}

	private static final HandlerList handlers = new HandlerList();
	private final IgniteCause igniteCause;

	public BlockIgniteEvent(Block block, BlockSnapshot newState, Cause<?> reason, IgniteCause igniteCause) {
		super(block, newState, reason);
		this.igniteCause = igniteCause;
	}

	/**
	 * The reason why the block was ignited
	 *
	 * @return IgniteCause
	 */
	public IgniteCause getIgniteCause() {
		return igniteCause;
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
