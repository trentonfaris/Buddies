package me.Man_cub.Buddies.protocol.message.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.inventory.ItemStack;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityEquipmentMessage extends EntityMessage {
	private final ItemStack item;

	public EntityEquipmentMessage(int entityId, ItemStack item) {
		super(entityId);
		this.item = item;
	}

	public ItemStack get() {
		return item;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("item", item)
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
		final EntityEquipmentMessage other = (EntityEquipmentMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.item, other.item)
				.isEquals();
	}

}
