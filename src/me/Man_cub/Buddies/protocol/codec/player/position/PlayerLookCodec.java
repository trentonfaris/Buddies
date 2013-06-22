package me.Man_cub.Buddies.protocol.codec.player.position;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.position.PlayerLookMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerLookCodec extends MessageCodec<PlayerLookMessage> {
	
	public PlayerLookCodec() {
		super(PlayerLookMessage.class, 0x0C);
	}

	@Override
	public PlayerLookMessage decode(ChannelBuffer buffer) throws IOException {
		float yaw = -buffer.readFloat();
		float pitch = buffer.readFloat();
		boolean onGround = buffer.readByte() == 1;
		return new PlayerLookMessage(yaw, pitch, onGround);
	}

	@Override
	public ChannelBuffer encode(PlayerLookMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(9);
		buffer.writeFloat(-message.getYaw());
		buffer.writeFloat(message.getPitch());
		buffer.writeByte(message.isOnGround() ? 1 : 0);
		return buffer;
	}

}
