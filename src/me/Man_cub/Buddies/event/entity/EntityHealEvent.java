package me.Man_cub.Buddies.event.entity;

import me.Man_cub.Buddies.event.cause.HealCause;
import me.Man_cub.Buddies.event.cause.HealthChangeCause;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;

public class EntityHealEvent extends EntityHealthChangeEvent {
	private static HandlerList handlers = new HandlerList();
	private final HealCause cause;

	public EntityHealEvent(Entity e, int heal) {
		super(e, HealthChangeCause.HEAL, heal);
		this.cause = HealCause.UNKNOWN;
	}

	public EntityHealEvent(Entity e, int heal, HealCause cause) {
		super(e, HealthChangeCause.DAMAGE, heal);
		this.cause = cause;
	}

	/**
	 * Gets the type of heal. Defaults to UNKNOWN.
	 * @return type
	 */
	public HealCause getHealCause() {
		return cause;
	}

	/**
	 * Gets the heal to be applied to the health component.
	 * @return The damage to the health component.
	 */
	public int getHealAmount() {
		return getChange();
	}

	/**
	 * Sets the heal to be applied to the health component.
	 * @param damage The amount of damage dealt.
	 */
	public void setHealAmount(int amount) {
		setChange(amount);
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
