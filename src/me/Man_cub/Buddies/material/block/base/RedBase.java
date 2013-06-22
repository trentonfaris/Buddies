package me.Man_cub.Buddies.material.block.base;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.material.block.Solid;

public class RedBase extends Solid {
	
	public RedBase(String name, int id) {
		super(name, id, BuddiesMaterialModels.RED_BASE);
	}
	
	@Override
	public byte getLightLevel(short data) {
		return 14;
	}
}
