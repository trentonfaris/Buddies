package me.Man_cub.Buddies.material.item.weapon;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.data.weapon.WeaponType;
import me.Man_cub.Buddies.material.item.Weapon;

public class Fish extends Weapon {
	
	public Fish(String name, int id, WeaponType weaponType) {
		super(name, id, BuddiesMaterialModels.FISH, weaponType);
		this.setDamage(15);
	}

}
