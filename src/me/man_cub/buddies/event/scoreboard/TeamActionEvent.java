package me.man_cub.buddies.event.scoreboard;

import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;

import me.man_cub.buddies.scoreboard.Team;

public class TeamActionEvent extends ProtocolEvent {
	private static final HandlerList handlers = new HandlerList();
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

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
