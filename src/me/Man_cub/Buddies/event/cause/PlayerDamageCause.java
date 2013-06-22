package me.Man_cub.Buddies.event.cause;

import org.spout.api.entity.Player;
import org.spout.api.event.cause.PlayerCause;

public class PlayerDamageCause extends PlayerCause implements DamageCause<Player> {
	private final DamageType type;

	/**
	 * Contains the source and type of damage.
	 * @param player The player causing the damage
	 * @param type The cause of the damage.
	 */
	public PlayerDamageCause(Player player, DamageType type) {
		super(player);
		this.type = type;
	}

	@Override
	public DamageType getType() {
		return type;
	}
}
