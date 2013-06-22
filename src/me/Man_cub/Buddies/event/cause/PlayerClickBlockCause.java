package me.Man_cub.Buddies.event.cause;

import org.spout.api.entity.Player;
import org.spout.api.event.cause.PlayerCause;
import org.spout.api.geo.cuboid.Block;

public class PlayerClickBlockCause extends PlayerCause {
	private final Block block;

	public PlayerClickBlockCause(Player player, Block block) {
		super(player);
		this.block = block;
	}

	/**
	 * Gets the block that has been clicked
	 * @return clicked block
	 */
	public Block getBlock() {
		return block;
	}
}
