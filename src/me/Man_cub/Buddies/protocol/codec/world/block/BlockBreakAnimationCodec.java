package me.Man_cub.Buddies.protocol.codec.world.block;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.world.block.BlockBreakAnimationMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public class BlockBreakAnimationCodec extends MessageCodec<BlockBreakAnimationMessage> {
	
	public BlockBreakAnimationCodec() {
		super(BlockBreakAnimationMessage.class, 0x37);
	}

	@Override
	public BlockBreakAnimationMessage decode(ChannelBuffer buffer) throws IOException {
		int entityId = buffer.readInt();
		int x = buffer.readInt();
		int y = buffer.readInt();
		int z = buffer.readInt();
		byte stage = buffer.readByte();
		return new BlockBreakAnimationMessage(entityId, x, y, z, stage, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(BlockBreakAnimationMessage msg) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(17);
		buffer.writeInt(msg.getEntityId());
		buffer.writeInt(msg.getX());
		buffer.writeInt(msg.getY());
		buffer.writeInt(msg.getZ());
		buffer.writeByte(msg.getStage());
		return buffer;
	}

}
