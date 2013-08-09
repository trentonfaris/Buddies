package me.man_cub.buddies.material.item.weapon;

import me.man_cub.buddies.data.resources.BuddiesMaterialModels;
import me.man_cub.buddies.data.weapon.WeaponType;
import me.man_cub.buddies.material.item.Weapon;

public class Sword extends Weapon {
	
	public Sword(String name, int id, WeaponType weaponType) {
		super(name, id, BuddiesMaterialModels.SWORD, weaponType);
		this.setDamage(10);
	}

}
