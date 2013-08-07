package me.Man_cub.Buddies.component.entity.misc;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;
import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.entity.player.HUD;
import me.Man_cub.Buddies.component.entity.substance.object.Item;
import me.Man_cub.Buddies.data.Animation;
import me.Man_cub.Buddies.data.BuddiesData;
import me.Man_cub.Buddies.data.BuddiesResources;
import me.Man_cub.Buddies.event.cause.DamageCause;
import me.Man_cub.Buddies.event.cause.DamageCause.DamageType;
import me.Man_cub.Buddies.event.cause.HealCause;
import me.Man_cub.Buddies.event.cause.HealthChangeCause;
import me.Man_cub.Buddies.event.cause.NullDamageCause;
import me.Man_cub.Buddies.event.entity.EntityDeathEvent;
import me.Man_cub.Buddies.event.entity.EntityAnimationEvent;
import me.Man_cub.Buddies.event.entity.EntityDamageEvent;
import me.Man_cub.Buddies.event.entity.EntityHealEvent;
import me.Man_cub.Buddies.event.entity.EntityHealthChangeEvent;
import me.Man_cub.Buddies.event.player.PlayerDeathEvent;
import me.Man_cub.Buddies.event.player.network.PlayerHealthEvent;
import me.Man_cub.Buddies.material.block.crate.Crate;

import org.spout.api.Client;
import org.spout.api.component.widget.LabelComponent;
import org.spout.api.component.widget.RenderPartPacksComponent;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.event.Cause;
import org.spout.api.geo.discrete.Point;
import org.spout.api.gui.Widget;
import org.spout.api.gui.render.RenderPart;
import org.spout.api.gui.render.RenderPartPack;
import org.spout.api.inventory.ItemStack;
import org.spout.api.math.Rectangle;
import org.spout.api.math.Vector3;
import org.spout.api.Platform;

public class Health extends BuddiesEntityComponent {
	private static final int DEATH_TIME_TICKS = 30;
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();
	
	//Damage
	private DamageCause<?> lastDamageCause = new NullDamageCause(DamageType.UNKNOWN);
	private Object lastDamager;
	
	//Client only
	private Widget heart;
	private Widget heartAmount;
	
	public Health() {
		if (plugin.getEngine().getPlatform() == Platform.CLIENT) {
			heart = ((Client) plugin.getEngine()).getScreenStack().createWidget();
			heartAmount = ((Client) plugin.getEngine()).getScreenStack().createWidget();
		}
	}
	
	@Override
	public boolean canTick() {
		return true;
	}
	
	@Override
	public void onAttached() {
		if (plugin.getEngine() instanceof Client && getOwner() instanceof Player) {
			final RenderPartPacksComponent heartRect = heart.add(RenderPartPacksComponent.class);
			final RenderPartPack heart_pack = new RenderPartPack(BuddiesResources.ICONS_MAT);
			final RenderPart heartPart = new RenderPart();
			heartPart.setColor(Color.WHITE);
			heartPart.setSprite(new Rectangle(-0.95f, -0.95f, 0.25f, 0.25f));
			heartPart.setSource(new Rectangle(0f / 256f, 16f / 256f, 16f / 256f, 16f / 256f));
			heart_pack.add(heartPart);
			heartRect.add(heart_pack);
			
			heartAmount.getTransform().setPosition(-0.85625f, -0.8875f);
			heartAmount.getTransform().setScale(2.5f);
			LabelComponent heartMessage = heartAmount.add(LabelComponent.class);
			heartMessage.setFont(BuddiesResources.FONT);
			
			getOwner().get(HUD.class).attachWidget(heart);
			getOwner().get(HUD.class).attachWidget(heartAmount);
			
		}
	}
	
	/*
	@Override
	public void onTick(float dt) {
		heartAmount.get(LabelComponent.class).setText(ChatStyle.WHITE + new Integer(getHealth()).toString());
	}*/
	
	private void onDeath() {
		EntityDeathEvent event;
		Entity owner = getOwner();
		if (owner instanceof Player) {
			event = new PlayerDeathEvent((Player) owner);
		} else {
			event = new EntityDeathEvent(owner);
		}
		if (!plugin.getEngine().getEventManager().callEvent(event).isCancelled()) {
			if (!(owner instanceof Player)) {
				owner.remove();
			}
			dropInventoryItems(owner);
			dropDropInventory(owner);
		}
	}
	
	private void dropInventoryItems(Entity owner) {
		BuddyInventory inventory = owner.get(BuddyInventory.class);
		if (inventory != null) {
			Set<ItemStack> toDrop = new HashSet<ItemStack>();
			toDrop.addAll(getOwner().get(BuddyInventory.class).getInv());
			Point position = owner.getPhysics().getPosition();
			for (ItemStack stack : toDrop) {
				if (stack != null && !(stack.getMaterial() instanceof Crate)) {
					Item.dropNaturally(position, stack);
				}
			}
			inventory.clear();
			inventory.update();
		}
	}
	
	private void dropDropInventory(Entity owner) {
		DeathDrops dropComponent = owner.get(DeathDrops.class);
		if (dropComponent != null) {
			List<ItemStack> drops = dropComponent.getDrops();
			Point entityPosition = owner.getPhysics().getPosition();
			for (ItemStack stack : drops) {
				if (stack != null && !(stack.getMaterial() instanceof Crate)) {
					Item.drop(entityPosition, stack, Vector3.ZERO);
				}
			}
		}
	}
	
	/**
	 * Gets the last cause of the damage
	 * @return the last damager
	 */
	public DamageCause<?> getLastDamageCause() {
		return lastDamageCause;
	}

	/**
	 * Gets the last entity that damages this entity
	 * @return last damager
	 */
	public Object getLastDamager() {
		return lastDamager;
	}

