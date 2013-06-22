package me.Man_cub.Buddies.data.drops.type;

import java.util.List;
import java.util.Random;
import java.util.Set;

import me.Man_cub.Buddies.data.drops.Drop;

import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
import org.spout.api.util.flag.Flag;

public class RandomDrop extends Drop {
	private final Material material;
	private final int[] amounts;

	public RandomDrop(Material material, int... amounts) {
		this.material = material;
		this.amounts = amounts;
	}

	public Material getMaterial() {
		return this.material;
	}

	@Override
	public List<ItemStack> getDrops(Random random, Set<Flag> flags, List<ItemStack> drops) {
		if (this.canDrop(random, flags)) {
			int amount = amounts[random.nextInt(amounts.length)];
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