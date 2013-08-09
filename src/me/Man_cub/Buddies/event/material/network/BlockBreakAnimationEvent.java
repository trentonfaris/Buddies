package me.man_cub.buddies.event.material.network;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.geo.discrete.Point;

public class BlockBreakAnimationEvent extends ProtocolEvent {
	private static final HandlerList handlers = new HandlerList();
	private final Point point;
	private final byte level;
	private final Entity entity;

	public BlockBreakAnimationEvent(Entity entity, Point point, byte level) {
		this.entity = entity;
		this.point = point;
		this.level = level;
	}

	public Point getPoint() {
		return point;
	}

	public byte getLevel() {
		return level;
	}

	public Entity getEntity() {
		return entity;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
