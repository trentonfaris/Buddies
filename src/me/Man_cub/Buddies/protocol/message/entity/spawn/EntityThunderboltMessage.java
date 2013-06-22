package me.Man_cub.Buddies.protocol.message.entity.spawn;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public class EntityThunderboltMessage extends EntityMessage {
	private final int mode, x, y, z;

	public EntityThunderboltMessage(int id, int x, int y, int z, RepositionManager rm) {
		this(id, 1, x, y, z, rm);
	}

	public EntityThunderboltMessage(int id, int mode, int x, int y, int z, RepositionManager rm) {
		super(id);
		this.mode = mode;
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
	}

	public int getMode() {
		return mode;
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
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("mode", mode)
				.append("x", x)
				.append("y", y)
				.append("z", z)
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
		final EntityThunderboltMessage other = (EntityThunderboltMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.mode, other.mode)
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.isEquals();
	}

}
