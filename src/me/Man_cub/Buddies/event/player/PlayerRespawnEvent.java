package me.man_cub.buddies.event.player;

import me.man_cub.buddies.component.entity.living.buddy.Buddy;

import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntitySpawnEvent;
import org.spout.api.exception.InvalidControllerException;
import org.spout.api.geo.discrete.Point;

public class PlayerRespawnEvent extends EntitySpawnEvent {
	private static final HandlerList handlers = new HandlerList();
	private Point point;

	public PlayerRespawnEvent(Entity e, Point point) {
		super(e, point);
		if (e.get(Buddy.class) == null) {
			throw new InvalidControllerException();
		}
		this.point = point;
	}

	/**
	 * Gets the player associated in this event.
	 *
	 * @return The player of the event.
	 */
	public Player getPlayer() {
		return (Player) getEntity();
	}

	/**
	 * Gets the point where the player respawned.
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * Sets the point where the player respawns.
	 *
	 * @param point The new location where spawning will take place.
	 */
	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
