package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerStatisticMessage extends BuddiesMainChannelMessage {
	private final int id;
	private final byte amount;

	public PlayerStatisticMessage(int id, byte amount) {
		this.id = id;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public byte getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", id)
				.append("amount", amount)
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
		final PlayerStatisticMessage other = (PlayerStatisticMessage) obj;
		return new EqualsBuilder()
				.append(this.id, other.id)
				.append(this.amount, other.amount)
				.isEquals();
	}

}
