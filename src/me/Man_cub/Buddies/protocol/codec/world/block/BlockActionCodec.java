package me.Man_cub.Buddies.protocol.codec.world.block;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.world.block.BlockActionMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class BlockActionCodec extends MessageCodec<BlockActionMessage> {
	
	public BlockActionCodec() {
		super(BlockActionMessage.class, 0x36);
	}

	@Override
	public BlockActionMessage decode(ChannelBuffer buffer) throws IOException {
		int x = buffer.readInt();
		int y = buffer.readUnsignedShort();
		int z = buffer.readInt();
		byte firstByte = buffer.readByte();
		byte secondByte = buffer.readByte();
		short blockId = buffer.readShort();
		return new BlockActionMessage(x, y, z, blockId, firstByte, secondByte, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(BlockActionMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(14);
		buffer.writeInt(message.getX());
		buffer.writeShort(message.getY());
		buffer.writeInt(message.getZ());
		buffer.writeByte(message.getFirstByte());
		buffer.writeByte(message.getSecondByte());
		buffer.writeShort(message.getBlockId());
		return buffer;
	}

}
