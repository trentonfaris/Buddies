package me.Man_cub.Buddies.protocol.message.world;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public class ParticleEffectMessage extends BuddiesMainChannelMessage {
	private final float x, y, z, xOffset, yOffset, zOffset, velocity;
	private final int amount;
	private final String name;

	public ParticleEffectMessage(String name, float x, float y, float z, float xOffset, float yOffset, float zOffset, float velocity, int amount, RepositionManager rm) {
		this.name = name;
		this.velocity = velocity;
		this.amount = amount;

		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}

	public ParticleEffectMessage(String name, Vector3 position, Vector3 offset, float velocity, int amount, RepositionManager rm) {
		this(name, position.getX(), position.getY(), position.getZ(), offset.getX(), offset.getY(), offset.getZ(), velocity, amount, rm);
	}

	/**
	 * Name of the effect
	 * @return name
	 */
	public String getName() {
		return name;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	/**
	 * X offset of the particle effect.
	 * @return xOffset
	 */
	public float getXOffset() {
		return xOffset;
	}

	/**
	 * Y offset of the particle effect.
	 * @return yOffset
	 */
	public float getYOffset() {
		return yOffset;
	}

	/**
	 * Z offset of the particle effect.
	 * @return zOffset
	 */
	public float getZOffset() {
		return zOffset;
	}

	/**
	 * The number of particles to create
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * The velocity given to the particles
	 * @return velocity
	 */
	public float getVelocity() {
		return velocity;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("name", name)
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("xOffset", xOffset)
				.append("yOffset", yOffset)
				.append("zOffset", zOffset)
				.append("velocity", velocity)
				.append("amount", amount)
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
		final ParticleEffectMessage other = (ParticleEffectMessage) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.name, other.name)
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.xOffset, other.xOffset)
				.append(this.yOffset, other.yOffset)
				.append(this.zOffset, other.zOffset)
				.append(this.velocity, other.velocity)
				.append(this.amount, other.amount)
				.isEquals();
	}

}
