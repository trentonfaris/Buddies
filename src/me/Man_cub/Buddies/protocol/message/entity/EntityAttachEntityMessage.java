package me.Man_cub.Buddies.protocol.message.entity;

import me.Man_cub.Buddies.protocol.proxy.BuddiesConnectionInfo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.proxy.ConnectionInfo;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityAttachEntityMessage extends EntityMessage {
	private int vehicle;

	public EntityAttachEntityMessage(int id, int vehicle) {
		super(id);
		this.vehicle = vehicle;
	}

	@Override
	public Message transform(boolean upstream, int connects, ConnectionInfo info, ConnectionInfo auxChannelInfo) {
		super.transform(upstream, connects, info, auxChannelInfo);
		if (vehicle == ((BuddiesConnectionInfo) info).getEntityId()) {
			vehicle = ((BuddiesConnectionInfo) auxChannelInfo).getEntityId();
		} else if (vehicle == ((BuddiesConnectionInfo) auxChannelInfo).getEntityId()) {
			vehicle = ((BuddiesConnectionInfo) info).getEntityId();
		}
		return this;
	}

	public int getVehicle() {
		return vehicle;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("vehicle", vehicle)
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
		final EntityAttachEntityMessage other = (EntityAttachEntityMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.vehicle, other.vehicle)
				.isEquals();
	}

}
