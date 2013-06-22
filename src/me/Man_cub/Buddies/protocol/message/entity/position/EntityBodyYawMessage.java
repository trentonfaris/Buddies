package me.Man_cub.Buddies.protocol.message.entity.position;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public class EntityBodyYawMessage extends EntityMessage {
	private final int bodyYaw;

	public EntityBodyYawMessage(int id, int bodyYaw) {
		super(id);
		this.bodyYaw = bodyYaw;
	}

	public int getBodyYaw() {
		return bodyYaw;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("bodyYaw", bodyYaw)
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
		final EntityBodyYawMessage other = (EntityBodyYawMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.bodyYaw, other.bodyYaw)
				.isEquals();
	}

}
