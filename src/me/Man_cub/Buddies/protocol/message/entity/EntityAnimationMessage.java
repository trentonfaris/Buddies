package me.Man_cub.Buddies.protocol.message.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

import me.Man_cub.Buddies.data.Animation;

public final class EntityAnimationMessage extends EntityMessage {
	private final int animation;

	public EntityAnimationMessage(int id, int animation) {
		super(id);
		this.animation = animation;
	}

	public int getAnimationId() {
		return animation;
	}

	public Animation getAnimation() {
		return Animation.get(animation);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("animation", animation)
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
		final EntityAnimationMessage other = (EntityAnimationMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.animation, other.animation)
				.isEquals();
	}

}
