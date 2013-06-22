package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.EntityDestroyMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityDestroyCodec extends MessageCodec<EntityDestroyMessage> {
	
	public EntityDestroyCodec() {
		super(EntityDestroyMessage.class, 0x1D);
	}

	@Override
	public EntityDestroyMessage decode(ChannelBuffer buffer) throws IOException {
		byte length = buffer.readByte();
		int[] entityid = new int[length];
		for (int i = 0; i < length; i++) {
			entityid[i] = buffer.readInt();
		}
		return new EntityDestroyMessage(entityid);
	}

	@Override
	public ChannelBuffer encode(EntityDestroyMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeByte(message.getId().length);
		for (int i = 0; i < message.getId().length; i++) {
			buffer.writeInt(message.getId()[i]);
		}
		return buffer;
	}

}
