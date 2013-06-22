package me.Man_cub.Buddies.protocol.codec.server;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.ServerPluginMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;

public class ServerPluginCodec extends MessageCodec<ServerPluginMessage> {
	
	public ServerPluginCodec() {
		super(ServerPluginMessage.class, 0xFA);
	}

	@Override
	public ServerPluginMessage decode(ChannelBuffer buffer) throws IOException {
		String type = ChannelBufferUtils.readString(buffer);
		int length = buffer.readUnsignedShort();
		byte[] data = new byte[length];
		buffer.readBytes(data);
		return new ServerPluginMessage(type, data);
	}

	@Override
	public ChannelBuffer encode(ServerPluginMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getType());
		buffer.writeShort(message.getData().length);
		buffer.writeBytes(message.getData());
		return buffer;
	}

}
