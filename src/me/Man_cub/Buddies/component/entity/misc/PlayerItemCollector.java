package me.Man_cub.Buddies.component.entity.misc;

import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.entity.substance.object.Item;
import me.Man_cub.Buddies.event.entity.network.EntityCollectItemEvent;

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
