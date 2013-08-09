package me.man_cub.buddies.data.drops;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.spout.api.inventory.ItemStack;
import org.spout.api.util.flag.Flag;

public class SelectedDrops extends Drops {
	@Override
	public List<ItemStack> getDrops(Random random, Set<Flag> flags, List<ItemStack> drops) {
		if (!this.canDrop(random, flags) || this.isEmpty()) {
			return drops;
		} else {
			return getDrop(random.nextInt(getDropCount())).getDrops(random, flags, drops);
		}
	}

	@Override
	public SelectedDrops addFlags(Flag... dropFlags) {
		super.addFlags(dropFlags);
		return this;
	}
}
