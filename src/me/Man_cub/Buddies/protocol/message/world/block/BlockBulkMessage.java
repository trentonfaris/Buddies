package me.Man_cub.Buddies.protocol.message.world.block;

import me.Man_cub.Buddies.protocol.message.BuddiesBlockDataChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public final class BlockBulkMessage extends BuddiesBlockDataChannelMessage {
	private final int chunkX, chunkZ;
	private final short[] coordinates;
	private final short[] types;
	private final byte[] metadata;

	/**
	 * Creates a BlockBulkMessage using the specified data
	 * @param chunkX The x coordinate of the chunk containing these changes
	 * @param chunkZ The z coordinate of the chunk containing these changes
	 * @param coordinates An array of change coordinates. Length should be 3 * types.length, with coordinates in x y z format
	 * @param types An array of block types
	 * @param metadata An array of block metadata. No more than a nibble per entry
	 */
	public BlockBulkMessage(int chunkX, int chunkZ, short[] coordinates, short[] types, byte[] metadata, RepositionManager rm) {
		if (coordinates.length != (types.length * 3) || types.length != metadata.length) {
			throw new IllegalArgumentException();
		}
		// TODO - this message is broken anyway? - need to add reposition support + fix
		this.chunkX = rm.convertChunkX(chunkX);
		this.chunkZ = rm.convertChunkZ(chunkZ);
		this.coordinates = coordinates;
		this.types = types;
		this.metadata = metadata;
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public int getChanges() {
		return types.length;
	}

	public short[] getCoordinates() {
		return coordinates;
	}

	public short[] getTypes() {
		return types;
	}

	public byte[] getMetadata() {
		return metadata;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("chunkX", chunkX)
				.append("chunkZ", chunkZ)
				.append("coordinates", coordinates, false)
				.append("types", types, false)
				.append("metadata", metadata, false)
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
		final BlockBulkMessage other = (BlockBulkMessage) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.chunkX, other.chunkX)
				.append(this.chunkZ, other.chunkZ)
				.append(this.coordinates, other.coordinates)
				.append(this.types, other.types)
				.append(this.metadata, other.metadata)
				.isEquals();
	}

}
