package me.Man_cub.Buddies.protocol.netcache.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.util.Named;

public class ChunkCacheCodec extends MessageCodec<ChunkCacheMessage> implements Named {
	public static final String channelName = "ChkCache:setHash";

	public ChunkCacheCodec(int opcode) {
		super(ChunkCacheMessage.class, opcode);
	}

	@Override
	public ChannelBuffer encode(ChunkCacheMessage message) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeBytes(message.getData());
		return buffer;
	}

	@Override
	public ChunkCacheMessage decode(ChannelBuffer buffer) {
		byte[] data = new byte[buffer.readableBytes()];
		buffer.readBytes(data);
		return new ChunkCacheMessage(data);
	}

	public String getName() {
		return channelName;
	}
}
