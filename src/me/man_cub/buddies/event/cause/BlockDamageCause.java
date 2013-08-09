package me.man_cub.buddies.event.cause;

import org.spout.api.event.Cause;
import org.spout.api.event.cause.BlockCause;
import org.spout.api.geo.cuboid.Block;

public class BlockDamageCause extends BlockCause implements DamageCause<Block> {
	private final DamageType type;

	/**
	 * Contains the source and type of damage.
	 * @param block The block causing the damage
	 * @param type The cause of the damage.
	 */
	public BlockDamageCause(Block block, DamageType type) {
		super(block);
		this.type = type;
	}

	/**
	 * Contains the source and type of damage.
	 * @param parent cause of this cause
	 * @param block who caused this cause
	 * @param type who caused this cause
	 */
	public BlockDamageCause(Cause<?> parent, Block block, DamageType type) {
		super(parent, block);
		this.type = type;
	}

	@Override
	public DamageType getType() {
		return type;
	}
}
