package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.position.EntityRelativePositionMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityRelativePositionCodec extends MessageCodec<EntityRelativePositionMessage> {
	
	public EntityRelativePositionCodec() {
		super(EntityRelativePositionMessage.class, 0x1F);
	}

	@Override
	public EntityRelativePositionMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int dx = buffer.readByte();
		int dy = buffer.readByte();
		int dz = buffer.readByte();
		return new EntityRelativePositionMessage(id, dx, dy, dz);
	}

	@Override
	public ChannelBuffer encode(EntityRelativePositionMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(7);
		buffer.writeInt(message.getEntityId());
		buffer.writeByte(message.getDeltaX());
		buffer.writeByte(message.getDeltaY());
		buffer.writeByte(message.getDeltaZ());
		return buffer;
	}

}
