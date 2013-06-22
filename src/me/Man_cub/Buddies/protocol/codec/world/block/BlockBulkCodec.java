package me.Man_cub.Buddies.protocol.codec.world.block;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.world.block.BlockBulkMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class BlockBulkCodec extends MessageCodec<BlockBulkMessage> {
	
	public BlockBulkCodec() {
		super(BlockBulkMessage.class, 0x34);
	}

	@Override
	public BlockBulkMessage decode(ChannelBuffer buffer) throws IOException {
		int chunkX = buffer.readInt();
		int chunkZ = buffer.readInt();
		int changes = buffer.readUnsignedShort();
		int dataLength = buffer.readInt();
		if (dataLength != changes << 2) {
			throw new IllegalStateException("data length and record count mismatch");
		}

		short[] coordinates = new short[changes * 3];
		short[] types = new short[changes];
		byte[] metadata = new byte[changes];

		int coordinateIndex = 0;

		for (int i = 0; i < changes; i++) {
			int record = buffer.readInt();
			coordinates[coordinateIndex++] = (short) ((record >> 28) & 0x0F);
			coordinates[coordinateIndex++] = (short) ((record >> 16) & 0xFF);
			coordinates[coordinateIndex++] = (short) ((record >> 24) & 0x0F);
			types[i] = (short) ((record >> 4) & 0xFFF);
			metadata[i] = (byte) ((record >> 0) & 0xF);
		}

		return new BlockBulkMessage(chunkX, chunkZ, coordinates, types, metadata, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(BlockBulkMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(10 + message.getChanges() * 4);
		buffer.writeInt(message.getChunkX());
		buffer.writeInt(message.getChunkZ());
		buffer.writeShort(message.getChanges());
		buffer.writeInt(message.getChanges() << 2);

		int changes = message.getChanges();
		byte[] metadata = message.getMetadata();
		short[] types = message.getTypes();
		short[] coordinates = message.getCoordinates();

		int coordinateIndex = 0;

		for (int i = 0; i < changes; i++) {
			int record = metadata[i] & 0xF;
			record |= (types[i] & 0xFFF) << 4;
			record |= (coordinates[coordinateIndex++] & 0xF) << 28;
			record |= (coordinates[coordinateIndex++] & 0xFF) << 16;
			record |= (coordinates[coordinateIndex++] & 0xF) << 24;
			buffer.writeInt(record);
		}

		return buffer;
	}

}
