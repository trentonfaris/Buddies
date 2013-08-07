package me.Man_cub.Buddies.event.player.network;

import org.spout.api.entity.Player;
import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.event.player.PlayerEvent;

public class PlayerHealthEvent extends ProtocolEvent implements PlayerEvent {
	private static final HandlerList handlers = new HandlerList();
	private final Player player;

	public PlayerHealthEvent(Player p) {
		this.player = p;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
