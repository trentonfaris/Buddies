package me.Man_cub.Buddies.protocol.codec.player.connection;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.player.connection.PlayerKickMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerKickCodec extends MessageCodec<PlayerKickMessage> {
	
	public PlayerKickCodec() {
		super(PlayerKickMessage.class, 0xFF);
	}

	@Override
	public PlayerKickMessage decode(ChannelBuffer buffer) {
		return new PlayerKickMessage(ChannelBufferUtils.readString(buffer));
	}

	@Override
	public ChannelBuffer encode(PlayerKickMessage message) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getReason());
		return buffer;
	}

}
