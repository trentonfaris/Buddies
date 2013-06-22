package me.Man_cub.Buddies.event.world;

import org.spout.api.event.HandlerList;
import org.spout.api.event.world.WorldEvent;
import org.spout.api.geo.World;
import org.spout.api.protocol.event.ProtocolEvent;

public class TimeUpdateEvent extends WorldEvent implements ProtocolEvent {
	private static HandlerList handlers = new HandlerList();
	private final long newTime;

	public TimeUpdateEvent(World world, long newTime) {
		super(world);
		this.newTime = newTime;
	}

	public long getNewTime() {
		return this.newTime;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
