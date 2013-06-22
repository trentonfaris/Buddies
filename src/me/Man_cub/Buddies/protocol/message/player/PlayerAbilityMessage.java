package me.Man_cub.Buddies.protocol.message.player;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public class PlayerAbilityMessage extends BuddiesMainChannelMessage {
	private final boolean godMode, isFlying, canFly, creativeMode;
	private final byte flyingSpeed, walkingSpeed;

	public PlayerAbilityMessage(boolean godMode, boolean isFlying, boolean canFly, boolean creativeMode, byte flyingSpeed, byte walkingSpeed) {
		this.godMode = godMode;
		// TODO - is this required?  If canFly == false and isFlying == true, then client can't disable flight
		this.isFlying = isFlying && canFly;
		this.canFly = canFly;
		this.creativeMode = creativeMode;
		this.flyingSpeed = flyingSpeed;
		this.walkingSpeed = walkingSpeed;
	}

	public boolean isGodMode() {
		return godMode;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public boolean canFly() {
		return canFly;
	}

	public boolean isCreativeMode() {
		return creativeMode;
	}

	public byte getFlyingSpeed() {
		return flyingSpeed;
	}

	public byte getWalkingSpeed() {
		return walkingSpeed;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("godMode", godMode)
				.append("isFlying", isFlying)
				.append("canFly", canFly)
				.append("creativeMode", creativeMode)
				.append("flyingSpeed", flyingSpeed)
				.append("walkingSpeed", walkingSpeed)
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
		final PlayerAbilityMessage other = (PlayerAbilityMessage) obj;
		return new EqualsBuilder()
				.append(this.godMode, other.godMode)
				.append(this.isFlying, other.isFlying)
				.append(this.canFly, other.canFly)
				.append(this.creativeMode, other.creativeMode)
				.append(this.flyingSpeed, other.flyingSpeed)
				.append(this.walkingSpeed, other.walkingSpeed)
				.isEquals();
	}

}
