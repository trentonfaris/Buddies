package me.Man_cub.Buddies.protocol.netcache.protocol;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public class ChunkCacheMessage extends BuddiesMainChannelMessage {
	private final byte[] data;

	public ChunkCacheMessage(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public String getChannel() {
		return ChunkCacheCodec.channelName;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("hashes", data).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(39, 45).append(data).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ChunkCacheMessage) {
			final ChunkCacheMessage other = (ChunkCacheMessage) obj;
			return new EqualsBuilder().append(data, other.data).isEquals();
		} else {
			return false;
		}
	}

}
