package me.Man_cub.Buddies.protocol.message.player.position;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Quaternion;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerPositionLookMessage extends BuddiesMainChannelMessage {
	private final PlayerPositionMessage position;
	private final PlayerLookMessage rotation;

	public PlayerPositionLookMessage(double x, double y, double z, double stance, float yaw, float pitch, boolean onGround, RepositionManager rm) {
		position = new PlayerPositionMessage(x, y, z, stance, onGround, rm);
		rotation = new PlayerLookMessage(yaw, pitch, onGround);
	}

	public PlayerPositionLookMessage(double x, double y, double z, double stance, float yaw, float pitch, boolean onGround, int channelId, RepositionManager rm) {
		super(channelId);
		position = new PlayerPositionMessage(x, y, z, stance, onGround, rm);
		rotation = new PlayerLookMessage(yaw, pitch, onGround);
	}

	public PlayerPositionMessage getPlayerPositionMessage() {
		return position;
	}

	public PlayerLookMessage getPlayerLookMessage() {
		return rotation;
	}

	public double getX() {
		return position.getX();
	}

	public double getY() {
		return position.getY();
	}

	public double getZ() {
		return position.getZ();
	}

	public double getStance() {
		return position.getStance();
	}

	public Quaternion getRotation() {
		return rotation.getRotation();
	}

	public float getYaw() {
		return getRotation().getYaw();
	}

	public float getPitch() {
		return getRotation().getPitch();
	}

	public boolean isOnGround() {
		return position.isOnGround();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("position", position).append("rotation", rotation).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PlayerPositionLookMessage other = (PlayerPositionLookMessage) obj;
		return new EqualsBuilder().append(this.position, other.position).append(this.rotation, other.rotation).isEquals();
	}
}
