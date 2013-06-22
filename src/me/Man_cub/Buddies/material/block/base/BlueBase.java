package me.Man_cub.Buddies.material.block.base;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.material.block.Solid;

public class BlueBase extends Solid {
	
	public BlueBase(String name, int id) {
		super(name, id, BuddiesMaterialModels.BLUE_BASE);
	}
	
	@Override
	public byte getLightLevel(short data) {
		return 14;
	}
}