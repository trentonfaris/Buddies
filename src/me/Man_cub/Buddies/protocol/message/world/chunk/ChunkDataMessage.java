package me.Man_cub.Buddies.protocol.message.world.chunk;

import me.Man_cub.Buddies.protocol.message.BuddiesBlockDataChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.Session;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class ChunkDataMessage extends BuddiesBlockDataChannelMessage {
	private final int x, z;
	private final boolean contiguous;
	private final boolean[] hasAdditionalData;
	private final byte[][] data;
	private final byte[] biomeData;
	private final boolean unload;
	private final Session session;

	public ChunkDataMessage(int x, int z, boolean contiguous, boolean[] hasAdditionalData, byte[][] data, byte[] biomeData, Session session, RepositionManager rm) {
		this(x, z, contiguous, hasAdditionalData, data, biomeData, false, session, rm);
	}

	public ChunkDataMessage(int x, int z, boolean contiguous, boolean[] hasAdditionalData, byte[][] data, byte[] biomeData, boolean unload, Session session, RepositionManager rm) {
		if (!unload && (hasAdditionalData.length != data.length || data.length != 16)) {
			throw new IllegalArgumentException("Data and hasAdditionalData must have a length of 16");
		}
		this.x = rm.convertChunkX(x);
		this.z = rm.convertChunkZ(z);
		this.contiguous = contiguous;
		this.hasAdditionalData = hasAdditionalData;
		this.data = data;
		this.biomeData = biomeData;
		this.unload = unload;
		this.session = session;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public boolean[] hasAdditionalData() {
		return hasAdditionalData;
	}

	public boolean isContiguous() {
		return contiguous;
	}

	public byte[][] getData() {
		return data;
	}

	public byte[] getBiomeData() {
		return biomeData;
	}

	public boolean shouldUnload() {
		return unload;
	}

	public Session getSession() {
		return session;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("x", x).append("z", z).append("hasAdditionalData", hasAdditionalData).append("contiguous", contiguous).append("data", data, false).append("data(hash)", hash(data)).append("biomeData", data, false).append("biomeData(hash)", hash(biomeData)).append("unload", unload, false).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ChunkDataMessage other = (ChunkDataMessage) obj;
		return new EqualsBuilder().append(this.x, other.x).append(this.z, other.z).append(this.contiguous, other.contiguous).append(this.hasAdditionalData, other.hasAdditionalData).append(this.data, other.data).append(this.biomeData, other.biomeData).append(this.unload, other.unload).isEquals();
	}

	private static int hash(byte[][] arr) {
		if (arr == null) {
			return 0;
		}
		int hash = 1;
		for (int i = 0; i < arr.length; i++) {
			hash += (hash << 5) + hash(arr[i]);
		}
		return hash;
	}

	private static int hash(byte[] arr) {
		if (arr == null) {
			return 0;
		}
		int hash = 1;
		for (int i = 0; i < arr.length; i++) {
			hash += (hash << 5) + arr[i];
		}
		return hash;
	}

}
