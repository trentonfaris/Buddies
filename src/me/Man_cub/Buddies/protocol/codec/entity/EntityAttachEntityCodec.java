package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.EntityAttachEntityMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityAttachEntityCodec extends MessageCodec<EntityAttachEntityMessage> {
	
	public EntityAttachEntityCodec() {
		super(EntityAttachEntityMessage.class, 0x27);
	}

	@Override
	public EntityAttachEntityMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int vehicle = buffer.readInt();
		return new EntityAttachEntityMessage(id, vehicle);
	}

	@Override
	public ChannelBuffer encode(EntityAttachEntityMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(8);
		buffer.writeInt(message.getEntityId());
		buffer.writeInt(message.getVehicle());
		return buffer;
	}

}
