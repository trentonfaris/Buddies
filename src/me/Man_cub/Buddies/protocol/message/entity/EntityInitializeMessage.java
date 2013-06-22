package me.Man_cub.Buddies.protocol.message.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityInitializeMessage extends EntityMessage {
	public EntityInitializeMessage(int id) {
		super(id);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
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
		final EntityInitializeMessage other = (EntityInitializeMessage) obj;
		return this.getEntityId() == other.getEntityId();
	}

}
