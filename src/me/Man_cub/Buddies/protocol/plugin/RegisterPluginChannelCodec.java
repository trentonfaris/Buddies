package me.Man_cub.Buddies.protocol.plugin;

import java.util.Arrays;
import java.util.Iterator;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.util.Named;

public class RegisterPluginChannelCodec extends MessageCodec<RegisterPluginChannelMessage> implements Named {
	public RegisterPluginChannelCodec(int opcode) {
		super(RegisterPluginChannelMessage.class, opcode);
	}
	
	@Override
	public ChannelBuffer encode(RegisterPluginChannelMessage message) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		for (Iterator<String> i = message.getTypes().iterator(); i.hasNext(); ) {
			buffer.writeBytes(i.next().getBytes(CharsetUtil.UTF_8));
			if (i.hasNext()) {
				buffer.writeByte(0);
			}
		}
	return buffer;
	}

	@Override
	public RegisterPluginChannelMessage decode(ChannelBuffer buffer) {
		byte[] strData = new byte[buffer.readableBytes()];
		buffer.readBytes(strData);
		String str = new String(strData, CharsetUtil.UTF_8);
		return new RegisterPluginChannelMessage(Arrays.asList(str.split("\0")));
	}

	public String getName() {
		return "REGISTER";
	}

}
