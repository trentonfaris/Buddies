package me.Man_cub.Buddies.protocol.message.entity.position;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Vector3;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityRelativePositionYawMessage extends EntityMessage {
	private final int deltaX, deltaY, deltaZ, rotation, pitch;

	public EntityRelativePositionYawMessage(int id, Vector3 deltaPosition, int rotation, int pitch) {
		this(id, (int) deltaPosition.getX(), (int) deltaPosition.getY(), (int) deltaPosition.getZ(), rotation, pitch);
	}

	public EntityRelativePositionYawMessage(int id, int deltaX, int deltaY, int deltaZ, int rotation, int pitch) {
		super(id);
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
		this.rotation = rotation;
		this.pitch = pitch;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public int getDeltaZ() {
		return deltaZ;
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
				.append("deltaX", deltaX)
				.append("deltaY", deltaY)
				.append("deltaZ", deltaZ)
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
		final EntityRelativePositionYawMessage other = (EntityRelativePositionYawMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.deltaX, other.deltaX)
				.append(this.deltaY, other.deltaY)
				.append(this.deltaZ, other.deltaZ)
				.append(this.rotation, other.rotation)
				.append(this.pitch, other.pitch)
				.isEquals();
	}

}
