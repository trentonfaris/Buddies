package me.man_cub.buddies.material.item.weapon;

import me.man_cub.buddies.component.entity.substance.object.projectile.PistolProj;
import me.man_cub.buddies.data.resources.BuddiesMaterialModels;
import me.man_cub.buddies.data.weapon.WeaponType;
import me.man_cub.buddies.material.item.RangedWeapon;

import org.spout.api.entity.Entity;
import org.spout.api.event.player.Action;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockFace;

public class Pistol extends RangedWeapon {
	
	public Pistol(String name, int id, int max_ammo, WeaponType weaponType) {
		super(name, id, BuddiesMaterialModels.PISTOL, max_ammo, weaponType);
		this.setRangedDamage(10);
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
			entity.getWorld().createAndSpawnEntity(entity.getPhysics().getPosition(), LoadOption.NO_LOAD, PistolProj.class);
			setAmmo(entity, getAmmo(entity) - 1);
			updateAmmo(entity);
		}
	}

}
