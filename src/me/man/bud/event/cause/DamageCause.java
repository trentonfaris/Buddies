package me.man_cub.buddies.event.cause;

public interface DamageCause<T> {
	
	public enum DamageType {
		ATTACK,
		BURN,
		COMMAND,
		CRUSH,
		DROWN,
		EXPLOSION,
		FALL,
		FIRE_SOURCE,
		FIREBALL,
		PROJECTILE,
		UNKNOWN;
		
		public boolean equals(DamageType... types) {
			for (DamageType type : types) {
				if (equals(type)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public DamageType getType();

}
