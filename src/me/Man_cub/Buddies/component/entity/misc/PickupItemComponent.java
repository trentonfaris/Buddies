package me.man_cub.buddies.component.entity.misc;

import java.util.List;

import me.man_cub.buddies.component.entity.BuddiesEntityComponent;
import me.man_cub.buddies.component.entity.inventory.BuddyInventory;
import me.man_cub.buddies.component.entity.substance.object.Item;
import me.man_cub.buddies.event.entity.network.EntityCollectItemEvent;

import org.spout.api.entity.Entity;

public class PickupItemComponent extends BuddiesEntityComponent {
	private final int DISTANCE = 2; //Might give users the ability to set this option in config.
	private List<Entity> nearbyEntities;
	private final int WAIT_RESET = 30;
	private int wait = 0;

	@Override
	public boolean canTick() {
		Health healthComponent = getOwner().get(Health.class);
		if (healthComponent != null) {
			if (!healthComponent.isDead() && wait != 0) {
				wait--;
				return false;
			}
			if (healthComponent.isDead()) {
				wait = WAIT_RESET;
				return false;
			}
		}
		nearbyEntities = getOwner().getWorld().getNearbyEntities(getOwner(), DISTANCE);
		return !nearbyEntities.isEmpty();
	}

	@Override
	public void onTick(float dt) {
		for (Entity entity : nearbyEntities) {
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
