package me.Man_cub.Buddies.protocol.codec.entity.position;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.position.EntityBodyYawMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public class EntityBodyYawCodec extends MessageCodec<EntityBodyYawMessage> {
	
	public EntityBodyYawCodec() {
		super(EntityBodyYawMessage.class, 0x23);
	}

	@Override
	public EntityBodyYawMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int headYaw = buffer.readUnsignedByte();
		return new EntityBodyYawMessage(id, headYaw);
	}

	@Override
	public ChannelBuffer encode(EntityBodyYawMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(5);
		buffer.writeInt(message.getEntityId());
		buffer.writeByte(message.getBodyYaw());
		return buffer;
	}

}
