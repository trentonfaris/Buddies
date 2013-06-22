package me.Man_cub.Buddies.event.cause;

public enum HealCause {
	
	/**
	 * Health gained from hearts obtained in crates
	 */
	HEART,
	/**
	 * Health gained due to regeneration from a regeneration item.
	 */
	REGENERATION,
	/**
	 * Health gained due to an unknown source.
	 */
	UNKNOWN;
	
	public boolean equals(HealCause... causes) {
		for (HealCause cause : causes) {
			if (equals(cause)) {
				return true;
			}
		}
		return false;
	}
	

}
