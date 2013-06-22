package me.Man_cub.Buddies.event.entity;

import java.util.List;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntityEvent;
import org.spout.api.protocol.event.ProtocolEvent;
import org.spout.api.util.Parameter;

public class EntityMetaChangeEvent extends EntityEvent implements ProtocolEvent {
	private static HandlerList handlers = new HandlerList();
	private List<Parameter<?>> parameters;

	public EntityMetaChangeEvent(Entity e, List<Parameter<?>> parameters) {
		super(e);
		this.parameters = parameters;
	}

	/**
	 * Gets the updates meta data parameters of the Entity
	 * @return parameter list
	 */
	public List<Parameter<?>> getParameters() {
		return parameters;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
