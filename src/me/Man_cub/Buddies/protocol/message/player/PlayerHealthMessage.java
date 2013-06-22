package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerHealthMessage extends BuddiesMainChannelMessage {
	private final short health;

	public PlayerHealthMessage(short health) {
		this.health = health;
	}

	public short getHealth() {
		return health;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("health", health)
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
		final PlayerHealthMessage other = (PlayerHealthMessage) obj;
		return new EqualsBuilder()
				.append(this.health, other.health)
				.isEquals();
	}
	

}
