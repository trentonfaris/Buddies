package me.Man_cub.Buddies.protocol.message;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.BuddiesProtocol;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.Protocol;
import org.spout.api.protocol.dynamicid.DynamicWrapperMessage;
import org.spout.api.util.SpoutToStringStyle;

public class ServerPluginMessage extends BuddiesMainChannelMessage implements DynamicWrapperMessage {
	private final byte[] data;
	private final String type;
	
	public ServerPluginMessage(String type, byte[] data) {
		this.type = type;
		this.data = data;
	}
	
	public String getType() {
		return type;
	}
	
	public byte[] getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("data", data).append("type", type).toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(61, 33).append(data).append(type).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		final ServerPluginMessage other = (ServerPluginMessage) obj;
		return new EqualsBuilder().append(this.data, other.data).append(this.type, other.type).isEquals();
	}

	@Override
	public Message unwrap(boolean upstream, Protocol activeProtocol) throws IOException {
		MessageCodec<?> codec = BuddiesProtocol.getCodec(getType(), activeProtocol);
		if (codec != null) {
			return codec.decode(upstream, ChannelBuffers.wrappedBuffer(getData()));
		} else {
			return null;
		}
	}

}
