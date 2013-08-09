package me.man_cub.buddies.data.drops.type;

import java.util.List;
import java.util.Random;
import java.util.Set;

import me.man_cub.buddies.data.drops.Drop;

import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
import org.spout.api.util.flag.Flag;

public class RandomRangeDrop extends Drop {
	private final Material material;
	private final int min, max;

	public RandomRangeDrop(Material material, int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("min must be less than max");
		}
		this.material = material;
		this.min = min;
		this.max = max;
	}

	public Material getMaterial() {
		return this.material;
	}

	public int getMinAmount() {
		return this.min;
	}

	public int getMaxAmount() {
		return this.max;
	}

	@Override
	public List<ItemStack> getDrops(Random random, Set<Flag> flags, List<ItemStack> drops) {
		if (this.canDrop(random, flags)) {
			int amount = min + random.nextInt(max - min + 1);
			if (amount > 0) {
				drops.add(new ItemStack(getMaterial(), amount));
			}
		}
		return drops;
	}

	@Override
	public boolean containsDrop(Material material) {
		return getMaterial().isMaterial(material);
	}
}
