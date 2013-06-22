package me.Man_cub.Buddies.protocol.message.player.position;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Quaternion;
import org.spout.api.math.QuaternionMath;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerLookMessage extends BuddiesMainChannelMessage {
	private final float yaw, pitch, roll;
	private final boolean onGround;

	public PlayerLookMessage(float yaw, float pitch, boolean onGround) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = 0.0f; // No roll supported
		this.onGround = onGround;
	}

	public Quaternion getRotation() {
		return QuaternionMath.rotation(this.pitch, this.yaw, this.roll);
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}

	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("yaw", yaw).append("pitch", pitch).append("roll", roll).append("onGround", onGround).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PlayerLookMessage other = (PlayerLookMessage) obj;
		return new EqualsBuilder().append(this.yaw, other.yaw).append(this.pitch, other.pitch).append(this.roll, other.roll).append(this.onGround, other.onGround).isEquals();
	}

}
