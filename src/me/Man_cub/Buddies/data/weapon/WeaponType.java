package me.Man_cub.Buddies.data.weapon;

import me.Man_cub.Buddies.data.drops.flag.WeaponTypeFlags;

import org.spout.api.util.flag.Flag;

public class WeaponType {
	public static final WeaponType OTHER = new WeaponType(WeaponTypeFlags.OTHER);
	public static final WeaponType BAT = new WeaponType(WeaponTypeFlags.BAT);
	public static final WeaponType SWORD = new WeaponType(WeaponTypeFlags.SWORD);
	public static final WeaponType TAZER = new WeaponType(WeaponTypeFlags.TAZER);
	public static final WeaponType HAMMER = new WeaponType(WeaponTypeFlags.HAMMER);
	public static final WeaponType FISH = new WeaponType(WeaponTypeFlags.FISH);
	public static final WeaponType PISTOL = new WeaponType(WeaponTypeFlags.PISTOL);
	public static final WeaponType FIREBALL = new WeaponType(WeaponTypeFlags.FIREBALL);
	public static final WeaponType GRENDAE = new WeaponType(WeaponTypeFlags.GRENADE);
	public static final WeaponType SHOTGUN = new WeaponType(WeaponTypeFlags.SHOTGUN);
	public static final WeaponType UZI = new WeaponType(WeaponTypeFlags.UZI);
	public static final WeaponType BAZOOKA = new WeaponType(WeaponTypeFlags.BAZOOKA);
	public static final WeaponType GATLING_GUN = new WeaponType(WeaponTypeFlags.GATLING_GUN);
	public static final WeaponType FREEZE_RAY = new WeaponType(WeaponTypeFlags.FREEZE_RAY);
	public static final WeaponType FLAMETHROWER = new WeaponType(WeaponTypeFlags.FLAMETHROWER);
	public static final WeaponType POWER_ORB_GUN = new WeaponType(WeaponTypeFlags.POWER_ORB_GUN);
	private final Flag weaponFlag;
	
	public WeaponType(Flag weaponFlag) {
		this.weaponFlag = weaponFlag;
	}
	
	public Flag getWeaponFlag() {
		return this.weaponFlag;
	}
	
	public Flag getDropFlag() {
		return this.weaponFlag;
	}

}
