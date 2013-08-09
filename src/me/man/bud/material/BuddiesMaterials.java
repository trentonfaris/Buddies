package me.man_cub.buddies.material;

import gnu.trove.map.hash.TShortObjectHashMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import me.man_cub.buddies.BuddiesPlugin;
import me.man_cub.buddies.data.weapon.WeaponType;
import me.man_cub.buddies.material.block.base.BlueBase;
import me.man_cub.buddies.material.block.base.GreenBase;
import me.man_cub.buddies.material.block.base.RedBase;
import me.man_cub.buddies.material.block.base.YellowBase;
import me.man_cub.buddies.material.block.crate.Crate;
import me.man_cub.buddies.material.block.crate.MegaCrate;
import me.man_cub.buddies.material.block.natural.Border;
import me.man_cub.buddies.material.block.natural.Dirt;
import me.man_cub.buddies.material.block.natural.Fire;
import me.man_cub.buddies.material.block.natural.Grass;
import me.man_cub.buddies.material.block.natural.Snow;
import me.man_cub.buddies.material.block.natural.Stone;
import me.man_cub.buddies.material.block.pad.blue.BluePadCenter;
import me.man_cub.buddies.material.block.pad.blue.BluePadEdge;
import me.man_cub.buddies.material.block.pad.blue.BluePadVertex;
import me.man_cub.buddies.material.block.pad.green.GreenPadCenter;
import me.man_cub.buddies.material.block.pad.green.GreenPadEdge;
import me.man_cub.buddies.material.block.pad.green.GreenPadVertex;
import me.man_cub.buddies.material.block.pad.red.RedPadCenter;
import me.man_cub.buddies.material.block.pad.red.RedPadEdge;
import me.man_cub.buddies.material.block.pad.red.RedPadVertex;
import me.man_cub.buddies.material.block.pad.yellow.YellowPadCenter;
import me.man_cub.buddies.material.block.pad.yellow.YellowPadEdge;
import me.man_cub.buddies.material.block.pad.yellow.YellowPadVertex;
import me.man_cub.buddies.material.item.weapon.BaseballBat;
import me.man_cub.buddies.material.item.weapon.Bazooka;
import me.man_cub.buddies.material.item.weapon.Fireball;
import me.man_cub.buddies.material.item.weapon.Fish;
import me.man_cub.buddies.material.item.weapon.GatlingGun;
import me.man_cub.buddies.material.item.weapon.Grenade;
import me.man_cub.buddies.material.item.weapon.Hammer;
import me.man_cub.buddies.material.item.weapon.Pistol;
import me.man_cub.buddies.material.item.weapon.Shotgun;
import me.man_cub.buddies.material.item.weapon.Sword;
import me.man_cub.buddies.material.item.weapon.Tazer;
import me.man_cub.buddies.material.item.weapon.Uzi;

import org.spout.api.material.BlockMaterial;
import org.spout.api.material.Material;
import org.spout.api.material.MaterialRegistry;
import org.spout.api.util.map.concurrent.AtomicShortArray;

public class BuddiesMaterials {
	public static short id = 0;
	
	// Natural
	public static final BlockMaterial AIR = BlockMaterial.AIR;
	public static final Border BORDER = new Border("Border", id++);
	public static final Dirt DIRT = new Dirt("Dirt", id++);
	public static final Fire FIRE = new Fire("Fire", id++);
	public static final Grass GRASS = new Grass("Grass", id++);
	public static final Snow SNOW = new Snow("Snow", id++);
	public static final Stone STONE = new Stone("Stone", id++);
	
	//Base
	public static final BlueBase BLUE_BASE = new BlueBase("Blue Base", id++);
	public static final GreenBase GREEN_BASE = new GreenBase("Green Base", id++);
	public static final RedBase RED_BASE = new RedBase("Red Base", id++);
	public static final YellowBase YELLOW_BASE = new YellowBase("Yellow Base", id++);
	
	//Pad
	public static final BluePadCenter BLUE_PAD_CENTER = new BluePadCenter("Blue Pad Center", id++);
	public static final BluePadEdge BLUE_PAD_EDGE = new BluePadEdge("Blue Pad Edge", id++);
	public static final BluePadVertex BLUE_PAD_VERTEX = new BluePadVertex("Blue Pad Vertex", id++);
	public static final GreenPadCenter GREEN_PAD_CENTER = new GreenPadCenter("Green Pad Center", id++);
	public static final GreenPadEdge GREEN_PAD_EDGE = new GreenPadEdge("Green Pad Edge", id++);
	public static final GreenPadVertex GREEN_PAD_VERTEX = new GreenPadVertex("Green Pad Vertex", id++);
	public static final RedPadCenter RED_PAD_CENTER = new RedPadCenter("Red Pad Center", id++);
	public static final RedPadEdge RED_PAD_EDGE = new RedPadEdge("Red Pad Edge", id++);
	public static final RedPadVertex RED_PAD_VERTEX = new RedPadVertex("Red Pad Vertex", id++);
	public static final YellowPadCenter YELLOW_PAD_CENTER = new YellowPadCenter("Yellow Pad Center", id++);
	public static final YellowPadEdge YELLOW_PAD_EDGE = new YellowPadEdge("Yellow Pad Edge", id++);
	public static final YellowPadVertex YELLOW_PAD_VERTEX = new YellowPadVertex("Yellow Pad Vertex", id++);
	
