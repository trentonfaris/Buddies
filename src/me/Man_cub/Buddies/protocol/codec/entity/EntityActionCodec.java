package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.EntityActionMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityActionCodec extends MessageCodec<EntityActionMessage> {
	
	public EntityActionCodec() {
		super(EntityActionMessage.class, 0x13);
	}

	@Override
	public EntityActionMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int action = buffer.readUnsignedByte();
		return new EntityActionMessage(id, action);
	}

	@Override
	public ChannelBuffer encode(EntityActionMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(5);
		buffer.writeInt(message.getEntityId());
		buffer.writeByte(message.getAction());
		return buffer;
	}

}
