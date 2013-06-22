package me.Man_cub.Buddies.protocol.message.entity.position;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityYawMessage extends EntityMessage {
	private final int rotation, pitch;

	public EntityYawMessage(int id, int rotation, int pitch) {
		super(id);
		this.rotation = rotation;
		this.pitch = pitch;
	}

	public int getRotation() {
		return rotation;
	}

	public int getPitch() {
		return pitch;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("rotation", rotation)
				.append("pitch", pitch)
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
		final EntityYawMessage other = (EntityYawMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.rotation, other.rotation)
				.append(this.pitch, other.pitch)
				.isEquals();
	}

}
