package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.PlayerHeldItemChangeMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerHeldItemChangeCodec extends MessageCodec<PlayerHeldItemChangeMessage> {
	
	public PlayerHeldItemChangeCodec() {
		super(PlayerHeldItemChangeMessage.class, 0x10);
	}

	@Override
	public PlayerHeldItemChangeMessage decode(ChannelBuffer buffer) throws IOException {
		int slot = buffer.readUnsignedShort();
		return new PlayerHeldItemChangeMessage(slot);
	}

	@Override
	public ChannelBuffer encode(PlayerHeldItemChangeMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(2);
		buffer.writeShort(message.getSlot());
		return buffer;
	}

}
