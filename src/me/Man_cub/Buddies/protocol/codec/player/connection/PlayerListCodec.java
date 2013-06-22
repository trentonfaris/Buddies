package me.Man_cub.Buddies.protocol.codec.player.connection;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.player.connection.PlayerListMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public class PlayerListCodec extends MessageCodec<PlayerListMessage> {
	
	public PlayerListCodec() {
		super(PlayerListMessage.class, 0xC9);
	}

	@Override
	public PlayerListMessage decode(ChannelBuffer buffer) throws IOException {
		String name = ChannelBufferUtils.readString(buffer);
		boolean addOrRemove = buffer.readByte() == 1;
		short ping = buffer.readShort();
		return new PlayerListMessage(name, addOrRemove, ping);
	}

	@Override
	public ChannelBuffer encode(PlayerListMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getPlayerName());
		buffer.writeByte(message.playerIsOnline() ? 1 : 0);
		buffer.writeShort(message.getPing());
		return buffer;
	}

}
