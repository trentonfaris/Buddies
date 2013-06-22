package me.Man_cub.Buddies.protocol.message.player.position;

import java.util.List;

import me.Man_cub.Buddies.protocol.message.entity.EntityMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Vector3;
import org.spout.api.util.Parameter;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerSpawnMessage extends EntityMessage {
	private final int x, y, z, yaw, pitch, item;
	private final String name;
	private final List<Parameter<?>> parameters;

	public PlayerSpawnMessage(int id, String name, Vector3 position, int yaw, int pitch, int item, List<Parameter<?>> parameters) {
		this(id, name, (int) position.getX(), (int) position.getY(), (int) position.getZ(), yaw, pitch, item, parameters);
	}

	public PlayerSpawnMessage(int id, String name, int x, int y, int z, int yaw, int pitch, int item, List<Parameter<?>> parameters) {
		super(id);
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.item = item;
		this.parameters = parameters;
	}

	public String getName() {
		return name;
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

	public int getId() {
		return item;
	}

	public List<Parameter<?>> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("yaw", yaw)
				.append("pitch", pitch)
				.append("item", item)
				.append("name", name)
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
		final PlayerSpawnMessage other = (PlayerSpawnMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.yaw, other.yaw)
				.append(this.pitch, other.pitch)
				.append(this.item, other.item)
				.append(this.name, other.name)
				.isEquals();
	}

}
