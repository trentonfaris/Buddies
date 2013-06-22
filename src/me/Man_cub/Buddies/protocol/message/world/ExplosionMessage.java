package me.Man_cub.Buddies.protocol.message.world;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class ExplosionMessage extends BuddiesMainChannelMessage {
	private final double x, y, z;
	private final float radius;
	private final byte[] coordinates;

	public ExplosionMessage(Vector3 position, float radius, byte[] coordinates, RepositionManager rm) {
		this(position.getX(), position.getY(), position.getZ(), radius, coordinates, rm);
	}

	public ExplosionMessage(double x, double y, double z, float radius, byte[] coordinates, RepositionManager rm) {
		if (coordinates.length % 3 != 0) {
			throw new IllegalArgumentException();
		}

		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.radius = radius;
		this.coordinates = coordinates;
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

	public float getRadius() {
		return radius;
	}

	public int getRecords() {
		return coordinates.length / 3;
	}

	public byte[] getCoordinates() {
		return coordinates;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("radius", radius)
				.append("coordinates", coordinates)
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
		final ExplosionMessage other = (ExplosionMessage) obj;
		return new EqualsBuilder()
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.radius, other.radius)
				.append(this.coordinates, other.coordinates)
				.isEquals();
	}

}
