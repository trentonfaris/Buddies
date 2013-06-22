package me.Man_cub.Buddies.material.item.weapon;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.data.weapon.WeaponType;
import me.Man_cub.Buddies.material.item.Weapon;

public class Tazer extends Weapon {
	
	public Tazer(String name, int id, WeaponType weaponType) {
		super(name, id, BuddiesMaterialModels.TAZER, weaponType);
		this.setDamage(3);
	}

}
