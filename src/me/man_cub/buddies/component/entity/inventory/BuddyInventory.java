package me.man_cub.buddies.component.entity.inventory;

import me.man_cub.buddies.BuddiesPlugin;
import me.man_cub.buddies.data.BuddiesData;
import me.man_cub.buddies.inventory.EntityInventory;
import me.man_cub.buddies.material.BuddiesMaterials;
import me.man_cub.buddies.material.block.crate.Crate;
import me.man_cub.buddies.material.block.crate.MegaCrate;
import me.man_cub.buddies.material.item.Weapon;

import org.spout.api.component.entity.EntityComponent;
import org.spout.api.inventory.ItemStack;

public class BuddyInventory extends EntityComponent {
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();

	public EntityInventory getInv() {
		return getData().get(BuddiesData.INVENTORY);
	}
	
	public ItemStack getHeldItem() {
		return getInv().get(0);
	}
	
	// TODO : Clean this up
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
	
	// TODO : Clean this up
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
