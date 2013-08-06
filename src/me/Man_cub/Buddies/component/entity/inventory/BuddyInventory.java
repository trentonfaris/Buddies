package me.Man_cub.Buddies.component.entity.inventory;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.data.BuddiesData;
import me.Man_cub.Buddies.inventory.EntityInventory;
import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.material.block.crate.Crate;
import me.Man_cub.Buddies.material.block.crate.MegaCrate;
import me.Man_cub.Buddies.material.item.Weapon;

import org.spout.api.component.entity.EntityComponent;
import org.spout.api.inventory.ItemStack;

public class BuddyInventory extends EntityComponent {
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();

	public EntityInventory getInv() {
		if (getData().get(BuddiesData.INVENTORY) != null) {
			return getData().get(BuddiesData.INVENTORY);
		} else {
			return null;
		}
	}
	
	public ItemStack getHeldItem() {
		return getInv().get(0);
	}
	
	// TODO : Clean this up using set and remove.
	public boolean add(ItemStack item) {
		EntityInventory inv = getInv();
		ItemStack primary = getHeldItem();
		if (item.getMaterial() instanceof Crate) {
			if (primary.getMaterial() instanceof Crate) {
				return false;
			} else {
				inv.remove(1);
				inv.set(1, primary);
				inv.remove(0);
				inv.set(0, item);
			}
		} else if (item.getMaterial() instanceof MegaCrate) {
			if (primary.getMaterial() instanceof MegaCrate) {
				return false;
			} else {
				inv.remove(1);
				inv.set(1, primary);
				inv.remove(0);
				inv.set(0, item);
			}
		} else if (item.getMaterial() instanceof Weapon) {
			if (primary.getMaterial() instanceof Crate) {
				inv.remove(1);
				inv.set(1, item);
			} else if (primary.getMaterial() instanceof MegaCrate) {
				inv.remove(1);
				inv.set(1, item);
			} else {
				inv.remove(0);
				inv.set(0, item);
			}
		} else {
			if (primary == null) {
				inv.set(0, item);
			} else {
				inv.set(1, item);
			}
		}
		return true;
	}
	
	public void remove(int slot) {
		EntityInventory inv = getInv();
		if (slot == 0) {
			inv.remove(0);
			inv.set(0, inv.get(1));
			inv.remove(1);
			inv.set(1, new ItemStack(BuddiesMaterials.AIR, 1));
		} else if (slot == 1){
			inv.remove(1);
			inv.set(1, new ItemStack(BuddiesMaterials.AIR, 1));
		}
	}
	
	public void clear() {
		getInv().clear();
	}
	
	public void update() {
		if (getInv() != null) {
			getInv().updateAll();
		}
	}
	
}
