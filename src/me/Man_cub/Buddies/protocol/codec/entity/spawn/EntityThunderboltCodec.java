package me.Man_cub.Buddies.protocol.codec.entity.spawn;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.entity.spawn.EntityThunderboltMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class EntityThunderboltCodec extends MessageCodec<EntityThunderboltMessage> {
	
	public EntityThunderboltCodec() {
		super(EntityThunderboltMessage.class, 0x47);
	}

	@Override
	public EntityThunderboltMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int mode = buffer.readUnsignedByte();
		int x = buffer.readInt() / 32;
		int y = buffer.readInt() / 32;
		int z = buffer.readInt() / 32;
		return new EntityThunderboltMessage(id, mode, x, y, z, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(EntityThunderboltMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(17);
		buffer.writeInt(message.getEntityId());
		buffer.writeByte(message.getMode());
		buffer.writeInt(message.getX() * 32);
		buffer.writeInt(message.getY() * 32);
		buffer.writeInt(message.getZ() * 32);
		return buffer;
	}

}
