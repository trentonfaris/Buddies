package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.PlayerTimeMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerTimeCodec extends MessageCodec<PlayerTimeMessage> {
	
	public PlayerTimeCodec() {
		super(PlayerTimeMessage.class, 0x04);
	}

	@Override
	public PlayerTimeMessage decode(ChannelBuffer buffer) throws IOException {
		return new PlayerTimeMessage(buffer.readLong(), buffer.readLong());
	}

	@Override
	public ChannelBuffer encode(PlayerTimeMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(16);
		buffer.writeLong(message.getAge());
		buffer.writeLong(message.getTime());
		return buffer;
	}

}
