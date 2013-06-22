package me.Man_cub.Buddies.protocol.codec.player.position;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.position.PlayerPositionMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class PlayerPositionCodec extends MessageCodec<PlayerPositionMessage> {
	
	public PlayerPositionCodec() {
		super(PlayerPositionMessage.class, 0x0B);
	}

	@Override
	public PlayerPositionMessage decode(ChannelBuffer buffer) throws IOException {
		double x = buffer.readDouble();
		double y = buffer.readDouble();
		double stance = buffer.readDouble();
		double z = buffer.readDouble();
		boolean flying = buffer.readByte() == 1;
		return new PlayerPositionMessage(x, y, z, stance, flying, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(PlayerPositionMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(33);
		buffer.writeDouble(message.getX());
		buffer.writeDouble(message.getY());
		buffer.writeDouble(message.getStance());
		buffer.writeDouble(message.getZ());
		buffer.writeByte(message.isOnGround() ? 1 : 0);
		return buffer;
	}

}
