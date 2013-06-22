package me.Man_cub.Buddies.protocol.codec.scoreboard;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.scoreboard.ScoreboardTeamMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public class ScoreboardTeamCodec extends MessageCodec<ScoreboardTeamMessage> {
	
	public ScoreboardTeamCodec() {
		super(ScoreboardTeamMessage.class, 0xD1);
	}

	@Override
	public ScoreboardTeamMessage decode(ChannelBuffer buffer) throws IOException {
		String name = ChannelBufferUtils.readString(buffer);
		String displayName, prefix, suffix;
		boolean friendlyFire = false;
		String[] players = null;

		byte mode = buffer.readByte();
		switch (mode) {
			case 0:
			case 2:
				displayName = ChannelBufferUtils.readString(buffer);
				prefix = ChannelBufferUtils.readString(buffer);
				suffix = ChannelBufferUtils.readString(buffer);
				friendlyFire = buffer.readByte() == 1;
				players = null;
				break;
			case 3:
			case 4:
				short count = buffer.readShort();
				players = new String[count];
				for (int i = 0; i < count; i++) {
					players[i] = ChannelBufferUtils.readString(buffer);
				}
			default:
				displayName = prefix = suffix = null;
				break;
		}

		return new ScoreboardTeamMessage(name, mode, displayName, prefix, suffix, friendlyFire, players);
	}

	@Override
	public ChannelBuffer encode(ScoreboardTeamMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getName());
		buffer.writeByte(message.getAction());
		switch (message.getAction()) {
			case 0:
			case 2:
				ChannelBufferUtils.writeString(buffer, message.getDisplayName());
				ChannelBufferUtils.writeString(buffer, message.getPrefix());
				ChannelBufferUtils.writeString(buffer, message.getSuffix());
				buffer.writeByte(message.isFriendlyFire() ? 1 : 0);
				break;
			case 3:
			case 4:
				buffer.writeShort(message.getPlayers().length);
				for (String name : message.getPlayers()) {
					ChannelBufferUtils.writeString(buffer, name);
				}
				break;
			default:
		}
		return buffer;
	}

}
