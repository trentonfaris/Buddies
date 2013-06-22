package me.Man_cub.Buddies.protocol.message.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class EntityActionMessage extends EntityMessage {
	public static final int ACTIOn_CROUCH = 1;
	public static final int ACTION_UNCROUCH = 2;
	public final int action;
	
	public EntityActionMessage(int id, int action) {
		super(id);
		this.action = action;
	}
	
	public int getAction() {
		return action;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("action", action)
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
		final EntityActionMessage other = (EntityActionMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.action, other.action)
				.isEquals();
	}

}
