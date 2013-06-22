package me.Man_cub.Buddies.protocol.message.entity.position;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Vector3;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityVelocityMessage extends EntityMessage {
	private final int velocityX, velocityY, velocityZ;

	public EntityVelocityMessage(int id, Vector3 velocity) {
		this(id, (int) velocity.getX(), (int) velocity.getY(), (int) velocity.getZ());
	}

	public EntityVelocityMessage(int id, int velocityX, int velocityY, int velocityZ) {
		super(id);
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}

	public int getVelocityX() {
		return velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public int getVelocityZ() {
		return velocityZ;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("velocityX", velocityX)
				.append("velocityY", velocityY)
				.append("velocityZ", velocityZ)
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
		final EntityVelocityMessage other = (EntityVelocityMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.velocityX, other.velocityX)
				.append(this.velocityY, other.velocityY)
				.append(this.velocityZ, other.velocityZ)
				.isEquals();
	}

}
