package me.Man_cub.Buddies.data.drops;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.spout.api.inventory.ItemStack;
import org.spout.api.util.flag.Flag;

public class SwitchDrops extends Drops {
	public final Drops TRUE = new Drops();
	public final Drops FALSE = new Drops();

	@Override
	public List<ItemStack> getDrops(Random random, Set<Flag> flags, List<ItemStack> drops) {
		if (this.canDrop(random, flags)) {
			drops = this.TRUE.getDrops(random, flags, drops);
		} else {
			drops = this.FALSE.getDrops(random, flags, drops);
		}
		return drops;
	}

	@Override
	public SwitchDrops addFlags(Flag... dropFlags) {
		super.addFlags(dropFlags);
		return this;
	}

	@Override
	public SwitchDrops setChance(double chance) {
		super.setChance(chance);
		return this;
	}
}
