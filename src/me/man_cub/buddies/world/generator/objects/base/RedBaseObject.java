package me.man_cub.buddies.world.generator.objects.base;

import me.man_cub.buddies.material.BuddiesMaterials;

import org.spout.api.generator.WorldGeneratorObject;
import org.spout.api.geo.World;
import org.spout.api.material.BlockMaterial;

public class RedBaseObject extends WorldGeneratorObject {
	private BlockMaterial red = BuddiesMaterials.RED_BASE;

	@Override
	public boolean canPlaceObject(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void placeObject(World w, int x, int y, int z) {
		w.setBlockMaterial(x, y, z, red, (short) 0, null);
	}

}
