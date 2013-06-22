package me.Man_cub.Buddies.event.block;

import org.spout.api.event.Event;
import org.spout.api.event.HandlerList;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.protocol.event.ProtocolEvent;

public class BlockControllerDataEvent extends Event implements ProtocolEvent {
	private static HandlerList handlers = new HandlerList();
	private final Block block;
	private int action;
	private int[] data;

	/**
	 * Constructs a new Data event for the block specified
	 * @param block for the data
	 * @param action to perform
	 * @param data to use, max 3 elements
	 */
	public BlockControllerDataEvent(Block block, int action, int... data) {
		if (data.length > 3) {
			throw new IllegalArgumentException("Too many data values: " + data.length + " (maximum is 3)");
		}
		this.block = block;
		this.action = action;
		this.data = data;
	}

	/**
	 * Gets the Block the data is meant for
	 * @return Block
	 */
	public Block getBlock() {
		return this.block;
	}

	/**
	 * Gets the action to perform
	 * @return action Id
	 */
	public int getAction() {
		return this.action;
	}

	/**
	 * Gets the data to use
	 * @return data array
	 */
	public int[] getData() {
		return this.data;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
