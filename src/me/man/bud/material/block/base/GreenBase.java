package me.man_cub.buddies.material.block.base;

import me.man_cub.buddies.data.resources.BuddiesMaterialModels;
import me.man_cub.buddies.material.block.Solid;

public class GreenBase extends Solid {
	
	public GreenBase(String name, int id) {
		super(name, id, BuddiesMaterialModels.GREEN_BASE);
	}
	
	@Override
	public byte getLightLevel(short data) {
		return 14;
	}
}
