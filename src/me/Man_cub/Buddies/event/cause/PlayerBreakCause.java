package me.Man_cub.Buddies.event.cause;

import org.spout.api.entity.Player;
import org.spout.api.event.cause.PlayerCause;
import org.spout.api.geo.cuboid.Block;

public class PlayerBreakCause extends PlayerCause {
	private final Block block;

	public PlayerBreakCause(Player player, Block block) {
		super(player);
		this.block = block;
	}

	/**
	 * Gets the block being broken
	 * @return broken
	 */
	public Block getBlock() {
		return block;
	}
}
