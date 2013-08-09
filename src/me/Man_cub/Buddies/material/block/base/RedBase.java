package me.man_cub.buddies.material.block.base;

import me.man_cub.buddies.data.resources.BuddiesMaterialModels;
import me.man_cub.buddies.material.block.Solid;

public class RedBase extends Solid {
	
	public RedBase(String name, int id) {
		super(name, id, BuddiesMaterialModels.RED_BASE);
	}
	
	@Override
	public byte getLightLevel(short data) {
		return 14;
	}
}
