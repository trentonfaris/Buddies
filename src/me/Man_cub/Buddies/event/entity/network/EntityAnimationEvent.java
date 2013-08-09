package me.man_cub.buddies.event.entity.network;

import me.man_cub.buddies.data.Animation;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.event.entity.EntityEvent;

public class EntityAnimationEvent extends ProtocolEvent implements EntityEvent {
	private static final HandlerList handlers = new HandlerList();
	private Animation animation;
	private final Entity entity;

	public EntityAnimationEvent(Entity e, Animation animation) {
		this.entity = e;
		this.animation = animation;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
