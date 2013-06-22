package me.Man_cub.Buddies.component.entity.living.buddy;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.entity.living.Living;
import me.Man_cub.Buddies.component.entity.misc.EntityBody;
import me.Man_cub.Buddies.component.entity.misc.Health;
import me.Man_cub.Buddies.component.entity.misc.PickupItemComponent;
import me.Man_cub.Buddies.component.entity.substance.object.Item;
import me.Man_cub.Buddies.data.BuddiesData;
import me.Man_cub.Buddies.data.configuration.BuddiesConfig;
import me.Man_cub.Buddies.event.entity.BuddyAbilityChangeEvent;
import me.Man_cub.Buddies.event.player.network.PlayerAbilityUpdateEvent;
import me.Man_cub.Buddies.protocol.entity.BuddyEntityProtocol;

import org.spout.api.component.entity.TextModelComponent;
import org.spout.api.data.Data;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.inventory.ItemStack;
import org.spout.api.math.GenericMath;
import org.spout.api.math.Vector3;
import org.spout.api.math.VectorMath;

public class Buddy extends Living {
	public static final int SPAWN_HEALTH = 100;
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();

	@Override
	public void onAttached() {
		super.onAttached();
		Entity holder = getOwner();
		holder.add(PickupItemComponent.class);
		// holder.add(DiggingComponent.class);
		holder.getNetwork().setEntityProtocol(
				BuddiesPlugin.BUDDIES_PROTOCOL_ID, new BuddyEntityProtocol());
		// Add height offset if loading from disk
		// if (holder instanceof Player) {
		// ((Player) holder).teleport(holder.getTransform().getPosition().add(0,
		// 1.85F, 0));
		// }
		if (getAttachedCount() == 1) {
			holder.add(Health.class).setSpawnHealth(SPAWN_HEALTH);
		}
		TextModelComponent textModel = getOwner().get(TextModelComponent.class);
		if (textModel != null) {
			textModel.setSize(0.5f);
			textModel.setTranslation(new Vector3(0, 3f, 0));
		}
	}

	public boolean isSprinting() {
		return getOwner().getData().get(BuddiesData.IS_SPRINTING);
	}

	public void setSprinting(boolean isSprinting) {
		getOwner().getData().put(BuddiesData.IS_SPRINTING, isSprinting);
		sendMetaData();
	}

	public boolean isFalling() {
		return getOwner().getData().get(BuddiesData.IS_FALLING);
	}

	public void setFalling(boolean isFalling) {
		getOwner().getData().put(BuddiesData.IS_FALLING, isFalling);
	}

	public boolean isJumping() {
		return getOwner().getData().get(BuddiesData.IS_JUMPING);
	}

	public void setJumping(boolean isJumping) {
		getOwner().getData().put(BuddiesData.IS_JUMPING, isJumping);
	}

	public boolean isInWater() {
		// TODO : Water -> return
		// getOwner().getData().get(BuddiesData.IS_IN_WATER);
		return false;
	}

	public void setInWater(boolean inWater) {
		// TODO : Water -> getOwner().getData().put(BuddiesData.IS_IN_WATER,
		// inWater);
	}

	public String getName() {
		return getDatatable().get(Data.NAME);
	}

	public void setName(String name) {
		getDatatable().put(Data.NAME, name);
		TextModelComponent textModel = getOwner().get(TextModelComponent.class);
		if (textModel != null) {
			textModel.setText(name);
		}
	}

	public boolean isOp() {
		return getOwner() instanceof Player
				&& BuddiesConfig.OPS.isOp(getName());
	}

	// TODO: Drop item stuff
	/**
	 * Drops the item specified into the direction the player looks, with slight randomness
	 * @param item to drop
	 */
	public void dropItem(ItemStack item) {
		final Transform dropFrom;
		EntityBody body = getBody();
		if (body != null) {
			dropFrom = body.getBodyTransform();
		} else {
			dropFrom = getOwner().getScene().getTransform();
		}
		// Some constants
		final double impulseForce = 0.3;
		final float maxXZForce = 0.02f;
		final float maxYForce = 0.1f;

		// Create a velocity vector using the transform, apply (random) force
		Vector3 impulse = dropFrom.getRotation().getDirection().multiply(impulseForce);

		// Random rotational offset to avoid dropping at the same position
		Random rand = GenericMath.getRandom();
		float xzLength = maxXZForce * rand.nextFloat();
		float yLength = maxYForce * (rand.nextFloat() - rand.nextFloat());
		impulse = impulse.add(VectorMath.getRandomDirection2D(rand).multiply(xzLength).toVector3(yLength));

		// Slightly dropping upwards
		impulse = impulse.add(0.0, 0.1, 0.0);

		// Conversion factor, some sort of unit problem
		//TODO: This needs an actual value and this value might change when gravity changes!
		impulse = impulse.multiply(100);

		// Finally drop using a 4 second pickup delay
		Item spawnedItem = Item.drop(dropFrom.getPosition(), item, impulse);
		spawnedItem.setUncollectableDelay(4000);
	}

