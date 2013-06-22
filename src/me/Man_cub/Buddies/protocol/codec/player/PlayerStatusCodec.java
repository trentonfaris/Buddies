package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.PlayerStatusMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerStatusCodec extends MessageCodec<PlayerStatusMessage> {
	
	public PlayerStatusCodec() {
		super(PlayerStatusMessage.class, 0xCD);
	}

	@Override
	public PlayerStatusMessage decode(ChannelBuffer buffer) throws IOException {
		byte status = buffer.readByte();
		return new PlayerStatusMessage(status);
	}

	@Override
	public ChannelBuffer encode(PlayerStatusMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(1);
		buffer.writeByte(message.getStatus());
		return buffer;
	}

}
