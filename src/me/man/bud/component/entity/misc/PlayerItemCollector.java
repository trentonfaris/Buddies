package me.man_cub.buddies.component.entity.misc;

import me.man_cub.buddies.component.entity.inventory.BuddyInventory;
import me.man_cub.buddies.component.entity.substance.object.Item;
import me.man_cub.buddies.event.entity.network.EntityCollectItemEvent;

import org.spout.api.entity.Entity;

public class PlayerItemCollector extends EntityItemCollector {
	
	@Override
	public void onTick(float dt) {
		for (Entity entity : getNearbyEntities()) {
			Item item = entity.get(Item.class);
			if (item != null && item.canBeCollected()) {
				getOwner().getNetwork().callProtocolEvent(new EntityCollectItemEvent(getOwner(), entity));
				BuddyInventory inv = getOwner().get(BuddyInventory.class);
				if (inv != null) {
					inv.add(item.getItemStack());
				}
				entity.remove();
			}
		}
	}

}
