package me.Man_cub.Buddies.event.player.network;

import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;

public class PingEvent extends ProtocolEvent {
	private static final HandlerList handlers = new HandlerList();
	private final int hash;

	public PingEvent(int hash) {
		this.hash = hash;
	}

	/**
	 * Gets the Hash code for this ping message
	 *
	 * @return ping unique code
	 */
	public int getHash() {
		return this.hash;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
