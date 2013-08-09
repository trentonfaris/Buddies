package me.man_cub.buddies.event.entity.network;

import java.util.List;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.event.entity.EntityEvent;
import org.spout.api.util.Parameter;

public class EntityMetaChangeEvent extends ProtocolEvent implements EntityEvent {
	private static final HandlerList handlers = new HandlerList();
	private final List<Parameter<?>> parameters;
	private final Entity entity;

	public EntityMetaChangeEvent(Entity e, List<Parameter<?>> parameters) {
		this.entity = e;
		this.parameters = parameters;
	}

	/**
	 * Gets the updates meta data parameters of the Entity
	 *
	 * @return parameter list
	 */
	public List<Parameter<?>> getParameters() {
		return parameters;
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
