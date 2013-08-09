package me.man_cub.buddies.event.entity;

import me.man_cub.buddies.component.entity.living.buddy.Buddy;

import org.spout.api.event.Cancellable;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.AbstractEntityEvent;

public class BuddyAbilityChangeEvent extends AbstractEntityEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private final float flyingSpeed;
	private final float walkingSpeed;
	private final boolean isFlying;
	private final boolean canFly;

	public BuddyAbilityChangeEvent(Buddy buddy) {
		super(buddy.getOwner());
		flyingSpeed = buddy.getFlyingSpeed();
		walkingSpeed = buddy.getWalkingSpeed();
		isFlying = buddy.isFlying();
		canFly = buddy.canFly();
	}

	public float getFlyingSpeed() {
		return flyingSpeed;
	}

	public float getWalkingSpeed() {
		return walkingSpeed;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public boolean canFly() {
		return canFly;
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
