package me.man_cub.buddies.component.entity.misc;

import java.util.List;

import me.man_cub.buddies.component.entity.BuddiesEntityComponent;
import me.man_cub.buddies.data.BuddiesData;

import org.spout.api.inventory.Inventory;
import org.spout.api.inventory.ItemStack;

public class DeathDrops extends BuddiesEntityComponent {
	
	public DeathDrops addDrop(ItemStack itemstack) {
		Inventory dropInventory = getOwner().getData().get(BuddiesData.DROP_INVENTORY);
		dropInventory.add(itemstack);
		return this;
	}
	
	public List<ItemStack> getDrops() {
		return getOwner().getData().get(BuddiesData.DROP_INVENTORY);
	}

}