	//Crates
	public static final Crate CRATE = new Crate("Crate", id++);
	public static final MegaCrate MEGACRATE = new MegaCrate("Mega Crate", id++);
	
	//Weapons
	public static final BaseballBat BAT = new BaseballBat("Bat", id++, WeaponType.BAT);
	public static final Bazooka BAZOOKA = new Bazooka("Bazooka", id++, 5, WeaponType.BAZOOKA);
	public static final Fireball FIREBALL = new Fireball("Fireball", id++, 10, WeaponType.FIREBALL);
	public static final Fish FISH = new Fish("Fish", id++, WeaponType.FISH);
	public static final GatlingGun GATLING_GUN = new GatlingGun("Gatling gun", id++, 100, WeaponType.GATLING_GUN);
	public static final Grenade GRENADE = new Grenade("Grenade", id++, 5, WeaponType.GRENDAE);
	public static final Hammer HAMMER = new Hammer("Hammer", id++, WeaponType.HAMMER);
	public static final Pistol PISTOL = new Pistol("Pistol", id++, 15, WeaponType.PISTOL);
	public static final Shotgun SHOTGUN = new Shotgun("Shotgun", id++, 12, WeaponType.SHOTGUN);
	public static final Sword SWORD = new Sword("Sword", id++, WeaponType.SWORD);
	public static final Tazer TAZER = new Tazer("Tazer", id++, WeaponType.TAZER);
	public static final Uzi UZI = new Uzi("Uzi", id++, 50, WeaponType.UZI);
	
	//Support
	//Heart
	
	private static boolean initialized = false;
	private final static AtomicShortArray conversionTable = new AtomicShortArray(Short.MAX_VALUE);
	private final static TShortObjectHashMap<Material> reverseTable = new TShortObjectHashMap<Material>(500);
	
	static {
		for (Field field : BuddiesMaterials.class.getFields()) {
			try {
				if ((field.getModifiers() & (Modifier.STATIC | Modifier.PUBLIC)) > 0) {
					Object temp = field.get(null);
					if (temp instanceof BuddiesMaterial) {
						BuddiesMaterial material = (BuddiesMaterial) temp;
						Material mat = (Material) material;
						if (mat.isSubMaterial()) {
							mat = mat.getParentMaterial();
						}
						reverseTable.put((short) material.getBuddiesId(), mat);
						short minMask;
						short dataMask;
						if (material instanceof BuddiesBlockMaterial) {
							minMask = MaterialRegistry.getMinimumDatamask((BuddiesBlockMaterial) material);
							dataMask = ((BuddiesBlockMaterial) material).getDataMask();
						} else if (material instanceof BuddiesItemMaterial) {
							minMask = MaterialRegistry.getMinimumDatamask((BuddiesItemMaterial) material);
							dataMask = ((BuddiesItemMaterial) material).getDataMask();
						} else {
							throw new IllegalStateException("All materials should be either Blocks or Items");
						}
						if (minMask != dataMask) {
							throw new IllegalStateException("Submaterial data mask is not set to minimum valid value (exp = " + minMask + ", actual = " + dataMask + " for material " + material);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public static Material getMaterial(short buddiesId) {
		return reverseTable.get(buddiesId);
	}
	
	public static Material getMaterial(short buddiesId, short data) {
		return reverseTable.get(buddiesId).getSubMaterial(data);
	}
	
	public static short getBuddiesId(Material material) {
		if (!(material instanceof BuddiesMaterial)) {
			return (short) 0;
		}
		
		return (short) ((BuddiesMaterial) material).getBuddiesId();
	}
	
	public static short getBuddiesId(short id) {
		if (id <= 0) {
			return 0;
		}
		short buddiesId = conversionTable.get(id);
		if (buddiesId == 0) {
			buddiesId = getBuddiesId(MaterialRegistry.get(id));
			conversionTable.set(id, buddiesId);
		}
		return buddiesId;
	}
	
	public static void initialize() {
		if (initialized) {
			return;
		}
		for (Field field : BuddiesMaterials.class.getFields()) {
			try {
				if (field == null || ((field.getModifiers() & (Modifier.STATIC | Modifier.PUBLIC)) != (Modifier.STATIC | Modifier.PUBLIC)) || !BuddiesMaterial.class.isAssignableFrom(field.getType())) {
					continue;
				}
				BuddiesMaterial material = (BuddiesMaterial) field.get(null);
				if (material == null) {
					BuddiesPlugin.getInstance().getEngine().getLogger().severe("Buddies Material field '" + field.getName() + "' is not yet initialized");
					continue;
				}
				if (material instanceof InitializableMaterial) {
					InitializableMaterial initializableMaterial = (InitializableMaterial) material;
					initializableMaterial.initialize();
					if (material instanceof BuddiesBlockMaterial) {
						for (Material subMaterial : ((BuddiesBlockMaterial) material).getSubMaterials()) {
							((InitializableMaterial) subMaterial).initialize();
						}
					}
				}
			} catch (Throwable t) {
				BuddiesPlugin.getInstance().getEngine().getLogger().severe("An exception occurred while reading Buddies Material field '" + field.getName() + "':");
				t.printStackTrace();
			}
		}
		initialized = true;
	}

}
