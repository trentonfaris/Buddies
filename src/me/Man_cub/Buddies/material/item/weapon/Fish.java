package me.man_cub.buddies.material.item.weapon;

import me.man_cub.buddies.data.resources.BuddiesMaterialModels;
import me.man_cub.buddies.data.weapon.WeaponType;
import me.man_cub.buddies.material.item.Weapon;

public class Fish extends Weapon {
	
	public Fish(String name, int id, WeaponType weaponType) {
		super(name, id, BuddiesMaterialModels.FISH, weaponType);
		this.setDamage(15);
	}

}
