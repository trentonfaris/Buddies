package me.Man_cub.Buddies.protocol.codec.player.connection;

import me.Man_cub.Buddies.protocol.message.player.connection.PlayerPingMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerPingCodec extends MessageCodec<PlayerPingMessage> {
	
	public PlayerPingCodec() {
		super(PlayerPingMessage.class, 0x00);
	}

	@Override
	public PlayerPingMessage decode(ChannelBuffer buffer) {
		int id = buffer.readInt();
		return new PlayerPingMessage(id);
	}

	@Override
	public ChannelBuffer encode(PlayerPingMessage message) {
		ChannelBuffer buffer = ChannelBuffers.buffer(4);
		buffer.writeInt(message.getPingId());
		return buffer;
	}

}
