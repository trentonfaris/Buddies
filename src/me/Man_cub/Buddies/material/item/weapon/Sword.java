package me.Man_cub.Buddies.material.item.weapon;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.data.weapon.WeaponType;
import me.Man_cub.Buddies.material.item.Weapon;

public class Sword extends Weapon {
	
	public Sword(String name, int id, WeaponType weaponType) {
		super(name, id, BuddiesMaterialModels.SWORD, weaponType);
		this.setDamage(10);
	}

}
