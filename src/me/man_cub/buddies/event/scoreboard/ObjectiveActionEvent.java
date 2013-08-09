package me.man_cub.buddies.event.scoreboard;

import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;

import me.man_cub.buddies.scoreboard.Objective;

public class ObjectiveActionEvent extends ProtocolEvent {
	private static final HandlerList handlers = new HandlerList();
	private final Objective obj;
	private final byte action;

	public ObjectiveActionEvent(Objective obj, byte action) {
		this.obj = obj;
		this.action = action;
	}

	public Objective getObjective() {
		return obj;
	}

	public byte getAction() {
		return action;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
