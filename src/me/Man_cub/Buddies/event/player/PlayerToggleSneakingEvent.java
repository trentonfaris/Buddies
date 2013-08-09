package me.man_cub.buddies.event.player;

import org.spout.api.entity.Player;
import org.spout.api.event.Cancellable;
import org.spout.api.event.HandlerList;
import org.spout.api.event.player.AbstractPlayerEvent;

public class PlayerToggleSneakingEvent extends AbstractPlayerEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private final boolean isSneaking;

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
