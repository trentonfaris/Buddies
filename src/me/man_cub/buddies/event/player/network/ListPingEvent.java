package me.man_cub.buddies.event.player.network;

import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;

public class ListPingEvent extends ProtocolEvent {
	private static final HandlerList handlers = new HandlerList();
	private final long ping;
	private final boolean online;
	private final String playerDisplayName;

	public ListPingEvent(String playerDisplayName, long pingDelayMS, boolean online) {
		this.ping = pingDelayMS;
		this.online = online;
		this.playerDisplayName = playerDisplayName;
	}

	/**
	 * Gets the name of the player that this event relates to
	 *
	 * @return the player's name
	 */
	public String getPlayerDisplayName() {
		return this.playerDisplayName;
	}

	/**
	 * Gets the player's online status
	 *
	 * @return true if the player is online
	 */
	public boolean getOnline() {
		return this.online;
	}

	/**
	 * Gets the network delay between the server and the player
	 *
	 * @return true if the player is online
	 */
	public long getPingDelay() {
		return this.ping;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
