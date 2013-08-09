package me.man_cub.buddies.event.cause;

public enum LivingSpawnCause {
	
	/**
	 * This spawn occurred as the result of being built.
	 */
	BUILT,
	/**
	 * This spawn occurred due to a custom reason.
	 */
	CUSTOM,
	/**
	 * This spawn occurred due to a spawner.
	 */
	SPAWNER,
	/**
	 * This spawn occurred due to an unknown reason.
	 */
	UNKNOWN;

	public boolean equals(LivingSpawnCause... causes) {
		for (LivingSpawnCause cause : causes) {
			if (equals(cause)) {
				return true;
			}
		}
		return false;
	}
	
}
