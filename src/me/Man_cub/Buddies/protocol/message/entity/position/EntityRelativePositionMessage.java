package me.Man_cub.Buddies.protocol.message.entity.position;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Vector3;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityRelativePositionMessage extends EntityMessage {
	private final int deltaX, deltaY, deltaZ;

	public EntityRelativePositionMessage(int id, Vector3 deltaPosition) {
		this(id, (int) deltaPosition.getX(), (int) deltaPosition.getY(), (int) deltaPosition.getZ());
	}

	public EntityRelativePositionMessage(int id, int deltaX, int deltaY, int deltaZ) {
		super(id);
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("deltaX", deltaX)
				.append("deltaY", deltaY)
				.append("deltaZ", deltaZ)
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
		final EntityRelativePositionMessage other = (EntityRelativePositionMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.deltaX, other.deltaX)
				.append(this.deltaY, other.deltaY)
				.append(this.deltaZ, other.deltaZ)
				.isEquals();
	}

}
