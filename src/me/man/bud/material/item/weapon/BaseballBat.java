package me.man_cub.buddies.material.item.weapon;

import me.man_cub.buddies.data.resources.BuddiesMaterialModels;
import me.man_cub.buddies.data.weapon.WeaponType;
import me.man_cub.buddies.material.item.Weapon;

public class BaseballBat extends Weapon {
	
	public BaseballBat(String name, int id, WeaponType weaponType) {
		super(name, id, BuddiesMaterialModels.BAT, weaponType);
		this.setDamage(10);
	}

}
