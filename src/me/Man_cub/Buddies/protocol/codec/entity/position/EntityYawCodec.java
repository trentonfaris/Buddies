package me.Man_cub.Buddies.protocol.codec.entity.position;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.position.EntityYawMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityYawCodec extends MessageCodec<EntityYawMessage> {
	
	public EntityYawCodec() {
		super(EntityYawMessage.class, 0x20);
	}

	@Override
	public EntityYawMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int rotation = buffer.readUnsignedByte();
		int pitch = buffer.readUnsignedByte();
		return new EntityYawMessage(id, rotation, pitch);
	}

	@Override
	public ChannelBuffer encode(EntityYawMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(6);
		buffer.writeInt(message.getEntityId());
		buffer.writeByte(message.getRotation());
		buffer.writeByte(message.getPitch());
		return buffer;
	}

}
