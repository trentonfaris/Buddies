package me.Man_cub.Buddies.protocol.codec.entity.position;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.position.EntityRelativePositionYawMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class EntityRelativePositionYawCodec extends MessageCodec<EntityRelativePositionYawMessage> {
	
	public EntityRelativePositionYawCodec() {
		super(EntityRelativePositionYawMessage.class, 0x21);
	}

	@Override
	public EntityRelativePositionYawMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int dx = buffer.readByte();
		int dy = buffer.readByte();
		int dz = buffer.readByte();
		int rotation = buffer.readUnsignedByte();
		int pitch = buffer.readUnsignedByte();
		return new EntityRelativePositionYawMessage(id, dx, dy, dz, rotation, pitch);
	}

	@Override
	public ChannelBuffer encode(EntityRelativePositionYawMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(9);
		buffer.writeInt(message.getEntityId());
		buffer.writeByte(message.getDeltaX());
		buffer.writeByte(message.getDeltaY());
		buffer.writeByte(message.getDeltaZ());
		buffer.writeByte(message.getRotation());
		buffer.writeByte(message.getPitch());
		return buffer;
	}

}
