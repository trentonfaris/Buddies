package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.PlayerGroundMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerGroundCodec extends MessageCodec<PlayerGroundMessage> {
	
	public PlayerGroundCodec() {
		super(PlayerGroundMessage.class, 0x0A);
	}

	@Override
	public PlayerGroundMessage decode(ChannelBuffer buffer) throws IOException {
		return new PlayerGroundMessage(buffer.readByte() == 1);
	}

	@Override
	public ChannelBuffer encode(PlayerGroundMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(1);
		buffer.writeByte(message.isOnGround() ? 1 : 0);
		return buffer;
	}

}
