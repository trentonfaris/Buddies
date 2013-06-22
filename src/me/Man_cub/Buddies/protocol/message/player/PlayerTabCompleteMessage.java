package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.spout.api.util.SpoutToStringStyle;

public class PlayerTabCompleteMessage extends BuddiesMainChannelMessage {
	private final String text;

	public PlayerTabCompleteMessage(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("text", text)
				.toString();
	}

	public String getText() {
		return text;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PlayerTabCompleteMessage other = (PlayerTabCompleteMessage) obj;
		return new EqualsBuilder()
				.append(this.text, other.text)
				.isEquals();
	}

}
