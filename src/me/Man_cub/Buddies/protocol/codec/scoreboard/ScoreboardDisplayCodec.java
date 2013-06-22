package me.Man_cub.Buddies.protocol.codec.scoreboard;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.scoreboard.ScoreboardDisplayMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public class ScoreboardDisplayCodec extends MessageCodec<ScoreboardDisplayMessage> {
	
	public ScoreboardDisplayCodec() {
		super(ScoreboardDisplayMessage.class, 0xD0);
	}

	@Override
	public ScoreboardDisplayMessage decode(ChannelBuffer buffer) throws IOException {
		byte position = buffer.readByte();
		String name = ChannelBufferUtils.readString(buffer);
		return new ScoreboardDisplayMessage(position, name);
	}

	@Override
	public ChannelBuffer encode(ScoreboardDisplayMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeByte(message.getPosition());
		ChannelBufferUtils.writeString(buffer, message.getName());
		return buffer;
	}

}
