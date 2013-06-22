package me.Man_cub.Buddies.protocol.codec.player.position;

import java.io.IOException;
import java.util.List;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.player.position.PlayerSpawnMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.util.Parameter;

public final class PlayerSpawnCodec extends MessageCodec<PlayerSpawnMessage> {
	
	public PlayerSpawnCodec() {
		super(PlayerSpawnMessage.class, 0x14);
	}

	@Override
	public PlayerSpawnMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		String name = ChannelBufferUtils.readString(buffer);
		int x = buffer.readInt();
		int y = buffer.readInt();
		int z = buffer.readInt();
		int rotation = buffer.readUnsignedByte();
		int pitch = buffer.readUnsignedByte();
		int item = buffer.readUnsignedShort();
		List<Parameter<?>> parameters = ChannelBufferUtils.readParameters(buffer);
		return new PlayerSpawnMessage(id, name, x, y, z, rotation, pitch, item, parameters);
	}

	@Override
	public ChannelBuffer encode(PlayerSpawnMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(message.getEntityId());
		ChannelBufferUtils.writeString(buffer, message.getName());
		buffer.writeInt(message.getX());
		buffer.writeInt(message.getY());
		buffer.writeInt(message.getZ());
		buffer.writeByte(message.getYaw());
		buffer.writeByte(message.getPitch());
		buffer.writeShort(message.getId());
		ChannelBufferUtils.writeParameters(buffer, message.getParameters());
		return buffer;
	}

}
