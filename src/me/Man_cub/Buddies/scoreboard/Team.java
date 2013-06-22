package me.Man_cub.Buddies.scoreboard;

import java.util.HashSet;
import java.util.Set;

import me.Man_cub.Buddies.ChatStyle;
import me.Man_cub.Buddies.event.scoreboard.TeamActionEvent;
import me.Man_cub.Buddies.protocol.message.scoreboard.ScoreboardTeamMessage;

import org.spout.api.util.Named;

public class Team implements Named {
	private final Scoreboard scoreboard;
	private final String name;
	private String displayName = "";
	private String prefix = "";
	private String suffix = ChatStyle.RESET.toString();
	private boolean friendlyFire = false;
	private final Set<String> playerNames = new HashSet<String>();

	protected Team(Scoreboard scoreboard, String name) {
		this.scoreboard = scoreboard;
		this.name = name;
	}

	/**
	 * Returns the name to display for this team.
	 * @return display name of this team
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the name to be displayed for this team
	 * @param displayName for team
	 * @return this team
	 */
	public Team setDisplayName(String displayName) {
		this.displayName = displayName;
		update();
		return this;
	}

	/**
	 * Returns the {@link String} that prefaces the name of each player
	 * on this team.
	 * @return prefix of players on this team
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the prefix of each player on this team.
	 * @param prefix of each player on the team
	 * @return this team
	 */
	public Team setPrefix(String prefix) {
		this.prefix = prefix;
		update();
		return this;
	}

	/**
	 * Returns the {@link String} that come after each player's name.
	 * @return suffix for players
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * Sets the suffix for the players on this team.
	 * @param suffix for players
	 * @return this team
	 */
	public Team setSuffix(String suffix) {
		this.suffix = suffix;
		update();
		return this;
	}

	/**
	 * Returns true if players on the same team cannot hurt each other.
	 * @return true if teammates can hurt each other
	 */
	public boolean isFriendlyFire() {
		return friendlyFire;
	}

	/**
	 * Sets if players on the same team can hurt each other.
	 * @param friendlyFire true if teammates should be able to hurt each other.
	 * @return this team
	 */
	public Team setFriendlyFire(boolean friendlyFire) {
		this.friendlyFire = friendlyFire;
		update();
		return this;
	}

	/**
	 * Returns a set of all the names of players on this team.
	 * @return names of players
	 */
	public Set<String> getPlayerNames() {
		return playerNames;
	}

	/**
	 * Adds a name to the team.
	 * @param name to add
	 * @return this team
	 */
	public Team addPlayerName(String name) {
		playerNames.add(name);
		scoreboard.callProtocolEvent(new TeamActionEvent(this, ScoreboardTeamMessage.ADD_PLAYERS, name));
		return this;
	}

	/**
	 * Removes a name from the team
	 * @param name to add
	 * @return this team
	 */
	public Team removePlayerName(String name) {
		playerNames.remove(name);
		scoreboard.callProtocolEvent(new TeamActionEvent(this, ScoreboardTeamMessage.REMOVE_PLAYERS, name));
		return this;
	}

	private void update() {
		scoreboard.callProtocolEvent(new TeamActionEvent(this, ScoreboardTeamMessage.ACTION_UPDATE));
	}

	@Override
	public String getName() {
		return name;
	}

}