	/**
	 * Drops the player's current item.
	 */
	public void dropItem() {
		BuddyInventory inventory = getOwner().get(BuddyInventory.class);
		if (inventory != null) {
			ItemStack drop = inventory.getHeldItem();
			if (drop == null) {
				return;
			} else {
				drop = drop.clone().setAmount(1);
			}
			// TODO : Do this.
			//drop.addAmount(-1);
			dropItem(drop);
		}
	}

	// Abilities
	public void setFlying(boolean isFlying, boolean updateClient) {
		Boolean previous = getOwner().getData().put(BuddiesData.IS_FLYING,
				isFlying);
		if (callAbilityChangeEvent().isCancelled()) {
			getOwner().getData().put(BuddiesData.IS_FLYING, previous);
			return;
		}
		updateAbilities(updateClient);
	}

	public void setFlying(boolean isFlying) {
		setFlying(isFlying, true);
	}

	public boolean isFlying() {
		return getOwner().getData().get(BuddiesData.IS_FLYING);
	}

	public void setFlyingSpeed(byte speed, boolean updateClient) {
		byte previous = getOwner().getData()
				.put(BuddiesData.FLYING_SPEED, speed).byteValue();
		if (callAbilityChangeEvent().isCancelled()) {
			getOwner().getData().put(BuddiesData.FLYING_SPEED, previous);
			return;
		}
		updateAbilities(updateClient);
	}

	public void setFlyingSpeed(byte speed) {
		setFlyingSpeed(speed, true);
	}

	public byte getFlyingSpeed() {
		return getOwner().getData().get(BuddiesData.FLYING_SPEED).byteValue();
	}

	public void setWalkingSpeed(byte speed, boolean updateClient) {
		byte previous = getOwner().getData()
				.put(BuddiesData.WALKING_SPEED, speed).byteValue();
		if (callAbilityChangeEvent().isCancelled()) {
			getOwner().getData().put(BuddiesData.WALKING_SPEED, previous);
			return;
		}
		updateAbilities(updateClient);
	}

	public void setWalkingSpeed(byte speed) {
		setWalkingSpeed(speed, true);
	}

	public byte getWalkingSpeed() {
		return getOwner().getData().get(BuddiesData.WALKING_SPEED).byteValue();
	}

	public void setCanFly(boolean canFly, boolean updateClient) {
		Boolean previous = getOwner().getData()
				.put(BuddiesData.CAN_FLY, canFly);
		if (callAbilityChangeEvent().isCancelled()) {
			getOwner().getData().put(BuddiesData.CAN_FLY, previous);
			return;
		}
		updateAbilities(updateClient);
	}

	public void setCanFly(boolean canFly) {
		setCanFly(canFly, true);
	}

	public boolean canFly() {
		return getOwner().getData().get(BuddiesData.CAN_FLY);
	}

	public BuddyAbilityChangeEvent callAbilityChangeEvent() {
		return plugin.getEngine().getEventManager()
				.callEvent(new BuddyAbilityChangeEvent(this));
	}

	// This is here to eliminate repetitive code above
	private void updateAbilities(boolean updateClient) {
		if (!updateClient || !(getOwner() instanceof Player)) {
			return;
		}
		((Player) getOwner()).getNetworkSynchronizer().callProtocolEvent(
				new PlayerAbilityUpdateEvent((Player) getOwner()));
	}

	public void updateAbilities() {
		updateAbilities(true);
	}

	private final AtomicReference<Point> livePosition = new AtomicReference<Point>(
			null);

	@Override
	public boolean canTick() {
		return true;
	}

	@Override
	public void onTick(float dt) {
		super.onTick(dt);
		final Point position = getOwner().getScene().getPosition();
		livePosition.set(position);
		// TODO : Water -> setInWater(position.getBlock().getMaterial()
		// instanceof Water);
	}

	public Point getLivePosition() {
		return livePosition.get();
	}

	public void setLivePosition(Point point) {
		livePosition.set(point);
		getOwner().getScene().setPosition(point);
	}

}
