package me.Man_cub.Buddies.protocol.message.entity;

import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
import org.spout.api.util.SpoutToStringStyle;

public class EntityItemDataMessage extends BuddiesMainChannelMessage {
	private final short type, id;
	private final byte[] data;

	public EntityItemDataMessage(ItemStack item, byte[] data) {
		this(item.getMaterial(), item.getData(), data);
	}

	public EntityItemDataMessage(Material material, short id, byte[] data) {
		this(BuddiesMaterials.getBuddiesId(material), id, data);
	}

	public EntityItemDataMessage(short type, short id, byte[] data) {
		this.type = type;
		this.id = id;
		this.data = data;
	}

	public short getType() {
		return type;
	}

	public short getId() {
		return id;
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("type", type)
				.append("id", id)
				.append("data", data)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final EntityItemDataMessage other = (EntityItemDataMessage) obj;
		return new EqualsBuilder()
				.append(this.type, other.type)
				.append(this.id, other.id)
				.append(this.data, other.data)
				.isEquals();
	}

}
