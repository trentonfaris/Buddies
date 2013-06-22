package me.Man_cub.Buddies.protocol.codec.player.connection;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.player.connection.PlayerHandshakeMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public final class PlayerHandshakeCodec extends MessageCodec<PlayerHandshakeMessage> {
	
	public PlayerHandshakeCodec() {
		super(PlayerHandshakeMessage.class, 0x02);
	}

	@Override
	public PlayerHandshakeMessage decode(ChannelBuffer buffer) {
		byte protoVersion = buffer.readByte();
		String username = ChannelBufferUtils.readString(buffer);
		String hostname = ChannelBufferUtils.readString(buffer);
		int port = buffer.readInt();
		return new PlayerHandshakeMessage(protoVersion, username, hostname, port);
	}

	@Override
	public ChannelBuffer encode(PlayerHandshakeMessage message) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeByte(message.getProtocolVersion());
		ChannelBufferUtils.writeString(buffer, message.getUsername());
		ChannelBufferUtils.writeString(buffer, message.getHostname());
		buffer.writeInt(message.getPort());
		return buffer;
	}

}
