package me.man_cub.buddies.event.cause;

import org.spout.api.entity.Entity;
import org.spout.api.event.Cause;
import org.spout.api.event.cause.EntityCause;

public class EntityDamageCause extends EntityCause implements DamageCause<Entity> {
	private final DamageType type;

	/**
	 * Contains the source and type of damage.
	 * @param entity The entity causing the damage
	 * @param type The cause of the damage.
	 */
	public EntityDamageCause(Entity entity, DamageType type) {
		super(entity);
		this.type = type;
	}

	/**
	 * Contains the source and type of damage.
	 * @param parent cause of this cause
	 * @param entity who caused this cause
	 * @param type who caused this cause
	 */
	public EntityDamageCause(Cause<?> parent, Entity entity, DamageType type) {
		super(parent, entity);
		this.type = type;
	}

	@Override
	public DamageType getType() {
		return type;
	}
}
