package me.Man_cub.Buddies.event.entity.network;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.event.entity.EntityEvent;

public class EntityCollectItemEvent extends ProtocolEvent implements EntityEvent {
	private static final HandlerList handlers = new HandlerList();
	private final Entity collected;
	private final Entity entity;

	public EntityCollectItemEvent(Entity e, Entity collected) {
		this.entity = e;
		this.collected = collected;
	}

	public Entity getCollected() {
		return this.collected;
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
