package me.Man_cub.Buddies.event.entity;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntityEvent;
import org.spout.api.protocol.event.ProtocolEvent;

public class EntityCollectItemEvent extends EntityEvent implements ProtocolEvent {
	private static HandlerList handlers = new HandlerList();
	private Entity collected;

	public EntityCollectItemEvent(Entity e, Entity collected) {
		super(e);
		this.collected = collected;
	}

	public Entity getCollected() {
		return this.collected;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
