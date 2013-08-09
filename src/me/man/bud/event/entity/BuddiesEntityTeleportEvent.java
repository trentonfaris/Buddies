package me.man_cub.buddies.event.entity;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntityTeleportEvent;
import org.spout.api.geo.discrete.Point;

public class BuddiesEntityTeleportEvent extends EntityTeleportEvent {
	private static final HandlerList handlers = new HandlerList();
	private TeleportReason reason;

	public BuddiesEntityTeleportEvent(Entity e, Point from, Point to, TeleportReason reason) {
		super(e, from, to);
		this.reason = reason;
	}

	/**
	 * Gets the reason behind the teleport of the entity.
	 *
	 * @return The reason behind the teleport.
	 */
	public TeleportReason getReason() {
		return reason;
	}

	/**
	 * Sets the reason for the teleport of the entity.
	 *
	 * @param reason The new reason for the teleport of the entity.
	 */
	public void setReason(TeleportReason reason) {
		this.reason = reason;
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

	/**
	 * An enum to specify the reason behind teleports.
	 */
	public enum TeleportReason {
		/**
		 * Teleportation via a portal
		 */
		PORTAL,
		/**
		 * Teleportation due to a custom reason (normally a plugin).
		 */
		CUSTOM
	}

}
