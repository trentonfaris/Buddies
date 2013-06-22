package me.Man_cub.Buddies.protocol.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import me.Man_cub.Buddies.protocol.BuddiesProtocol;
import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.util.SpoutToStringStyle;

public class UnregisterPluginChannelMessage extends BuddiesMainChannelMessage {
	private final List<String> types;

	public UnregisterPluginChannelMessage(Collection<MessageCodec<?>> codecs) {
		List<String> types = new ArrayList<String>();
		for (MessageCodec<?> codec : codecs) {
			types.add(BuddiesProtocol.getName(codec));
	}
		this.types = Collections.unmodifiableList(types);
	}

	public UnregisterPluginChannelMessage(List<String> types) {
		this.types = Collections.unmodifiableList(types);
	}

	public List<String> getTypes() {
		return types;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("types", types).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(39, 45).append(types).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UnregisterPluginChannelMessage) {
			final UnregisterPluginChannelMessage other = (UnregisterPluginChannelMessage) obj;
			return new EqualsBuilder().append(types, other.types).isEquals();
		} else {
			return false;
		}
	}
}
