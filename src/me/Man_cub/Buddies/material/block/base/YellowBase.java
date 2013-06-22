package me.Man_cub.Buddies.material.block.base;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.material.block.Solid;

public class YellowBase extends Solid {
	
	public YellowBase(String name, int id) {
		super(name, id, BuddiesMaterialModels.YELLOW_BASE);
	}
	
	@Override
	public byte getLightLevel(short data) {
		return 14;
	}
}
