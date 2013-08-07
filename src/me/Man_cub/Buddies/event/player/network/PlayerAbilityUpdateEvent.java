package me.Man_cub.Buddies.event.player.network;

import me.Man_cub.Buddies.component.entity.living.buddy.Buddy;

import org.spout.api.entity.Player;
import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.event.player.PlayerEvent;

public class PlayerAbilityUpdateEvent extends ProtocolEvent implements PlayerEvent {
	private static final HandlerList handlers = new HandlerList();
	private final float flyingSpeed;
	private final float walkingSpeed;
	private final boolean isFlying;
	private final boolean canFly;
	private final Player player;

	public PlayerAbilityUpdateEvent(Player player) {
		Buddy buddy = player.get(Buddy.class);
		if (buddy == null) {
			throw new IllegalStateException("Cannot call PlayerAbilityChangeEvent for players which don't have the Buddy component");
		}
		this.player = player;
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
	public Player getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
