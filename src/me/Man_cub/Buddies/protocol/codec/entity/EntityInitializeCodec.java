package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.EntityInitializeMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityInitializeCodec extends MessageCodec<EntityInitializeMessage> {
	
	public EntityInitializeCodec() {
		super(EntityInitializeMessage.class, 0x1E);
	}

	@Override
	public EntityInitializeMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		return new EntityInitializeMessage(id);
	}

	@Override
	public ChannelBuffer encode(EntityInitializeMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(4);
		buffer.writeInt(message.getEntityId());
		return buffer;
	}

}
