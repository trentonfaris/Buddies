package me.Man_cub.Buddies.protocol.codec.scoreboard;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.scoreboard.ScoreboardScoreMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public class ScoreboardScoreCodec extends MessageCodec<ScoreboardScoreMessage> {
	
	public ScoreboardScoreCodec() {
		super(ScoreboardScoreMessage.class, 0xCF);
	}

	@Override
	public ScoreboardScoreMessage decode(ChannelBuffer buffer) throws IOException {
		String item = ChannelBufferUtils.readString(buffer);
		boolean remove = buffer.readByte() == 1;
		String name = null;
		int value = 0;
		if (!remove) {
			name = ChannelBufferUtils.readString(buffer);
			value = buffer.readInt();
		}
		return new ScoreboardScoreMessage(item, remove, name, value);
	}

	@Override
	public ChannelBuffer encode(ScoreboardScoreMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getItem());
		buffer.writeByte(message.isRemove() ? 1 : 0);
		if (!message.isRemove()) {
			ChannelBufferUtils.writeString(buffer, message.getScoreboard());
			buffer.writeInt(message.getValue());
		}
		return buffer;
	}

}
