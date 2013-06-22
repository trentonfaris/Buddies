package me.Man_cub.Buddies.protocol.codec.player.position;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.position.PlayerSpawnPositionMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class PlayerSpawnPositionCodec extends MessageCodec<PlayerSpawnPositionMessage> {
	
	public PlayerSpawnPositionCodec() {
		super(PlayerSpawnPositionMessage.class, 0x06);
	}

	@Override
	public PlayerSpawnPositionMessage decode(ChannelBuffer buffer) throws IOException {
		int x = buffer.readInt();
		int y = buffer.readInt();
		int z = buffer.readInt();
		return new PlayerSpawnPositionMessage(x, y, z, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(PlayerSpawnPositionMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(12);
		buffer.writeInt(message.getX());
		buffer.writeInt(message.getY());
		buffer.writeInt(message.getZ());
		return buffer;
	}

}
