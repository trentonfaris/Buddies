package me.Man_cub.Buddies.protocol.codec.player.position;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.player.position.PlayerRespawnMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerRespawnCodec extends MessageCodec<PlayerRespawnMessage> {
	
	public PlayerRespawnCodec() {
		super(PlayerRespawnMessage.class, 0x09);
	}

	@Override
	public PlayerRespawnMessage decode(ChannelBuffer buffer) throws IOException {
		int dimension = buffer.readInt();
		byte difficulty = buffer.readByte();
		byte creative = buffer.readByte();
		int height = buffer.readUnsignedShort();
		String worldType = ChannelBufferUtils.readString(buffer);
		return new PlayerRespawnMessage(dimension, difficulty, creative, height, worldType);
	}

	@Override
	public ChannelBuffer encode(PlayerRespawnMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(message.getDimension());
		buffer.writeByte(message.getDifficulty());
		buffer.writeByte(message.getGameMode());
		buffer.writeShort(message.getWorldHeight());
		ChannelBufferUtils.writeString(buffer, message.getWorldType());
		return buffer;
	}

}
