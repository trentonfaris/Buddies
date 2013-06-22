package me.Man_cub.Buddies.protocol.codec.player;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.player.PlayerChatMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerChatCodec extends MessageCodec<PlayerChatMessage> {
	
	public PlayerChatCodec() {
		super(PlayerChatMessage.class, 0x03);
	}

	@Override
	public PlayerChatMessage decode(ChannelBuffer buffer) {
		String message = ChannelBufferUtils.readString(buffer);
		return new PlayerChatMessage(message);
	}

	@Override
	public ChannelBuffer encode(PlayerChatMessage message) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getMessage());
		return buffer;
	}

}
