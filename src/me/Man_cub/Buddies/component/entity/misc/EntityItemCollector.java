package me.Man_cub.Buddies.component.entity.misc;

import java.util.List;

import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;
import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.entity.substance.object.Item;
import me.Man_cub.Buddies.data.configuration.BuddiesConfig;
import me.Man_cub.Buddies.event.entity.EntityCollectItemEvent;
import me.Man_cub.Buddies.material.BuddiesItemMaterial;
import me.Man_cub.Buddies.material.item.Weapon;

import org.spout.api.entity.Entity;
import org.spout.api.math.Vector3;

public class EntityItemCollector extends BuddiesEntityComponent {
	private final int DISTANCE = BuddiesConfig.ITEM_PICKUP_RANGE.getInt();
	private List<Entity> nearbyEntities;
	private final int WAIT_RESET = 30;
	private int wait = 0;
	
	@Override
	public boolean canTick() {
		Health health = getOwner().get(Health.class);
		if (health != null) {
			if (!health.isDead() && wait != 0) {
				wait --;
				return false;
			}
			if (health.isDead()) {
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
			if (item == null) {
				continue;
			}
			BuddyInventory inv = getOwner().get(BuddyInventory.class);
			boolean equip = false;
			if (!equip && (item.getItemStack().getMaterial() instanceof BuddiesItemMaterial || inv.getHeldItem() == null)) {
				if (inv.getHeldItem() == null) {
					equip = true;
				} else if (inv.getHeldItem().getMaterial() instanceof BuddiesItemMaterial) {
					BuddiesItemMaterial itemMaterial = (BuddiesItemMaterial) item.getItemStack().getMaterial();
					BuddiesItemMaterial equipMaterial = (BuddiesItemMaterial) inv.getHeldItem().getMaterial();
					if (equipMaterial.getDamage() < itemMaterial.getDamage()) {
						equip = true;
					// TODO: Make this check for ammo in weapon instead of item data
					} else if (itemMaterial.getDamage() == equipMaterial.getDamage() && itemMaterial instanceof Weapon && equipMaterial instanceof Weapon && inv.getHeldItem().getData() > item.getItemStack().getData()) {
						equip = true;
					}
				}
				if (equip) {
					getOwner().getNetwork().callProtocolEvent(new EntityCollectItemEvent(getOwner(), entity));
					if (inv.getHeldItem() != null) {
						Item.drop(getOwner().getScene().getPosition(), inv.getHeldItem(), Vector3.ZERO);
					}
					inv.getInv().set(0, item.getItemStack(), true);
					entity.remove();
					break;
				}
			}
		}
	}
	
	protected List<Entity> getNearbyEntities() {
		return nearbyEntities;
	}

}
