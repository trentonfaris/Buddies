package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public class PlayerStatusMessage extends BuddiesMainChannelMessage {
	public static final byte INITIAL_SPAWN = 0;
	public static final byte RESPAWN = 1;
	private final byte status;

	public PlayerStatusMessage(byte status) {
		this.status = status;
	}

	public byte getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("status", status)
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
		final PlayerStatusMessage other = (PlayerStatusMessage) obj;
		return new EqualsBuilder()
				.append(this.status, other.status)
				.isEquals();
	}

}
