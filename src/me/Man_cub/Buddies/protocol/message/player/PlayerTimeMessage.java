package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerTimeMessage extends BuddiesMainChannelMessage {
	private final long age;
	private final long time;

	public PlayerTimeMessage(long age, long time) {
		this.age = age;
		this.time = time;
	}

	public long getAge() {
		return age;
	}

	public long getTime() {
		return time;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("age", age)
				.append("time", time)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PlayerTimeMessage other = (PlayerTimeMessage) obj;
		return this.time == other.time;
	}

}
