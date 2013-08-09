package me.man_cub.buddies.material.block.base;

import me.man_cub.buddies.data.resources.BuddiesMaterialModels;
import me.man_cub.buddies.material.block.Solid;

public class YellowBase extends Solid {
	
	public YellowBase(String name, int id) {
		super(name, id, BuddiesMaterialModels.YELLOW_BASE);
	}
	
	@Override
	public byte getLightLevel(short data) {
		return 14;
	}
}
