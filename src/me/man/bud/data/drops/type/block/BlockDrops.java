package me.man_cub.buddies.data.drops.type.block;

import me.man_cub.buddies.data.drops.Drops;
import me.man_cub.buddies.data.drops.flag.DropFlags;
import me.man_cub.buddies.data.drops.flag.PlayerFlags;

public class BlockDrops extends Drops {
	/**
	 * All the drops spawned when the no creative player is involved
	 */
	public final Drops NOT_ADMIN;
	/**
	 * All of the drops when destroyed by an explosion
	 */
	public final Drops EXPLOSION;

	public BlockDrops() {
		this.NOT_ADMIN = this.forFlags(PlayerFlags.ADMIN.NOT);
		this.EXPLOSION = this.forFlags(DropFlags.EXPLOSION_DROPS);
	}

	@Override
	public BlockDrops clear() {
		super.clear();
		add(NOT_ADMIN).clear();
		return this;
	}
}
