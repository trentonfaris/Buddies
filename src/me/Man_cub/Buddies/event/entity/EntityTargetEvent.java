package me.Man_cub.Buddies.event.entity;

import org.spout.api.entity.Entity;
import org.spout.api.event.Cancellable;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntityEvent;

public class EntityTargetEvent extends EntityEvent implements Cancellable {
	private static HandlerList handlers = new HandlerList();
	private TargetCause cause;
	private Entity target = null;

	public EntityTargetEvent(Entity e, TargetCause cause) {
		super(e);
		this.cause = cause;
	}

	public EntityTargetEvent(Entity e, TargetCause cause, Entity target) {
		this(e, cause);
		this.target = target;
	}

	/**
	 * Gets the cause for targeting.
	 * @return the target cause
	 */
	public TargetCause getCause() {
		return cause;
	}

	/**
	 * Sets the cause for the targeting.
	 * @param cause The reason for the targeting
	 */
	public void setCause(TargetCause cause) {
		this.cause = cause;
	}

	/**
	 * Gets the new target.
	 * @return target or null
	 */
	public Entity getTarget() {
		return target;
	}

	/**
	 * Sets the new target.
	 * @param target
	 */
	public void setTarget(Entity target) {
		this.target = target;
	}

	/**
	 * Whether this is a targeting or untargeting event.
	 * @return true if the entity is targeting, false if it is untargeting
	 */
	public boolean isTargeting() {
		return cause.isTarget();
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
	 * Represents a target change cause for an EntityTargetEvent.
	 */
	public enum TargetCause {
		TARGET_DIED(false),
		CLOSEST_PLAYER(true),
		TARGET_ATTACKED_ENTITY(true),
		FORGOT_TARGET(false),
		OWNER_ATTACKED(true),
		RANDOM_TARGET(true),
		CUSTOM_TARGET(true),
		CUSTOM_UNTARGET(false);
		private boolean target;

		private TargetCause(boolean target) {
			this.target = target;
		}

		/**
		 * Whether this is targeting or untargeting.
		 * @return true if targeting, false if untargeting
		 */
		public boolean isTarget() {
			return target;
		}
	}
}
