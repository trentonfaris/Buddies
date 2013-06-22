package me.Man_cub.Buddies.protocol.message.player.connection;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerPingMessage extends BuddiesMainChannelMessage {
	private final int pingId;

	public PlayerPingMessage(int pingId) {
		this.pingId = pingId;
	}

	public int getPingId() {
		return pingId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("pingId", pingId)
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
		final PlayerPingMessage other = (PlayerPingMessage) obj;
		return this.pingId == other.pingId;
	}

}
