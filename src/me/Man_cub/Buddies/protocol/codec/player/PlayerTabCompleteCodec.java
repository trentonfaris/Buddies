package me.Man_cub.Buddies.protocol.codec.player;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.player.PlayerTabCompleteMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public class PlayerTabCompleteCodec extends MessageCodec<PlayerTabCompleteMessage> {
	
	public PlayerTabCompleteCodec() {
		super(PlayerTabCompleteMessage.class, 0xCB);
	}

	@Override
	public PlayerTabCompleteMessage decode(ChannelBuffer buffer) {
		String message = ChannelBufferUtils.readString(buffer);
		return new PlayerTabCompleteMessage(message);
	}

	@Override
	public ChannelBuffer encode(PlayerTabCompleteMessage message) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getText());
		return buffer;
	}

}
