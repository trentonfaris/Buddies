package me.Man_cub.Buddies.protocol.message.world.chunk;

import java.util.Arrays;

import me.Man_cub.Buddies.protocol.message.BuddiesBlockDataChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class ChunkBulkMessage extends BuddiesBlockDataChannelMessage {
	
	private final int[] x;
	private final int[] z;
	private final boolean[][] addData;
	private final byte[][][] data;
	private final byte[][] biomeData;

	public ChunkBulkMessage(int[] x, int[] z, boolean[][] hasAdditionalData, byte[][][] data, byte[][] biomeData, RepositionManager rm) {
		int l = x.length;
		if (l != z.length || l != hasAdditionalData.length || l != data.length || l != biomeData.length) {
			throw new IllegalArgumentException("The lengths of all bulk data arrays must be equal");
		}
		this.x = Arrays.copyOf(x, x.length);
		this.z = Arrays.copyOf(z, z.length);
		for (int i = 0; i < x.length; i++) {
			x[i] = rm.convertChunkX(x[i]);
			z[i] = rm.convertChunkZ(z[i]);
		}
		this.addData = hasAdditionalData;
		this.data = data;
		this.biomeData = biomeData;
	}

	public int[] getX() {
		return x;
	}

	public int[] getZ() {
		return z;
	}

	public boolean[][] hasAdditionalData() {
		return addData;
	}

	public byte[][][] getData() {
		return data;
	}

	public byte[][] getBiomeData() {
		return biomeData;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("x", x)
				.append("z", z)
				.append("hasAdditionalData", addData)
				.append("data", data, false)
				.append("biomeData", data, false)
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
		final ChunkBulkMessage other = (ChunkBulkMessage) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.x, other.x)
				.append(this.z, other.z)
				.append(this.addData, other.addData)
				.append(this.data, other.data)
				.append(this.biomeData, other.biomeData)
				.isEquals();
	}

}
