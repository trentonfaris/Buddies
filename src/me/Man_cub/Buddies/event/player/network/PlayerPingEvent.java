package me.Man_cub.Buddies.event.player.network;

import org.spout.api.event.Event;
import org.spout.api.event.HandlerList;
import org.spout.api.protocol.event.ProtocolEvent;

public class PlayerPingEvent extends Event implements ProtocolEvent {
	private static HandlerList handlers = new HandlerList();
	private int hash;

	public PlayerPingEvent(int hash) {
		this.hash = hash;
	}

	/**
	 * Gets the Hash code for this ping message
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
