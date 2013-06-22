package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;
import me.Man_cub.Buddies.protocol.proxy.BuddiesConnectionInfo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.proxy.ConnectionInfo;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerUseEntityMessage extends EntityMessage {
	private int target;
	private final boolean punching;

	public PlayerUseEntityMessage(int id, int target, boolean punching) {
		super(id);
		this.target = target;
		this.punching = punching;
	}

	@Override
	public Message transform(boolean upstream, int connects, ConnectionInfo info, ConnectionInfo auxChannelInfo) {
		super.transform(upstream, connects, info, auxChannelInfo);
		if (target == ((BuddiesConnectionInfo) info).getEntityId()) {
			target = ((BuddiesConnectionInfo) auxChannelInfo).getEntityId();
		} else if (target == ((BuddiesConnectionInfo) auxChannelInfo).getEntityId()) {
			target = ((BuddiesConnectionInfo) info).getEntityId();
		}
		return this;
	}

	public int getTarget() {
		return target;
	}

	public boolean isPunching() {
		return punching;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("target", target)
				.append("punching", punching)
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
		final PlayerUseEntityMessage other = (PlayerUseEntityMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.target, other.target)
				.append(this.punching, other.punching)
				.isEquals();
	}

}
