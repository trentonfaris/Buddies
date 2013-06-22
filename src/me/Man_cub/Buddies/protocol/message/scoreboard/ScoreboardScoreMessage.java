package me.Man_cub.Buddies.protocol.message.scoreboard;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public class ScoreboardScoreMessage extends BuddiesMainChannelMessage {
	private final String item, scoreboard;
	private final boolean remove;
	private final int value;

	public ScoreboardScoreMessage(String item, boolean remove, String scoreboard, int value) {
		this.item = item;
		this.remove = remove;
		this.scoreboard = scoreboard;
		this.value = value;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public boolean isRemove() {
		return remove;
	}

	public String getItem() {
		return item;
	}

	public String getScoreboard() {
		return scoreboard;
	}

	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ScoreboardScoreMessage other = (ScoreboardScoreMessage) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.item, other.item)
				.append(this.remove, other.remove)
				.append(this.scoreboard, other.scoreboard)
				.append(this.value, other.value)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("item", item)
				.append("remove", remove)
				.append("scoreboard", scoreboard)
				.append("value", value)
				.toString();
	}

}
