package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.PlayerStatisticMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerStatisticCodec extends MessageCodec<PlayerStatisticMessage> {
	
	public PlayerStatisticCodec() {
		super(PlayerStatisticMessage.class, 0xC8);
	}

	@Override
	public PlayerStatisticMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		byte amount = buffer.readByte();
		return new PlayerStatisticMessage(id, amount);
	}

	@Override
	public ChannelBuffer encode(PlayerStatisticMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(5);
		buffer.writeInt(message.getId());
		buffer.writeByte(message.getAmount());
		return buffer;
	}

}
