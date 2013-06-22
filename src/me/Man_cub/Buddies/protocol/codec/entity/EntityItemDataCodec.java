package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.EntityItemDataMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityItemDataCodec extends MessageCodec<EntityItemDataMessage> {
	
	public EntityItemDataCodec() {
		super(EntityItemDataMessage.class, 0x83);
	}

	@Override
	public EntityItemDataMessage decode(ChannelBuffer buffer) throws IOException {
		short type = buffer.readShort();
		short id = buffer.readShort();
		int size = buffer.readUnsignedShort();
		byte[] data = new byte[size];
		buffer.readBytes(data);
		return new EntityItemDataMessage(type, id, data);
	}

	@Override
	public ChannelBuffer encode(EntityItemDataMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeShort(message.getType());
		buffer.writeShort(message.getId());
		buffer.writeShort(message.getData().length);
		buffer.writeBytes(message.getData());
		return buffer;
	}

}
