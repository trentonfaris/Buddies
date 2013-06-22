package me.Man_cub.Buddies.material.item;

import me.Man_cub.Buddies.data.BuddiesData;
import me.Man_cub.Buddies.data.weapon.WeaponType;

import org.spout.api.entity.Entity;

public class RangedWeapon extends Weapon {
	private int rangedDamage = 1;
	private int max_ammo;

	public RangedWeapon(String name, int id, String model, int max_ammo, WeaponType weaponType) {
		super(name, id, model, weaponType);
		this.max_ammo = max_ammo;
	}
	
	public int getRangedDamage() {
		return rangedDamage;
	}
	
	public RangedWeapon setRangedDamage(int damage) {
		this.rangedDamage = damage;
		return this;
	}
	
	public int getMaxAmmo() {
		return max_ammo;
	}
	
	public Weapon setMaxAmmo(int max_ammo) {
		this.max_ammo = max_ammo;
		return this;
	}
	
	public int getAmmo(Entity source) {
		if (source.getData().get(BuddiesData.AMMO) != null) {
			return source.getData().get(BuddiesData.AMMO);
		} else {
			return 0;
		}
	}
	
	public void setAmmo(Entity source, int ammo) {
		source.getData().put(BuddiesData.AMMO, (int) ammo);
	}
	
	public void updateAmmo(Entity source) {
		int ammo = getAmmo(source);
		if (ammo < 0) {
			source.getData().put(BuddiesData.AMMO, 0);
		} else {
			source.getData().put(BuddiesData.AMMO, ammo);
		}
	}
	
	public boolean canUse(Entity source) {
		if (getAmmo(source) == 0) {
			return false;
		} else {
			return true;
		}
	}
	
}
