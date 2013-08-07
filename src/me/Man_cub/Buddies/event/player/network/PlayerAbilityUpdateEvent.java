package me.Man_cub.Buddies.event.player.network;

import me.Man_cub.Buddies.component.entity.living.buddy.Buddy;

import org.spout.api.entity.Player;
import org.spout.api.event.HandlerList;
import org.spout.api.event.player.PlayerEvent;
import org.spout.api.protocol.event.ProtocolEvent;

public class PlayerAbilityUpdateEvent extends PlayerEvent implements ProtocolEvent {
	private static HandlerList handlers = new HandlerList();
	private final float flyingSpeed;
	private final float walkingSpeed;
	private final boolean isFlying;
	private final boolean canFly;

	public PlayerAbilityUpdateEvent(Player player) {
		super(player);
		if (player.get(Buddy.class) == null) {
			throw new IllegalStateException("Cannot call PlayerAbilityChangeEvent for players which don't have the Buddy component");
		}
		Buddy buddy = player.get(Buddy.class);
		flyingSpeed = buddy.getFlyingSpeed();
		walkingSpeed = buddy.getWalkingSpeed();
		isFlying = buddy.isFlying();
		canFly = buddy.canFly();
	}

	public float getFlyingSpeed() {
		return flyingSpeed;
	}

	public float getWalkingSpeed() {
		return walkingSpeed;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public boolean canFly() {
		return canFly;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
