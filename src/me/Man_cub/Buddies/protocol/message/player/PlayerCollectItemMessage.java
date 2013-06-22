package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;
import me.Man_cub.Buddies.protocol.proxy.BuddiesConnectionInfo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.proxy.ConnectionInfo;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerCollectItemMessage extends EntityMessage {
	private int collector;

	public PlayerCollectItemMessage(int id, int collector) {
		super(id);
		this.collector = collector;
	}

	@Override
	public Message transform(boolean upstream, int connects, ConnectionInfo info, ConnectionInfo auxChannelInfo) {
		super.transform(upstream, connects, info, auxChannelInfo);
		if (collector == ((BuddiesConnectionInfo) info).getEntityId()) {
			collector = ((BuddiesConnectionInfo) auxChannelInfo).getEntityId();
		} else if (collector == ((BuddiesConnectionInfo) auxChannelInfo).getEntityId()) {
			collector = ((BuddiesConnectionInfo) info).getEntityId();
		}
		return this;
	}

	public int getCollector() {
		return collector;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("collector", collector)
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
		final PlayerCollectItemMessage other = (PlayerCollectItemMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.collector, other.collector)
				.isEquals();
	}

}
