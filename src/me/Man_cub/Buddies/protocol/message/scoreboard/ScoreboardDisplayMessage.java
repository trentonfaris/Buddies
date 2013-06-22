package me.Man_cub.Buddies.protocol.message.scoreboard;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public class ScoreboardDisplayMessage extends BuddiesMainChannelMessage {
	private final String name;
	private final byte position;

	public ScoreboardDisplayMessage(byte position, String name) {
		this.position = position;
		this.name = name;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public String getName() {
		return name;
	}

	public byte getPosition() {
		return position;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ScoreboardDisplayMessage other = (ScoreboardDisplayMessage) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.position, other.position)
				.append(this.name, other.name)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("position", position)
				.append("name", name)
				.toString();
	}

}
