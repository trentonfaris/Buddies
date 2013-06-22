package me.Man_cub.Buddies.protocol.message.entity;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;
import org.spout.nbt.CompoundMap;

public class EntityTileDataMessage extends BuddiesMainChannelMessage {
	private final int x;
	private final short y;
	private final int z;
	private final byte action;
	private CompoundMap data = new CompoundMap();

	public EntityTileDataMessage(int x, int y, int z, byte action, CompoundMap data, RepositionManager rm) {
		this.x = rm.convertX(x);
		this.y = (short) rm.convertY(y);
		this.z = rm.convertZ(z);
		this.action = action;
		this.data = data;
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

	public int getAction() {
		return action;
	}

	public CompoundMap getData() {
		return data;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("action", action)
				.append("data", data)
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
		final EntityTileDataMessage other = (EntityTileDataMessage) obj;
		return new EqualsBuilder()
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.action, other.action)
				.append(this.data, other.data)
				.isEquals();
	}

}
