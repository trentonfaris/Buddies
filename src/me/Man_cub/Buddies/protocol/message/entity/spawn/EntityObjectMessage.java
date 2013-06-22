package me.Man_cub.Buddies.protocol.message.entity.spawn;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.entity.Entity;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityObjectMessage extends EntityMessage {
	private final byte type;
	private final int x, y, z, throwerId;
	private final short speedX, speedY, speedZ;
	private final byte pitch, yaw;

	public EntityObjectMessage(Entity entity, byte type, int throwerId, RepositionManager rm) {
		super(entity);
		this.type = type;
		this.throwerId = throwerId;
		Transform transform = entity.getScene().getTransform();
		Point pos = transform.getPosition();

		double p = 32d;
		x = rm.convertX((int) Math.floor(pos.getX() * p));
		y = rm.convertY((int) Math.floor(pos.getY() * p));
		z = rm.convertZ((int) Math.floor(pos.getZ() * p));

		double v = 3.9d;
		Vector3 factor = new Vector3(v, v, v);

		Vector3 velocity;
		if (entity.getScene().isActivated()) {
			velocity = entity.getScene().getMovementVelocity(); //TODO: Check if it's alright.
		} else {
			velocity = Vector3.ZERO;
		}

		velocity = velocity.max(factor.multiply(-1)).min(factor);

		double s = 8000d;
		speedX = (short) (velocity.getX() * s);
		speedY = (short) (velocity.getY() * s);
		speedZ = (short) (velocity.getZ() * s);

		pitch = (byte) ChannelBufferUtils.protocolifyPitch(transform.getRotation().getPitch());
		yaw = (byte) ChannelBufferUtils.protocolifyYaw(transform.getRotation().getYaw());
	}

	public EntityObjectMessage(Entity entity, byte type, RepositionManager rm) {
		this(entity, type, 0, rm);
	}

	public EntityObjectMessage(int entityId, byte type, int x, int y, int z, int throwerId, short speedX, short speedY, short speedZ, byte yaw, byte pitch, RepositionManager rm) {
		super(entityId);
		this.type = type;
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.throwerId = throwerId;
		this.speedX = speedX;
		this.speedY = speedY;
		this.speedZ = speedZ;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	public EntityObjectMessage(int entityId, byte type, int x, int y, int z, int throwerId, byte yaw, byte pitch, RepositionManager rm) {
		this(entityId, type, x, y, z, throwerId, (short) 0, (short) 0, (short) 0, pitch, yaw, rm);
	}

	public byte getType() {
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

	public int getThrowerId() {
		return throwerId;
	}

	public short getSpeedX() {
		return speedX;
	}

	public short getSpeedY() {
		return speedY;
	}

	public short getSpeedZ() {
		return speedZ;
	}

	public byte getPitch() {
		return pitch;
	}

	public byte getYaw() {
		return yaw;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("type", type)
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("speedX", speedX)
				.append("speedY", speedY)
				.append("speedZ", speedZ)
				.append("pitch", pitch)
				.append("yaw", yaw)
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
		final EntityObjectMessage other = (EntityObjectMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.type, other.type)
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.speedX, other.speedX)
				.append(this.speedY, other.speedY)
				.append(this.speedZ, other.speedZ)
				.append(this.yaw, other.yaw)
				.append(this.pitch, other.pitch)
				.isEquals();
	}

}
