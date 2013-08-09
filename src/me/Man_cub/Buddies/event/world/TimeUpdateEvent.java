package me.man_cub.buddies.event.world;

import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.event.world.WorldEvent;
import org.spout.api.geo.World;

public class TimeUpdateEvent extends ProtocolEvent implements WorldEvent {
	private static final HandlerList handlers = new HandlerList();
	private final long newTime;
	private final World world;

	public TimeUpdateEvent(World world, long newTime) {
		this.world = world;
		this.newTime = newTime;
	}

	public long getNewTime() {
		return this.newTime;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
