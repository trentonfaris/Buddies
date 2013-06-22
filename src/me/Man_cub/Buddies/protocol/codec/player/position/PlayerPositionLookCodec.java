package me.Man_cub.Buddies.protocol.codec.player.position;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.position.PlayerPositionLookMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class PlayerPositionLookCodec extends MessageCodec<PlayerPositionLookMessage> {
	
	public PlayerPositionLookCodec() {
		super(PlayerPositionLookMessage.class, 0x0D);
	}

	@Override
	public PlayerPositionLookMessage decode(ChannelBuffer buffer) throws IOException {
		double x = buffer.readDouble();
		double y = buffer.readDouble();
		double stance = buffer.readDouble();
		double z = buffer.readDouble();
		float yaw = -buffer.readFloat();
		float pitch = buffer.readFloat();
		boolean onGround = buffer.readByte() == 1;
		return new PlayerPositionLookMessage(x, y, z, stance, yaw, pitch, onGround, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(PlayerPositionLookMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(41);
		buffer.writeDouble(message.getX());
		buffer.writeDouble(message.getY());
		buffer.writeDouble(message.getStance());
		buffer.writeDouble(message.getZ());
		buffer.writeFloat(-message.getYaw());
		buffer.writeFloat(message.getPitch());
		buffer.writeByte(message.isOnGround() ? 1 : 0);
		return buffer;
	}

}
