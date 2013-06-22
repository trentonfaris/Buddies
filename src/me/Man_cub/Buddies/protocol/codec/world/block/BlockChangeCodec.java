package me.Man_cub.Buddies.protocol.codec.world.block;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.world.block.BlockChangeMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class BlockChangeCodec extends MessageCodec<BlockChangeMessage> {
	
	public BlockChangeCodec() {
		super(BlockChangeMessage.class, 0x35);
	}

	@Override
	public BlockChangeMessage decode(ChannelBuffer buffer) throws IOException {
		int x = buffer.readInt();
		int y = buffer.readUnsignedByte();
		int z = buffer.readInt();
		short type = buffer.readShort();
		int metadata = buffer.readUnsignedByte();
		return new BlockChangeMessage(x, y, z, type, metadata, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(BlockChangeMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(12);
		buffer.writeInt(message.getX());
		buffer.writeByte(message.getY());
		buffer.writeInt(message.getZ());
		buffer.writeShort(message.getType());
		buffer.writeByte(message.getMetadata());
		return buffer;
	}

}
