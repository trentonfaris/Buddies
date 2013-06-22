package me.Man_cub.Buddies.protocol.message.entity.spawn;

import java.util.List;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.Parameter;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityMobMessage extends EntityMessage {
	private final int type, x, y, z, yaw, pitch, headYaw;
	private final short velocityZ, velocityX, velocityY;
	private final List<Parameter<?>> parameters;

	public EntityMobMessage(int id, int type, Vector3 pos, int yaw, int pitch, int headYaw, short velocityZ, short velocityX, short velocityY, List<Parameter<?>> parameters, RepositionManager rm) {
		this(id, type, (int) pos.getX(), (int) pos.getY(), (int) pos.getZ(), yaw, pitch, headYaw, velocityZ, velocityX, velocityY, parameters, rm);
	}

	public EntityMobMessage(int id, int type, int x, int y, int z, int yaw, int pitch, int headYaw, short velocityZ, short velocityX, short velocityY, List<Parameter<?>> parameters, RepositionManager rm) {
		super(id);
		this.type = type;
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.yaw = yaw;
		this.pitch = pitch;
		this.headYaw = headYaw;
		this.velocityZ = velocityZ;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.parameters = parameters;
	}

	public int getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getYaw() {
		return yaw;
	}

	public int getPitch() {
		return pitch;
	}

	public int getHeadYaw() {
		return headYaw;
	}

	public short getVelocityZ() {
		return velocityZ;
	}

	public short getVelocityX() {
		return velocityX;
	}

	public short getVelocityY() {
		return velocityY;
	}

	public List<Parameter<?>> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("type", type)
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("yaw", yaw)
				.append("pitch", pitch)
				.append("headYaw", headYaw)
				.append("velocityz", velocityZ)
				.append("velocityx", velocityX)
				.append("velocityY", velocityY)
				.append("parameters", parameters)
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
		final EntityMobMessage other = (EntityMobMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.type, other.type)
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.yaw, other.yaw)
				.append(this.pitch, other.pitch)
				.append(this.headYaw, other.headYaw)
				.append(this.velocityZ, other.velocityZ)
				.append(this.velocityX, other.velocityX)
				.append(this.velocityY, other.velocityY)
				.append(this.parameters, other.parameters)
				.isEquals();
	}

}
