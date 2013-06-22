package me.Man_cub.Buddies.event.player.network;

import org.spout.api.entity.Player;
import org.spout.api.event.HandlerList;
import org.spout.api.event.player.PlayerEvent;
import org.spout.api.protocol.event.ProtocolEvent;

public class PlayerHealthEvent extends PlayerEvent implements ProtocolEvent {
	private static HandlerList handlers = new HandlerList();

	public PlayerHealthEvent(Player p) {
		super(p);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
