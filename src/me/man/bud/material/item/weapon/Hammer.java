package me.man_cub.buddies.material.item.weapon;

import me.man_cub.buddies.data.resources.BuddiesMaterialModels;
import me.man_cub.buddies.data.weapon.WeaponType;
import me.man_cub.buddies.material.item.Weapon;

public class Hammer extends Weapon {
	
	public Hammer(String name, int id, WeaponType weaponType) {
		super(name, id, BuddiesMaterialModels.HAMMER, weaponType);
		this.setDamage(13);
	}

}
