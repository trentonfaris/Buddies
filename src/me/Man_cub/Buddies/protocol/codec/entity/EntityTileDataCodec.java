package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.entity.EntityTileDataMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;
import org.spout.nbt.CompoundMap;

public class EntityTileDataCodec extends MessageCodec<EntityTileDataMessage> {
	
	public EntityTileDataCodec() {
		super(EntityTileDataMessage.class, 0x84);
	}

	@Override
	public EntityTileDataMessage decode(ChannelBuffer buffer) throws IOException {
		int x = buffer.readInt();
		int y = buffer.readShort();
		int z = buffer.readInt();
		byte action = buffer.readByte();
		CompoundMap data = ChannelBufferUtils.readCompound(buffer);
		return new EntityTileDataMessage(x, y, z, action, data, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(EntityTileDataMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(message.getX());
		buffer.writeShort(message.getY());
		buffer.writeInt(message.getZ());
		buffer.writeByte(message.getAction());
		ChannelBufferUtils.writeCompound(buffer, message.getData());
		return buffer;
	}

}
