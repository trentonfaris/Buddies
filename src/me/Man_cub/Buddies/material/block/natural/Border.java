package me.Man_cub.Buddies.material.block.natural;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.material.block.Solid;

public class Border extends Solid {
	
	public Border(String name, int id) {
		super(name, id, BuddiesMaterialModels.BORDER);
		this.setTransparent();
	}

}
