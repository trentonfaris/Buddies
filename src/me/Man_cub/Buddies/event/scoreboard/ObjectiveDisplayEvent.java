package me.man_cub.buddies.event.scoreboard;

import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;

import me.man_cub.buddies.scoreboard.ObjectiveSlot;

public class ObjectiveDisplayEvent extends ProtocolEvent {
	private static final HandlerList handlers = new HandlerList();
	private final String objName;
	private final ObjectiveSlot slot;

	public ObjectiveDisplayEvent(String objName, ObjectiveSlot slot) {
		this.objName = objName;
		this.slot = slot;
	}

	public String getObjectiveName() {
		return objName;
	}

	public ObjectiveSlot getSlot() {
		return slot;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
