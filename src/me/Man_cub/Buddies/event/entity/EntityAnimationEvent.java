package me.Man_cub.Buddies.event.entity;

import me.Man_cub.Buddies.data.Animation;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntityEvent;
import org.spout.api.protocol.event.ProtocolEvent;

public class EntityAnimationEvent extends EntityEvent implements ProtocolEvent {
	private static HandlerList handlers = new HandlerList();
	private Animation animation;

	public EntityAnimationEvent(Entity e, Animation animation) {
		super(e);
		this.animation = animation;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
