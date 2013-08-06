package me.Man_cub.Buddies.scoreboard;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;
import me.Man_cub.Buddies.event.scoreboard.ObjectiveActionEvent;
import me.Man_cub.Buddies.event.scoreboard.TeamActionEvent;

import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.protocol.event.ProtocolEvent;

public class Scoreboard extends BuddiesEntityComponent {
	private Player player;
	private final Set<Objective> objectives = new HashSet<Objective>();
	private final Set<Team> teams = new HashSet<Team>();

	/**
	 * Creates a new objective with the specified name and display name.
	 *
	 * @param name of objective
	 * @return newly created objective
	 */
	public Objective createObjective(String name) {
		Objective obj = new Objective(this, name);
		objectives.add(obj);
		//callProtocolEvent(new ObjectiveActionEvent(obj, ScoreboardObjectiveMessage.ACTION_CREATE));
		return obj;
	}

	/**
	 * Removes the objective with the specified name.
	 *
	 * @param name to remove objective
	 */
	public void removeObjective(String name) {
		Objective obj = getObjective(name);
		if (obj == null) {
			throw new IllegalArgumentException("Specified objective name does not exist on this scoreboard.");
		}
		objectives.remove(obj);
		//callProtocolEvent(new ObjectiveActionEvent(obj, ScoreboardObjectiveMessage.ACTION_REMOVE));
	}

	/**
	 * Returns the objective with the specified name.
	 *
	 * @param name to get objective from
	 * @return objective with specified name
	 */
	public Objective getObjective(String name) {
		for (Objective obj : objectives) {
			if (obj.getName().equals(name)) {
				return obj;
			}
		}
		return null;
	}

	/**
	 * Returns all created objectives on this scoreboard.
	 *
	 * @return all objectives
	 */
	public Set<Objective> getObjectives() {
		return Collections.unmodifiableSet(objectives);
	}

	/**
	 * Creates a new team on this scoreboard.
	 *
	 * @param name of new team
	 * @return newly created team
	 */
	public Team createTeam(String name) {
		Team team = new Team(this, name);
		teams.add(team);
		//callProtocolEvent(new TeamActionEvent(team, ScoreboardTeamMessage.ACTION_CREATE));
		return team;
	}

	/**
	 * Removes team with specified name.
	 *
	 * @param name of team to remove
	 */
	public void removeTeam(String name) {
		Team team = getTeam(name);
		if (team == null) {
			throw new IllegalArgumentException("Specified team does not exist on this scoreboard.");
		}
		teams.remove(team);
		//callProtocolEvent(new TeamActionEvent(team, ScoreboardTeamMessage.ACTION_REMOVE));
	}

	/**
	 * Returns the team with the specified name.
	 *
	 * @param name of team to get
	 * @return team with specified name
	 */
	public Team getTeam(String name) {
		for (Team team : teams) {
			if (team.getName().equals(name)) {
				return team;
			}
		}
		return null;
	}

	/**
	 * Returns a set of all teams on this scoreboard.
	 *
	 * @return set of all teams
	 */
	public Set<Team> getTeams() {
		return Collections.unmodifiableSet(teams);
	}

	/**
	 * Updates the score of the specified key for every objective with the
	 * specified criteria to the specified value.
	 *
	 * @param criteria to check for
	 * @param key to find
	 * @param value of points to add
	 */
	public void evaluateCriteria(String key, int value, boolean add, String... criteria) {
		for (Objective obj : objectives) {
			for (String c : criteria) {
				if (obj.getCriteria().equals(c) && obj.getScoreMap().containsKey(key)) {
					if (add) {
						obj.addScore(key, value);
					} else {
						obj.setScore(key, value);
					}
				}
			}
		}
	}

	/**
	 * Returns the player associated with this scoreboard.
	 *
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	protected void callProtocolEvent(ProtocolEvent event) {
		player.getNetworkSynchronizer().callProtocolEvent(event);
	}

	@Override
	public void onAttached() {
		Entity owner = getOwner();
		if (!(owner instanceof Player)) {
			throw new IllegalStateException("Scoreboard can only be attached to players.");
		}
		player = (Player) owner;
	}

}
