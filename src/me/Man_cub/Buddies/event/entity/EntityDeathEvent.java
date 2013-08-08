package me.Man_cub.Buddies.event.entity;

import me.Man_cub.Buddies.event.cause.DamageCause;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.AbstractEntityEvent;

public class EntityDeathEvent extends AbstractEntityEvent {
	private static final HandlerList handlers = new HandlerList();
	private DamageCause<?> lastDamageCause;
	private Object lastDamager;

	public EntityDeathEvent(Entity e) {
		super(e);
	}
	
	public EntityDeathEvent(Entity e, DamageCause<?> lastDamageCause, Object lastDamager) {
		super(e);
		this.lastDamageCause = lastDamageCause;
		this.lastDamager = lastDamager;
	}
	
	/**
	 * Gets the last damage cause.
	 * @return The last damage cause.
	 */
	public DamageCause<?> getLastDamageCause() {
		return lastDamageCause;
	}

	/**
	 * Sets the last damage cause.
	 * @param lastDamageCause The last damage cause to set.
	 */
	public void setLastDamageCause(DamageCause<?> lastDamageCause) {
		this.lastDamageCause = lastDamageCause;
	}

	/**
	 * Gets the last damager.
	 * @return the last damager.
	 */
	public Object getLastDamager() {
		return lastDamager;
	}

	/**
	 * Sets the last damager.
	 * @param lastDamager the last damager to set.
	 */
	public void setLastDamager(Object lastDamager) {
		this.lastDamager = lastDamager;
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
