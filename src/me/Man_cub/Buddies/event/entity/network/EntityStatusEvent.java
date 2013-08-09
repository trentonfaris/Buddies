package me.man_cub.buddies.event.entity.network;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.event.entity.EntityEvent;

public class EntityStatusEvent extends ProtocolEvent implements EntityEvent {
	private static final HandlerList handlers = new HandlerList();
	private final byte status;
	private final Entity entity;

	public EntityStatusEvent(Entity e, byte status) {
		this.entity = e;
		this.status = status;
	}
	
	@Override
	public Entity getEntity() {
		return entity;
	}

	public byte getStatus() {
		return this.status;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
