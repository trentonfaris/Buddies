package me.Man_cub.Buddies.event.entity;

import me.Man_cub.Buddies.event.cause.HealthChangeCause;

import org.spout.api.entity.Entity;
import org.spout.api.event.Cancellable;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.AbstractEntityEvent;

public class EntityHealthChangeEvent extends AbstractEntityEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private int change;
	private final HealthChangeCause cause;

	public EntityHealthChangeEvent(Entity e, HealthChangeCause cause, int change) {
		super(e);
		this.cause = cause;
		this.change = change;
	}

	/**
	 * Gets the cause of the event.
	 * @return The source that caused this event.
	 */
	public HealthChangeCause getCause() {
		return cause;
	}

	/**
	 * Gets the change in health.
	 * @return The amount of change.
	 */
	public int getChange() {
		return change;
	}

	/**
	 * Sets the change in health.
	 * @param damage The amount of change.
	 */
	public void setChange(int change) {
		this.change = change;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
