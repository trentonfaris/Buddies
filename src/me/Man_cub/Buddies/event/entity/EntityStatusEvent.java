package me.Man_cub.Buddies.event.entity;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntityEvent;
import org.spout.api.protocol.event.ProtocolEvent;

public class EntityStatusEvent extends EntityEvent implements ProtocolEvent {
	private static HandlerList handlers = new HandlerList();
	private byte status;

	public EntityStatusEvent(Entity e, byte status) {
		super(e);
		this.status = status;
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
