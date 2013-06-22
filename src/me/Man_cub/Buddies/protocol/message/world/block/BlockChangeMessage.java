package me.Man_cub.Buddies.protocol.message.world.block;

import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.protocol.message.BuddiesBlockDataChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class BlockChangeMessage extends BuddiesBlockDataChannelMessage {
	private final int x, y, z, metadata;
	private final short type;

	public BlockChangeMessage(Block block, RepositionManager rm) {
		this(block.getX(), block.getY(), block.getZ(), BuddiesMaterials.getBuddiesId(block.getMaterial()), block.getBlockData(), rm);
	}

	public BlockChangeMessage(int x, int y, int z, short type, int metadata, RepositionManager rm) {
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.type = type;
		this.metadata = metadata;
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

	public short getType() {
		return type;
	}

	public int getMetadata() {
		return metadata;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("x", x).append("y", y).append("z", z).append("type", type).append("metadata", metadata).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BlockChangeMessage other = (BlockChangeMessage) obj;
		return new EqualsBuilder().append(this.x, other.x).append(this.y, other.y).append(this.z, other.z).append(this.type, other.type).append(this.metadata, other.metadata).isEquals();
	}

}
