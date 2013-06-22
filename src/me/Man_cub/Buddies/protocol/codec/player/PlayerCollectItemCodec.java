package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.PlayerCollectItemMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerCollectItemCodec extends MessageCodec<PlayerCollectItemMessage> {
	
	public PlayerCollectItemCodec() {
		super(PlayerCollectItemMessage.class, 0x16);
	}

	@Override
	public PlayerCollectItemMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int collector = buffer.readInt();
		return new PlayerCollectItemMessage(id, collector);
	}

	@Override
	public ChannelBuffer encode(PlayerCollectItemMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(8);
		buffer.writeInt(message.getEntityId());
		buffer.writeInt(message.getCollector());
		return buffer;
	}

}
