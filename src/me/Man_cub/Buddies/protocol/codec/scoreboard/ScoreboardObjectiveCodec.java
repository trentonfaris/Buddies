package me.Man_cub.Buddies.protocol.codec.scoreboard;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.scoreboard.ScoreboardObjectiveMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public class ScoreboardObjectiveCodec extends MessageCodec<ScoreboardObjectiveMessage> {
	
	public ScoreboardObjectiveCodec() {
		super(ScoreboardObjectiveMessage.class, 0xCE);
	}

	@Override
	public ScoreboardObjectiveMessage decode(ChannelBuffer buffer) throws IOException {
		String name = ChannelBufferUtils.readString(buffer);
		String display = ChannelBufferUtils.readString(buffer);
		byte action = buffer.readByte();
		return new ScoreboardObjectiveMessage(name, display, action);
	}

	@Override
	public ChannelBuffer encode(ScoreboardObjectiveMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getName());
		ChannelBufferUtils.writeString(buffer, message.getDisplay());
		buffer.writeByte(message.getAction());
		return buffer;
	}

}
