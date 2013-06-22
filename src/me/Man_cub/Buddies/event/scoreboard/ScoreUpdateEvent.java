package me.Man_cub.Buddies.event.scoreboard;

import org.spout.api.protocol.event.ProtocolEvent;

public class ScoreUpdateEvent implements ProtocolEvent {
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

}
