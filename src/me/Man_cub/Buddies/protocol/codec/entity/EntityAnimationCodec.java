package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.EntityAnimationMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityAnimationCodec extends MessageCodec<EntityAnimationMessage> {
	
	public EntityAnimationCodec() {
		super(EntityAnimationMessage.class, 0x12);
	}

	@Override
	public EntityAnimationMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		byte animation = buffer.readByte();
		return new EntityAnimationMessage(id, animation);
	}

	@Override
	public ChannelBuffer encode(EntityAnimationMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(5);
		buffer.writeInt(message.getEntityId());
		buffer.writeByte(message.getAnimationId());
		return buffer;
	}

}
