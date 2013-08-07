package me.Man_cub.Buddies.event.scoreboard;

import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;

public class ScoreUpdateEvent extends ProtocolEvent {
	private static final HandlerList handlers = new HandlerList();
	private final String key;
	private final int value;
	private final String objName;
	private final boolean remove;

	public ScoreUpdateEvent(String key, int value, String objName, boolean remove) {
		this.key = key;
		this.value = value;
		this.objName = objName;
		this.remove = remove;
	}

	public String getKey() {
		return key;
	}

	public int getValue() {
		return value;
	}

	public String getObjectiveName() {
		return objName;
	}

	public boolean isRemove() {
		return remove;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
