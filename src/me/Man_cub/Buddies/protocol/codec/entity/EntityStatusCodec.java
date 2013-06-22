package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.EntityStatusMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityStatusCodec extends MessageCodec<EntityStatusMessage> {
	
	public EntityStatusCodec() {
		super(EntityStatusMessage.class, 0x26);
	}

	@Override
	public EntityStatusMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		byte status = buffer.readByte();
		return new EntityStatusMessage(id, status);
	}

	@Override
	public ChannelBuffer encode(EntityStatusMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(5);
		buffer.writeInt(message.getEntityId());
		buffer.writeByte(message.getStatus());
		return buffer;
	}

}
