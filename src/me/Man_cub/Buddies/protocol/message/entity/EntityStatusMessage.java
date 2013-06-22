package me.Man_cub.Buddies.protocol.message.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityStatusMessage extends EntityMessage {
	public static final byte ENTITY_HURT = 2;
	public static final byte ENTITY_DEAD = 3;
	private final byte status;

	public EntityStatusMessage(int id, byte status) {
		super(id);
		this.status = status;
	}

	public byte getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("status", status)
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
		final EntityStatusMessage other = (EntityStatusMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.status, other.status)
				.isEquals();
	}

}
