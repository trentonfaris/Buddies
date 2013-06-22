package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.PlayerUseEntityMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerUseEntityCodec extends MessageCodec<PlayerUseEntityMessage> {
	
	public PlayerUseEntityCodec() {
		super(PlayerUseEntityMessage.class, 0x07);
	}

	@Override
	public PlayerUseEntityMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int target = buffer.readInt();
		boolean punching = buffer.readByte() != 0;
		return new PlayerUseEntityMessage(id, target, punching);
	}

	@Override
	public ChannelBuffer encode(PlayerUseEntityMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(9);
		buffer.writeInt(message.getEntityId());
		buffer.writeInt(message.getTarget());
		buffer.writeByte(message.isPunching() ? 1 : 0);
		return buffer;
	}

}
