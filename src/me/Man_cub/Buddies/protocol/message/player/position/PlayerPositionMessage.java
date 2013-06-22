package me.Man_cub.Buddies.protocol.message.player.position;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerPositionMessage extends BuddiesMainChannelMessage {
	private final double x, y, z, stance;
	private final boolean onGround;
	private final long creationTimestamp = System.nanoTime();

	public PlayerPositionMessage(double x, double y, double z, double stance, boolean onGround, RepositionManager rm) {
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.stance = stance;
		this.onGround = onGround;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public Vector3 getPosition() {
		return new Vector3(x, y, z);
	}

	public double getStance() {
		return stance;
	}

	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("x", x).append("y", y).append("z", z).append("stance", stance).append("onGround", onGround).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PlayerPositionMessage other = (PlayerPositionMessage) obj;
		return new EqualsBuilder().append(this.x, other.x).append(this.y, other.y).append(this.z, other.z).append(this.stance, other.stance).append(this.onGround, other.onGround).isEquals();
	}

	public long getCreationTimestamp() {
		return creationTimestamp;
	}

}
