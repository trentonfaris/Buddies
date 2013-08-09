package me.man_cub.buddies.data.drops.flag;

import org.spout.api.inventory.ItemStack;
import org.spout.api.util.flag.FlagData;
import org.spout.api.util.flag.FlagSingle;

public class DropFlags {
	/**
	 * No drops should be spawned at all
	 */
	public static final FlagSingle NO_DROPS = new FlagSingle();
	/**
	 * Explosion drops
	 */
	public static final FlagSingle EXPLOSION_DROPS = new FlagSingle();
	/**
	 * Contents are passed in
	 */
	public static final FlagData<ItemStack[]> CONTENTS = new FlagData<ItemStack[]>();
}
