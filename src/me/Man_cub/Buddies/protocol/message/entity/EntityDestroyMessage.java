package me.Man_cub.Buddies.protocol.message.entity;

import java.util.Arrays;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;
import me.Man_cub.Buddies.protocol.proxy.BuddiesConnectionInfo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.proxy.ConnectionInfo;
import org.spout.api.protocol.proxy.TransformableMessage;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityDestroyMessage extends BuddiesMainChannelMessage implements TransformableMessage {
	private final int[] id;

	public EntityDestroyMessage(int[] id) {
		this.id = id;
	}

	@Override
	public Message transform(boolean upstream, int connects, ConnectionInfo info, ConnectionInfo auxChannelInfo) {
		for (int i = 0; i < id.length; i++) {
			if (id[i] == ((BuddiesConnectionInfo) info).getEntityId()) {
				id[i] = ((BuddiesConnectionInfo) auxChannelInfo).getEntityId();
			} else if (id[i] == ((BuddiesConnectionInfo) auxChannelInfo).getEntityId()) {
				id[i] = ((BuddiesConnectionInfo) info).getEntityId();
			}
		}

		return this;
	}

	public int[] getId() {
		return id;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", Arrays.toString(id))
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
		final EntityDestroyMessage other = (EntityDestroyMessage) obj;
		return Arrays.equals(this.id, other.id);
	}

}
