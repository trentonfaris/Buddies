package me.Man_cub.Buddies.event.scoreboard;

import me.Man_cub.Buddies.scoreboard.Team;

import org.spout.api.protocol.event.ProtocolEvent;

public class TeamActionEvent implements ProtocolEvent {
	private final Team team;
	private final String[] players;
	private final byte action;
	
	public TeamActionEvent(Team team, byte action, String... players) {
		this.team = team;
		this.players = players;
		this.action = action;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public String[] getPlayers() {
		return players;
	}
	
	public byte getAction() {
		return action;
	}

}
