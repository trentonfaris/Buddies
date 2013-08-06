package me.Man_cub.Buddies.material.item.weapon;

import me.Man_cub.Buddies.component.entity.substance.object.projectile.UziProj;
import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.data.weapon.WeaponType;
import me.Man_cub.Buddies.material.item.RangedWeapon;

import org.spout.api.entity.Entity;
import org.spout.api.event.player.Action;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockFace;

public class Uzi extends RangedWeapon {
	
	public Uzi(String name, int id, int max_ammo, WeaponType weaponType) {
		super(name, id, BuddiesMaterialModels.UZI, max_ammo, weaponType);
		this.setRangedDamage(3);
	}
	
	@Override
	public void onInteract(Entity entity, Block block, Action type, BlockFace clickedFace) {
		super.onInteract(entity, block, type, clickedFace);
		if (type == Action.LEFT_CLICK) {
			shoot(entity);
		}
	}
	
	@Override
	public void onInteract(Entity entity, Entity other, Action type) {
		super.onInteract(entity, other, type);
		if (type == Action.LEFT_CLICK) {
			shoot(entity);
		}
	}
	
	@Override
	public void onInteract(Entity entity, Action type) {
		super.onInteract(entity, type);
		if (type == Action.LEFT_CLICK) {
			shoot(entity);
		}
	}
	
	public void shoot(Entity entity) {
		if (canUse(entity)) {
			entity.getWorld().createAndSpawnEntity(entity.getPhysics().getPosition(), LoadOption.NO_LOAD, UziProj.class);
			setAmmo(entity, getAmmo(entity) - 1);
			updateAmmo(entity);
		}
	}

}
