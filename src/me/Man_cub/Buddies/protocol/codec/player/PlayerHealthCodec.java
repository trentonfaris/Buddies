package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.PlayerHealthMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerHealthCodec extends MessageCodec<PlayerHealthMessage> {
	
	public PlayerHealthCodec() {
		super(PlayerHealthMessage.class, 0x08);
	}

	@Override
	public PlayerHealthMessage decode(ChannelBuffer buffer) throws IOException {
		short health = buffer.readShort();
		return new PlayerHealthMessage(health);
	}

	@Override
	public ChannelBuffer encode(PlayerHealthMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(8);
		buffer.writeShort(message.getHealth());
		return buffer;
	}

}
