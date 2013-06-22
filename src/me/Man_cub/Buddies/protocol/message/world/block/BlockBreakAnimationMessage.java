package me.Man_cub.Buddies.protocol.message.world.block;

import me.Man_cub.Buddies.protocol.message.BuddiesBlockDataChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.entity.Entity;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public class BlockBreakAnimationMessage extends BuddiesBlockDataChannelMessage {
	private final int entityId;
	private final int x, y, z;
	private final byte stage;

	public BlockBreakAnimationMessage(Entity entity, Block block, byte stage, RepositionManager rm) {
		this(entity.getId(), block.getX(), block.getY(), block.getZ(), stage, rm);
	}

	public BlockBreakAnimationMessage(int entityId, int x, int y, int z, byte stage, RepositionManager rm) {
		this.entityId = entityId;
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.stage = stage;
	}

	public int getEntityId() {
		return entityId;
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

	public byte getStage() {
		return stage;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("entity_id", entityId)
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("stage", stage)
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
		final BlockBreakAnimationMessage other = (BlockBreakAnimationMessage) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.entityId, other.entityId)
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.stage, other.stage)
				.isEquals();
	}

}
