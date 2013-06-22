package me.Man_cub.Buddies.event.player;

import org.spout.api.entity.Player;
import org.spout.api.event.Cancellable;
import org.spout.api.event.HandlerList;
import org.spout.api.event.player.PlayerEvent;

public class PlayerToggleSneakingEvent extends PlayerEvent implements Cancellable {
	private static HandlerList handlers = new HandlerList();
	private boolean isSneaking;

	public PlayerToggleSneakingEvent(Player p, boolean isSneaking) {
		super(p);
		this.isSneaking = isSneaking;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	/**
	 * Returns the new state of sneaking
	 * @return
	 */
	public boolean isSneaking() {
		return isSneaking;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
