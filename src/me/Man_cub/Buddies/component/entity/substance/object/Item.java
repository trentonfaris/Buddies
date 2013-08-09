package me.man_cub.buddies.component.entity.substance.object;

import me.man_cub.buddies.component.entity.misc.Health;
import me.man_cub.buddies.data.BuddiesData;
import me.man_cub.buddies.data.configuration.BuddiesConfig;
import me.man_cub.buddies.material.BuddiesMaterials;

import org.spout.api.component.entity.PhysicsComponent;
import org.spout.api.data.Data;
import org.spout.api.entity.Entity;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.discrete.Point;
import org.spout.api.inventory.ItemStack;
import org.spout.api.math.Vector3;
import org.spout.physics.collision.shape.BoxShape;

public class Item extends Substance {
	/**
	 * The default delay in ms before the item can be picked up for a dropped item
	 */
	public static final long DROP_PICKUP_DELAY = 495;
	private float timeLeft = BuddiesConfig.ITEM_SPAWN_TIME.getFloat();
	
	@Override
	public void onAttached() {
		super.onAttached();
		PhysicsComponent physics = getOwner().getPhysics();
		physics.activate(1f, new BoxShape(0.05F, 0.05F, 0.05F), false, true);
		physics.setRestitution(0f);
		getOwner().add(Health.class).setMaxHealth(100);
	}
	
	@Override
	public boolean canTick() {
		return false;
	}
	
	public ItemStack getItemStack() {
		return getData().get(Data.HELD_ITEM);
	}
	
	public void setItemStack(ItemStack stack) {
		getData().put(Data.HELD_ITEM, stack);
	}
	
	/**
	 * Gets the time from which this item can be picked up
	 * @return uncollectable time in milliseconds
	 */
	public long getUncollectableTime() {
		return getData().get(BuddiesData.UNCOLLECTABLE_TICKS).longValue();
	}

	/**
	 * Sets the time from which this item can be picked up
	 * @param uncollectableTime in milliseconds
	 */
	public void setUncollectableTime(long uncollectableTime) {
		getData().put(BuddiesData.UNCOLLECTABLE_TICKS, Long.valueOf(uncollectableTime));
	}

	/**
	 * Sets the delay from the current time until this item can be picked up
	 * @param uncollectableDelay in milliseconds
	 */
	public void setUncollectableDelay(long uncollectableDelay) {
		setUncollectableTime(getOwner().getWorld().getAge() + uncollectableDelay);
	}

	public boolean canBeCollected() {
		return getUncollectableTime() < getOwner().getWorld().getAge();
	}

	/**
	 * Drops an item at the position with the item stack specified with a natural random velocity
	 * @param position to spawn the item
	 * @param itemStack to set to the item
	 * @return the Item entity
	 */
	public static Item dropNaturally(Point position, ItemStack itemStack) {
		Vector3 velocity = new Vector3(Math.random() * 2, 0.3, Math.random() * 2);
		return drop(position, itemStack, velocity);
	}
	
	/**
	 * Drops an item at the position with the item stack specified
	 * @param position to spawn the item
	 * @param itemStack to set to the item
	 * @param velocity to drop at
	 * @return the Item entity
	 */
	public static Item drop(Point position, ItemStack itemStack, Vector3 velocity) {
		if (itemStack == null || itemStack.getMaterial() == BuddiesMaterials.AIR) {
			throw new IllegalArgumentException("The dropped item can not be null or air!");
		}
		Entity entity = position.getWorld().createEntity(position, Item.class);
		Item item = entity.add(Item.class);
		item.setUncollectableDelay(DROP_PICKUP_DELAY);
		item.setItemStack(itemStack);
		entity.getPhysics().impulse(velocity);
		if (position.getChunk(LoadOption.NO_LOAD) != null) {
			position.getWorld().spawnEntity(entity);
		}
		return item;
	}
	
	@Override
	public void onTick(float dt) {
		timeLeft -= dt;
		if (timeLeft <= 0.05) {
			getOwner().remove();
		}
	}
	
}
