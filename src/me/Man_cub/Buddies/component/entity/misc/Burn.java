package me.Man_cub.Buddies.component.entity.misc;

import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;
import me.Man_cub.Buddies.component.entity.living.Living;
import me.Man_cub.Buddies.component.world.misc.Sky;
import me.Man_cub.Buddies.data.BuddiesData;
import me.Man_cub.Buddies.event.cause.DamageCause.DamageType;
import me.Man_cub.Buddies.event.cause.NullDamageCause;

import org.spout.api.geo.discrete.Point;

public class Burn extends BuddiesEntityComponent {
	private float internalTimer = 0.0f, rainTimer = 0f;
	private Health health;
	private Living living;

	@Override
	public void onAttached() {
		health = getOwner().add(Health.class);
		living = getOwner().get(Living.class);
	}

	@Override
	public boolean canTick() {
		return getFireTick() >= 0 && !health.isDead();
	}

	@Override
	public void onTick(float dt) {
		Sky sky = getOwner().getWorld().get(Sky.class);
		if (sky == null) {
			return;
		}
		
		Point point = getOwner().getScene().getPosition();
		if (sky.hasWeather()) {
			if (sky.getWeatherSimulator().isRainingAt((int) point.getX(), (int) point.getY(), (int) point.getZ(), false)) {
				rainTimer += dt;
			} else {
				rainTimer = 0f;
			}
			if (rainTimer >= 2.0f) {
				setFireTick(0f);
				setFireHurting(false);
				rainTimer = 0f;
			}
		}
		if (health.isDead()) {
			setFireTick(0f);
			setFireHurting(false);
		}

		living.sendMetaData();
		if (isFireHurting()) {
			if (internalTimer >= 1.0f) {
				health.damage(1, new NullDamageCause(DamageType.BURN));
				internalTimer = 0;
			}
		}
		setFireTick(getFireTick() - dt);
		if (getFireTick() <= 0) {
			living.sendMetaData();
		}
		internalTimer += dt;
	}

	/**
	 * Set the firetick value. Any value higher than 0 will put the entity on fire.
	 * @param fireTick The fire tick amount.
	 */
	private void setFireTick(float fireTick) {
		getOwner().getData().put(BuddiesData.FIRE_TICK, fireTick);
	}

	/**
	 * Retrieve the firetick value. Any value higher than 0 means the entity is on fire.
	 * @return The firetick value.
	 */
	public float getFireTick() {
		return getOwner().getData().get(BuddiesData.FIRE_TICK);
	}

	/**
	 * Check if the entity is on fire or not
	 * @return True if the entity is on fire else false.
	 */
	public boolean isOnFire() {
		return getFireTick() > 0;
	}

	/**
	 * Check if the fire hurts or not.
	 * @return True if the fire hurts and false if it doesn't
	 */
	public boolean isFireHurting() {
		return getOwner().getData().get(BuddiesData.FIRE_HURT);
	}

	/**
	 * Sets if the fire should hurt or not. Maybe we just want to be warm? ^^
	 * @param fireHurt True if the fire should hurt else false.
	 */
	private void setFireHurting(boolean fireHurt) {
		getOwner().getData().put(BuddiesData.FIRE_HURT, fireHurt);
	}

	/**
	 * Sets the entity on fire.
	 * @param time The amount of time in seconds the entity should be on fire.
	 * @param hurt True if the fire should hurt else false.
	 */
	public void setOnFire(float time, boolean hurt) {
		setFireTick(time);
		setFireHurting(hurt);
		living.sendMetaData();
	}

}
