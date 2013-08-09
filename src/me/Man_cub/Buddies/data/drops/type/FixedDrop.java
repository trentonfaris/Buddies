package me.man_cub.buddies.data.drops.type;

import java.util.List;
import java.util.Random;
import java.util.Set;

import me.man_cub.buddies.data.drops.Drop;

import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
import org.spout.api.util.flag.Flag;

public class FixedDrop extends Drop {
	private final Material material;
	private final int amount;

	public FixedDrop(Material material, int amount) {
		this.material = material;
		this.amount = amount;
	}

	public Material getMaterial() {
		return this.material;
	}

	public int getAmount() {
		return this.amount;
	}

	@Override
	public List<ItemStack> getDrops(Random random, Set<Flag> flags, List<ItemStack> drops) {
		if (this.canDrop(random, flags)) {
			drops.add(new ItemStack(getMaterial(), getAmount()));
		}
		return drops;
	}

	@Override
	public boolean containsDrop(Material material) {
		return getMaterial().isMaterial(material);
	}
}
