package me.Man_cub.Buddies.protocol.codec.server;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.ServerListPingMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public class ServerListPingCodec extends MessageCodec<ServerListPingMessage> {
	
	private static final ServerListPingMessage LIST_PING_MESSAGE = new ServerListPingMessage();

	public ServerListPingCodec() {
		super(ServerListPingMessage.class, 0xFE);
	}

	@Override
	public ServerListPingMessage decode(ChannelBuffer buffer) throws IOException {
		return LIST_PING_MESSAGE;
	}

	@Override
	public ChannelBuffer encode(ServerListPingMessage message) throws IOException {
		return ChannelBuffers.EMPTY_BUFFER;
	}

}
