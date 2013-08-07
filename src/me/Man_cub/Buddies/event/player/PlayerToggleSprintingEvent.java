package me.Man_cub.Buddies.event.player;

import org.spout.api.entity.Player;
import org.spout.api.event.Cancellable;
import org.spout.api.event.HandlerList;
import org.spout.api.event.player.AbstractPlayerEvent;

public class PlayerToggleSprintingEvent extends AbstractPlayerEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private final boolean isSprinting;

	public PlayerToggleSprintingEvent(Player p, boolean isSprinting) {
		super(p);
		this.isSprinting = isSprinting;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	/**
	 * Returns the new state of sprinting
	 */
	public boolean isSprinting() {
		return isSprinting;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
