package me.Man_cub.Buddies.event.player;

import me.Man_cub.Buddies.event.cause.DamageCause;
import me.Man_cub.Buddies.event.entity.EntityDeathEvent;

import org.spout.api.entity.Player;
import org.spout.api.event.HandlerList;

public class PlayerDeathEvent extends EntityDeathEvent {
	private static final HandlerList handlers = new HandlerList();

	public PlayerDeathEvent(Player player) {
		super(player);
	}

	public PlayerDeathEvent(Player player, DamageCause<?> lastDamageCause, Object lastDamager) {
		super(player, lastDamageCause, lastDamager);
	}

	/**
	 * Gets the player associated in this event.
	 *
	 * @return The player.
	 */
	public Player getPlayer() {
		return (Player) getEntity();
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
