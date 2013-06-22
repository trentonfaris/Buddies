package me.Man_cub.Buddies.material.block.base;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.material.block.Solid;

public class GreenBase extends Solid {
	
	public GreenBase(String name, int id) {
		super(name, id, BuddiesMaterialModels.GREEN_BASE);
	}
	
	@Override
	public byte getLightLevel(short data) {
		return 14;
	}
}
