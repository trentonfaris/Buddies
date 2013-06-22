package me.Man_cub.Buddies.event.player;

import org.spout.api.entity.Player;
import org.spout.api.event.Cancellable;
import org.spout.api.event.HandlerList;
import org.spout.api.event.player.PlayerEvent;

public class PlayerToggleSprintingEvent extends PlayerEvent implements Cancellable {
	private static HandlerList handlers = new HandlerList();
	private boolean isSprinting;

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
	 * @return
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
