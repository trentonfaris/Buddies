package me.Man_cub.Buddies.event.entity;

import me.Man_cub.Buddies.event.cause.DamageCause;
import me.Man_cub.Buddies.event.cause.DamageCause.DamageType;
import me.Man_cub.Buddies.event.cause.HealthChangeCause;
import me.Man_cub.Buddies.event.cause.NullDamageCause;

import org.spout.api.entity.Entity;
import org.spout.api.event.Cause;
import org.spout.api.event.HandlerList;

public class EntityDamageEvent extends EntityHealthChangeEvent {
	private static HandlerList handlers = new HandlerList();
	private boolean hasSendHurtMessage = true;
	private final Cause<?> cause;

	public EntityDamageEvent(Entity e, int damage) {
		super(e, HealthChangeCause.DAMAGE, -damage);
		this.cause = new NullDamageCause(DamageType.UNKNOWN);
	}

	public EntityDamageEvent(Entity e, int damage, Cause<?> cause) {
		super(e, HealthChangeCause.DAMAGE, -damage);
		this.cause = cause;
	}

	public EntityDamageEvent(Entity e, int damage, Cause<?> cause, boolean sendHurtMessage) {
		super(e, HealthChangeCause.DAMAGE, -damage);
		this.cause = cause;
		this.hasSendHurtMessage = sendHurtMessage;
	}

	/**
	 * Returns the object causing the damage, such as a block or entity.
	 * Defaults to null.
	 * @return The source of the damage.
	 */
	public Object getDamager() {
		return cause.getSource();
	}

	/**
	 * Gets the type of damage. Defaults to UNKNOWN.
	 * @return type
	 */
	public DamageType getDamageType() {
		if (cause instanceof DamageCause) {
			return ((DamageCause<?>) cause).getType();
		}
		return DamageType.UNKNOWN;
	}

	/**
	 * Gets the {@link Cause} for this event.
	 */
	public Cause<?> getDamageCause() {
		return cause;
	}

	/**
	 * Returns whether or not a hurt message will be sent.
	 * Defaults to true.
	 * @return boolean
	 */
	public boolean getSendMessage() {
		return hasSendHurtMessage;
	}

	/**
	 * Sets whether or not to send a hurt message.
	 * @param sendMessage
	 */
	public void setSendHurtMessage(boolean sendMessage) {
		this.hasSendHurtMessage = sendMessage;
	}

	/**
	 * Gets the damage dealt to the health component.
	 * @return The damage to the health component.
	 */
	public int getDamage() {
		return -getChange();
	}

	/**
	 * Sets the damage to be dealt to the health component.
	 * @param damage The amount of damage dealt.
	 */
	public void setDamage(int damage) {
		setChange(-damage);
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
