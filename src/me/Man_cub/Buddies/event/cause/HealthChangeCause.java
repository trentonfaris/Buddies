package me.man_cub.buddies.event.cause;

public enum HealthChangeCause {
	
	/**
	 * Health changed due to the execution of a command.
	 */
	COMMAND,
	/**
	 * Health changed due to being damaged.
	 * @see {@link org.spout.vanilla.api.event.cause.DamageCause}
	 */
	DAMAGE,
	/**
	 * Health changed due to being healed.
	 * @see {@link org.spout.vanilla.api.event.cause.HealCause}
	 */
	HEAL,
	/**
	 * Health changed due to the entity spawning.
	 */
	SPAWN,
	/**
	 * Health changed due to a plugin other than a command.
	 */
	PLUGIN,
	/**
	 * Health changed due to some unknown reason.
	 */
	UNKNOWN;

}
