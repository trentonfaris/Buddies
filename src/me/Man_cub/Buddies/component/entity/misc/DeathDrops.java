package me.Man_cub.Buddies.component.entity.misc;

import java.util.List;

import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;
import me.Man_cub.Buddies.data.BuddiesData;

import org.spout.api.inventory.Inventory;
import org.spout.api.inventory.ItemStack;

public class DeathDrops extends BuddiesEntityComponent {
	
	public DeathDrops addDrop(ItemStack itemstack) {
		Inventory dropInventory = getOwner().getDatatable().get(BuddiesData.DROP_INVENTORY);
		dropInventory.add(itemstack);
		return this;
	}
	
	public List<ItemStack> getDrops() {
		return getOwner().getDatatable().get(BuddiesData.DROP_INVENTORY);
	}

}