	/**
	 * Gets the maximum health this entity can have
	 * @return the maximum health
	 */
	public int getMaxHealth() {
		return getData().get(BuddiesData.MAX_HEALTH);
	}

	/**
	 * Sets the maximum health this entity can have
	 * @param maxHealth to set to
	 */
	public void setMaxHealth(int maxHealth) {
		getData().put(BuddiesData.MAX_HEALTH, maxHealth);
	}

	/**
	 * Sets the initial maximum health and sets the health to this value
	 * @param maxHealth of this health component
	 */
	public void setSpawnHealth(int maxHealth) {
		this.setMaxHealth(maxHealth);
		//Do not call setHealth yet, network has not been initialized if loading from file
		getData().put(BuddiesData.HEALTH, maxHealth);
	}

	/**
	 * Gets the health of this entity (hitpoints)
	 * @return the health value
	 */
	public int getHealth() {
		return getData().get(BuddiesData.HEALTH);
	}
	
	/**
	 * Sets the current health value for this entity
	 * @param health hitpoints value to set to
	 * @param cause of the change
	 */
	public void setHealth(int health, HealthChangeCause cause) {
		EntityHealthChangeEvent event = new EntityHealthChangeEvent(getOwner(), cause, health - getHealth());
		plugin.getEngine().getEventManager().callEvent(event);
		if (!event.isCancelled()) {
			if (getHealth() + event.getChange() > getMaxHealth()) {
				getData().put(BuddiesData.HEALTH, getMaxHealth());
			} else {
				getData().put(BuddiesData.HEALTH, getHealth() + event.getChange());
			}
		}

		if (getOwner() instanceof Player) {
			getOwner().getNetwork().callProtocolEvent(new PlayerHealthEvent(((Player) getOwner())));
		}
	}

	/**
	 * Heals this entity
	 * @param amount amount the entity will be healed by
	 */
	public void heal(int amount) {
		heal(amount, HealCause.UNKNOWN);
	}

	/**
	 * Heals this entity with the given {@link HealCause}
	 * @param amount amount the entity will be healed by
	 * @param cause cause of this entity being healed
	 */
	public void heal(int amount, HealCause cause) {
		EntityHealEvent event = new EntityHealEvent(getOwner(), amount, cause);
		EntityHealEvent healEvent = plugin.getEngine().getEventManager().callEvent(event);
		if (!healEvent.isCancelled()) {
			setHealth(getHealth() + event.getHealAmount(), HealthChangeCause.HEAL);
		}
	}
	
	/**
	 * Sets the health value to 0
	 * @param cause of the change
	 */
	public void kill(HealthChangeCause cause) {
		setHealth(0, cause);
	}
	
	/**
	 * Returns true if the entity is equal to or less than zero health remaining
	 * @return dead
	 */
	public boolean isDead() {
		return getHealth() <= 0;
	}

	/**
	 * @return
	 */
	public boolean isDying() {
		return getDeathTicks() > 0;
	}

	/**
	 * @return
	 */
	public int getDeathTicks() {
		return getData().get(BuddiesData.DEATH_TICKS);
	}

	/**
	 * @param deathTicks
	 */
	public void setDeathTicks(int deathTicks) {
		if (deathTicks > DEATH_TIME_TICKS) {
			deathTicks = DEATH_TIME_TICKS;
		}
		getData().put(BuddiesData.DEATH_TICKS, deathTicks);
	}

	/**
	 * Damages this entity
	 * @param amount amount the entity will be damaged by, can be modified based on armor and enchantments
	 */
	public void damage(int amount) {
		damage(amount, new NullDamageCause(DamageType.UNKNOWN));
	}

	/**
	 * Damages this entity with the given {@link DamageCause}.
	 * @param amount amount the entity will be damaged by, can be modified based on armor and enchantments
	 * @param cause cause of this entity being damaged
	 */
	public void damage(int amount, DamageCause<?> cause) {
		damage(amount, cause, true);
	}
	
	/**
	 * Damages this entity with the given {@link DamageCause} and damager.
	 * @param amount amount the entity will be damaged by, can be modified based on armor and enchantments
	 * @param cause cause of this entity being damaged
	 * @param sendHurtMessage whether to send the hurt packet to all players online
	 */
	public void damage(int amount, DamageCause<?> cause, boolean sendHurtMessage) {
		Cause<?> eventCause;
		if (cause instanceof Cause<?>) {
			eventCause = (Cause<?>) cause;
		} else {
			eventCause = new NullDamageCause(cause.getType());
		}
		// TODO take potion effects into account
		EntityDamageEvent event = plugin.getEngine().getEventManager().callEvent(new EntityDamageEvent(getOwner(), amount, eventCause, sendHurtMessage));
		if (event.isCancelled()) {
			return;
		}

		setHealth(getHealth() - event.getDamage(), HealthChangeCause.DAMAGE);
		lastDamager = event.getDamager();
		lastDamageCause = cause;

		if (event.getSendMessage()) {
			getOwner().getNetwork().callProtocolEvent(new EntityAnimationEvent(getOwner(), Animation.DAMAGE_ANIMATION));
			//getOwner().getNetwork().callProtocolEvent(new EntityStatusEvent(getOwner(), EntityStatusMessage.ENTITY_HURT));
			//getHurtEffect().playGlobal(getParent().getParent().getPosition());
		}
	}
	
	public boolean hasDeathAnimation() {
		return getData().get(BuddiesData.HAS_DEATH_ANIMATION);
	}

	public void setDeathAnimation(boolean hasDeathAnimation) {
		getData().put(BuddiesData.HAS_DEATH_ANIMATION, hasDeathAnimation);
	}

	public boolean hasInfiniteHealth() {
		return getHealth() == -1;
	}

}
