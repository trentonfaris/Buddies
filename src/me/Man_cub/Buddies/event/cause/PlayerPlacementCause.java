package me.man_cub.buddies.event.cause;

import org.spout.api.entity.Player;
import org.spout.api.event.cause.PlayerCause;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.Material;

public class PlayerPlacementCause extends PlayerCause {
	private final Block block;
	private final Material toPlace;

	public PlayerPlacementCause(Player player, Material toPlace, Block block) {
		super(player);
		this.block = block;
		this.toPlace = toPlace;
	}

	/**
	 * Gets where the material is going to be placed
	 * @return placed
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * Gets the block material that is going to be placed
	 * @return to place
	 */
	public Material getPlacedMaterial() {
		return toPlace;
	}
}
