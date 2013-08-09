package me.man_cub.buddies.event.entity;

import me.man_cub.buddies.event.cause.LivingSpawnCause;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntitySpawnEvent;
import org.spout.api.geo.discrete.Point;

public class LivingSpawnEvent extends EntitySpawnEvent {
	private static final HandlerList handlers = new HandlerList();
	private final LivingSpawnCause cause;

	public LivingSpawnEvent(Entity e, Point point) {
		super(e, point);
		this.cause = LivingSpawnCause.UNKNOWN;
	}

	public LivingSpawnEvent(Entity e, Point point, LivingSpawnCause cause) {
		super(e, point);
		this.cause = cause;
	}

	/**
	 * Gets the {@link LivingSpawnCause} of this spawn.
	 * @return cause
	 */
	public LivingSpawnCause getSpawnCause() {
		return cause;
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
