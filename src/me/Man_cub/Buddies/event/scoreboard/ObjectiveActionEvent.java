package me.Man_cub.Buddies.event.scoreboard;

import me.Man_cub.Buddies.scoreboard.Objective;

import org.spout.api.protocol.event.ProtocolEvent;

public class ObjectiveActionEvent implements ProtocolEvent {
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

}
