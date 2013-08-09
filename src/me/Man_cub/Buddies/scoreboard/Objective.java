package me.man_cub.buddies.scoreboard;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.man_cub.buddies.event.scoreboard.ObjectiveDisplayEvent;
import me.man_cub.buddies.event.scoreboard.ScoreUpdateEvent;

import org.spout.api.util.Named;

public class Objective implements Named {
	/**
	 * An objective with this criteria will have it's score updated when
	 * updated by commands.
	 */
	public static final String CRITERIA_DUMMY = "dummy";
	/**
	 * An objective with this criteria will have it's score updated when a
	 * player dies.
	 */
	public static final String CRITERIA_DEATH_COUNT = "deathCount";
	/**
	 * An objective with this criteria will have it's score updated when a
	 * player kills another player.
	 */
	public static final String CRITERIA_PLAYER_KILL_COUNT = "playerKillCount";
	/**
	 * An objective with this criteria will have it's score updated when a
	 * player kills another living entity.
	 */
	public static final String CRITERIA_TOTAL_KILL_COUNT = "totalKillCount";
	/**
	 * An objective with this criteria will have it's score updated when a
	 * player's health changes.
	 */
	public static final String CRITERIA_HEALTH = "health";
	
	private final Scoreboard scoreboard;
	private final String name;
	private String displayName = "";
	private final Map<String, Integer> score = new HashMap<String, Integer>();
	private ObjectiveSlot slot;
	private String criteria = CRITERIA_DUMMY;
	
	protected Objective(Scoreboard scoreboard, String name) {
		this.scoreboard = scoreboard;
		this.name = name;
	}

	/**
	 * Returns the objective associated with this scoreboard.
	 *
	 * @return scoreboard attached to this objective
	 */
	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	/**
	 * Returns the name displayed on the scoreboard for this objective.
	 *
	 * @return display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the name displayed on the scoreboard for this objective.
	 *
	 * @param displayName to set
	 * @return this objective
	 */
	public Objective setDisplayName(String displayName) {
		this.displayName = displayName;
		//scoreboard.callProtocolEvent(new ObjectiveActionEvent(this, ScoreboardObjectiveMessage.ACTION_UPDATE));
		return this;
	}

	/**
	 * Returns the score mapping.
	 *
	 * @return score mapping
	 */
	public Map<String, Integer> getScoreMap() {
		return Collections.unmodifiableMap(score);
	}

	/**
	 * Returns the score of the specified name.
	 * @param name to get score of
	 * @return score of specified name
	 */
	public int getScore(String name) {
		return score.get(name);
	}

	/**
	 * Sets the score for the specified key at the specified integer value.
	 * @param key to set score for
	 * @param value of score
	 * @return this objective
	 */
	public Objective setScore(String key, int value) {
		score.put(key, value);
		scoreboard.callProtocolEvent(new ScoreUpdateEvent(key, value, name, false));
		return this;
	}

	/**
	 * Adds to the current score of the specified name with the specified
	 * value.
	 * @param key to add score to
	 * @param value how much to add
	 * @return
	 */
	public Objective addScore(String key, int value) {
		setScore(key, getScore(key) + value);
		return this;
	}

	/**
	 * Removes a score entry of the specified name.
	 * @param key to remove entry
	 * @return this objective
	 */
	public Objective removeScore(String key) {
		score.remove(key);
		scoreboard.callProtocolEvent(new ScoreUpdateEvent(key, 0, name, true));
		return this;
	}

	/**
	 * Returns the slot this objective is being displayed at.
	 * @return slot this objective is being displayed at
	 */
	public ObjectiveSlot getSlot() {
		return slot;
	}

	/**
	 * Sets the slot this objective is being displayed at.
	 * @param slot to display objective at
	 * @return this objective
	 */
	public Objective setSlot(ObjectiveSlot slot) {
		this.slot = slot;
		scoreboard.callProtocolEvent(new ObjectiveDisplayEvent(name, slot));
		return this;
	}

	/**
	 * Returns the criteria for this objective.
	 * @return criteria of objective
	 */
	public String getCriteria() {
		return criteria;
	}

	/**
	 * Sets the criteria for this objective.
	 * @param criteria to set
	 * @return this objective
	 */
	public Objective setCriteria(String criteria) {
		this.criteria = criteria;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

}
