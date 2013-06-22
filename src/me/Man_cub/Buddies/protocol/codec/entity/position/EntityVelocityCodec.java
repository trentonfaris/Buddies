package me.Man_cub.Buddies.protocol.codec.entity.position;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.position.EntityVelocityMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityVelocityCodec extends MessageCodec<EntityVelocityMessage> {
	
	public EntityVelocityCodec() {
		super(EntityVelocityMessage.class, 0x1C);
	}

	@Override
	public EntityVelocityMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int vx = buffer.readUnsignedShort();
		int vy = buffer.readUnsignedShort();
		int vz = buffer.readUnsignedShort();
		return new EntityVelocityMessage(id, vx, vy, vz);
	}

	@Override
	public ChannelBuffer encode(EntityVelocityMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(10);
		buffer.writeInt(message.getEntityId());
		buffer.writeShort(message.getVelocityX());
		buffer.writeShort(message.getVelocityY());
		buffer.writeShort(message.getVelocityZ());
		return buffer;
	}

}
