package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerGroundMessage extends BuddiesMainChannelMessage {
	private final boolean onGround;

	public PlayerGroundMessage(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("onGround", onGround)
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
		final PlayerGroundMessage other = (PlayerGroundMessage) obj;
		return this.onGround == other.onGround;
	}

}
