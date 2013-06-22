package me.Man_cub.Buddies.protocol.message.player.position;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerSpawnPositionMessage extends BuddiesMainChannelMessage {
	private final int x, y, z;

	public PlayerSpawnPositionMessage(int x, int y, int z, RepositionManager rm) {
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("x", x).append("y", y).append("z", z).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PlayerSpawnPositionMessage other = (PlayerSpawnPositionMessage) obj;
		return new EqualsBuilder().append(this.x, other.x).append(this.y, other.y).append(this.z, other.z).isEquals();
	}

}
