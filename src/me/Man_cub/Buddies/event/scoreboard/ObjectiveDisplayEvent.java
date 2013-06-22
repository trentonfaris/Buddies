package me.Man_cub.Buddies.event.scoreboard;

import me.Man_cub.Buddies.scoreboard.ObjectiveSlot;

import org.spout.api.protocol.event.ProtocolEvent;

public class ObjectiveDisplayEvent implements ProtocolEvent {
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

}
