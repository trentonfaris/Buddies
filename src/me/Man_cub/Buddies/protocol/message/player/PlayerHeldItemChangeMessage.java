package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerHeldItemChangeMessage extends BuddiesMainChannelMessage {
	private final int slot;

	public PlayerHeldItemChangeMessage(int slot) {
		this.slot = slot;
	}

	public int getSlot() {
		return slot;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("slot", slot)
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
		final PlayerHeldItemChangeMessage other = (PlayerHeldItemChangeMessage) obj;
		return this.slot == other.slot;
	}

}
