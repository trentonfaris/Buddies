package me.Man_cub.Buddies.data;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public enum Animation {
	NONE(0),
	SWING_ARM(1),
	DAMAGE_ANIMATION(2),
	UNKNOWN_ANIMATION(102),
	CROUCH(104),
	UNCROUCH(105);
	private final int id;
	private static final TIntObjectMap<Animation> idMap = new TIntObjectHashMap<Animation>();
	
	private Animation(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	static {
		for (Animation animation : Animation.values()) {
			idMap.put(animation.getId(), animation);
		}
	}
	
	public static Animation get(int id) {
		return idMap.get(id);
	}

}
