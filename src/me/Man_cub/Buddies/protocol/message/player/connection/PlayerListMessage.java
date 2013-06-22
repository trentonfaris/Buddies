package me.Man_cub.Buddies.protocol.message.player.connection;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public class PlayerListMessage extends BuddiesMainChannelMessage {
	private final String playerName;
	private final boolean playerOnline;
	private final short ping;

	public PlayerListMessage(String playerName, boolean playerOnline, short ping) {
		this.playerName = playerName;
		this.playerOnline = playerOnline;
		this.ping = ping;
	}

	public String getPlayerName() {
		return playerName;
	}

	public boolean playerIsOnline() {
		return playerOnline;
	}

	public short getPing() {
		return ping;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("playerName", playerName)
				.append("playerOnline", playerOnline)
				.append("ping", ping)
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
		final PlayerListMessage other = (PlayerListMessage) obj;
		return new EqualsBuilder()
				.append(this.playerName, other.playerName)
				.append(this.playerOnline, other.playerOnline)
				.append(this.ping, other.ping)
				.isEquals();
	}
	
}
