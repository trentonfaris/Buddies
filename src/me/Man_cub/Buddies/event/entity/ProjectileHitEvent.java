package me.Man_cub.Buddies.event.entity;

import org.spout.api.entity.Entity;
import org.spout.api.event.Cancellable;
import org.spout.api.event.Cause;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntityEvent;

public class ProjectileHitEvent extends EntityEvent implements Cancellable {
	private static HandlerList handlers = new HandlerList();
	private Cause<?> cause;

	public ProjectileHitEvent(Entity e, Cause<?> cause) {
		super(e);
		this.cause = cause;
	}

	/**
	 * Gets the cause of this event.
	 * @return cause
	 */
	public Cause<?> getCause() {
		return cause;
	}

	/**
	 * Sets the cause of this event.
	 * @param cause
	 */
	public void setCause(Cause<?> cause) {
		this.cause = cause;
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