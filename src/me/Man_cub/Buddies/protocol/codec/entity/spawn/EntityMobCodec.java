package me.Man_cub.Buddies.protocol.codec.entity.spawn;

import java.io.IOException;
import java.util.List;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.entity.spawn.EntityMobMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;
import org.spout.api.util.Parameter;

public final class EntityMobCodec extends MessageCodec<EntityMobMessage> {
	
	public EntityMobCodec() {
		super(EntityMobMessage.class, 0x18);
	}

	@Override
	public EntityMobMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int type = buffer.readUnsignedByte();
		int x = buffer.readInt();
		int y = buffer.readInt();
		int z = buffer.readInt();
		int yaw = buffer.readUnsignedByte();
		int pitch = buffer.readUnsignedByte();
		int headYaw = buffer.readUnsignedByte();
		short velocityZ = buffer.readShort();
		short velocityX = buffer.readShort();
		short velocityY = buffer.readShort();
		List<Parameter<?>> parameters = ChannelBufferUtils.readParameters(buffer);
		return new EntityMobMessage(id, type, x, y, z, yaw, pitch, headYaw, velocityZ, velocityX, velocityY, parameters, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(EntityMobMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(message.getEntityId());
		buffer.writeByte(message.getType());
		buffer.writeInt(message.getX());
		buffer.writeInt(message.getY());
		buffer.writeInt(message.getZ());
		buffer.writeByte(message.getYaw());
		buffer.writeByte(message.getPitch());
		buffer.writeByte(message.getHeadYaw());
		buffer.writeShort(message.getVelocityZ());
		buffer.writeShort(message.getVelocityX());
		buffer.writeShort(message.getVelocityY());
		ChannelBufferUtils.writeParameters(buffer, message.getParameters());
		return buffer;
	}

}
