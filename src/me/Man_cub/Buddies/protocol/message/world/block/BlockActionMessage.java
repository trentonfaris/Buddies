package me.Man_cub.Buddies.protocol.message.world.block;

import me.Man_cub.Buddies.protocol.message.BuddiesBlockDataChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class BlockActionMessage extends BuddiesBlockDataChannelMessage {
	private final int x, y, z;
	private final byte firstByte, secondByte;
	private final short blockId;

	public BlockActionMessage(Block block, short blockId, byte firstByte, byte secondByte, RepositionManager rm) {
		this(block.getX(), block.getY(), block.getZ(), blockId, firstByte, secondByte, rm);
	}

	public BlockActionMessage(int x, int y, int z, short blockId, byte firstByte, byte secondByte, RepositionManager rm) {
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.firstByte = firstByte;
		this.secondByte = secondByte;
		this.blockId = blockId;
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

	public int getFirstByte() {
		return firstByte;
	}

	public int getSecondByte() {
		return secondByte;
	}

	public short getBlockId() {
		return blockId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("firstByte", firstByte)
				.append("secondByte", secondByte)
				.append("blockId", blockId)
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
		final BlockActionMessage other = (BlockActionMessage) obj;
		return new EqualsBuilder()
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.firstByte, other.firstByte)
				.append(this.secondByte, other.secondByte)
				.append(this.blockId, other.blockId)
				.isEquals();
	}

}
