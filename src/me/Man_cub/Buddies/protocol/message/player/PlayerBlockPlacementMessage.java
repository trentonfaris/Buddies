package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.inventory.ItemStack;
import org.spout.api.material.block.BlockFace;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerBlockPlacementMessage extends BuddiesMainChannelMessage {
	private final int x, y, z;
	private final Vector3 face;
	private final BlockFace direction;
	private final ItemStack heldItem;

	public PlayerBlockPlacementMessage(int x, int y, int z, BlockFace direction, Vector3 face, RepositionManager rm) {
		this(x, y, z, direction, face, null, rm);
	}

	public PlayerBlockPlacementMessage(int x, int y, int z, BlockFace direction, Vector3 face, ItemStack heldItem, RepositionManager rm) {
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.face = face;
		this.direction = direction;
		this.heldItem = heldItem;
	}

	public ItemStack getHeldItem() {
		return this.heldItem;
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

	public BlockFace getDirection() {
		return direction;
	}

	public Vector3 getFace() {
		return this.face;
	}

	public PlayerBlockPlacementMessage convert(RepositionManager rm) {
		return new PlayerBlockPlacementMessage(getX(), getY(), getZ(), getDirection(), getFace(), getHeldItem(), rm);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("direction", direction)
				.append("face", face)
				.append("heldItem", heldItem)
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
		final PlayerBlockPlacementMessage other = (PlayerBlockPlacementMessage) obj;
		return new EqualsBuilder()
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.direction, other.direction)
				.append(this.face, other.face)
				.append(this.heldItem, other.heldItem)
				.isEquals();
	}

}
