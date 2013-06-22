package me.Man_cub.Buddies.data.drops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
import org.spout.api.util.flag.Flag;
import org.spout.api.util.flag.FlagContainer;

public abstract class Drop extends FlagContainer {
	private double chance = 1.0;

	@Override
	public boolean matchFlags(Set<Flag> flags) {
		//		if (flags.contains(DropFlagSingle.NO_DROPS)) {
		//			return false;
		//		}
		return super.matchFlags(flags);
	}

	/**
	 * Tests if a drop is possible
	 * @param random to use
	 * @param dropFlags to check against
	 * @return True if a drop can be performed, False if not
	 */
	public boolean canDrop(Random random, Set<Flag> flags) {
		if (this.hasChance() && random.nextDouble() >= this.getChance()) {
			return false;
		}
		return this.matchFlags(flags);
	}

	/**
	 * Gets if chance is involved when this Drop is activated
	 * @return True if it has chance set, False if not
	 */
	public boolean hasChance() {
		return this.chance < 1.0;
	}

	/**
	 * Sets the chance for this Drop to be activated<br>
	 * @param chance to set to, value from 0 to 1
	 */
	public Drop setChance(double chance) {
		this.chance = chance;
		return this;
	}

	/**
	 * Gets the chance for this Drop to be activated
	 * @return chance
	 */
	public double getChance() {
		return this.chance;
	}

	@Override
	public Drop addFlags(Flag... dropFlags) {
		super.addFlags(dropFlags);
		return this;
	}

	@Override
	public Drop removeFlags(Flag... dropFlags) {
		super.removeFlags(dropFlags);
		return this;
	}

	/**
	 * Fills a list with the Drops
	 * @param random to use
	 * @param flags to evaluate against (contains no inverted flags)
	 * @param drops list to fill
	 * @return the inputed list of drops
	 */
	public abstract List<ItemStack> getDrops(Random random, Set<Flag> flags, List<ItemStack> drops);

	/**
	 * Gets the Drops
	 * @param random to use
	 * @param flags to evaluate against (contains no inverted flags)
	 * @return list of ItemStacks
	 */
	public final List<ItemStack> getDrops(Random random, Set<Flag> flags) {
		return getDrops(random, flags, new ArrayList<ItemStack>());
	}

	/**
	 * Tests if this Drop contains the Material specified
	 * @param material to check
	 * @return True if the material is contained, False if not
	 */
	public abstract boolean containsDrop(Material material);
}
