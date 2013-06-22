package me.Man_cub.Buddies.protocol.message.scoreboard;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public class ScoreboardObjectiveMessage extends BuddiesMainChannelMessage {
	public static final byte ACTION_CREATE = 0;
	public static final byte ACTION_REMOVE = 1;
	public static final byte ACTION_UPDATE = 2;
	private final String name, display;
	private final byte action;

	public ScoreboardObjectiveMessage(String name, String display, byte action) {
		this.name = name;
		this.display = display;
		this.action = action;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public String getName() {
		return name;
	}

	public String getDisplay() {
		return display;
	}

	public byte getAction() {
		return action;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ScoreboardObjectiveMessage other = (ScoreboardObjectiveMessage) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.name, other.name)
				.append(this.display, other.display)
				.append(this.action, other.action)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("name", name)
				.append("display", display)
				.append("action", action)
				.toString();
	}

}
