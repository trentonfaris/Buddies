package me.Man_cub.Buddies.world.generator.objects.base;

import me.Man_cub.Buddies.material.BuddiesMaterials;

import org.spout.api.generator.WorldGeneratorObject;
import org.spout.api.geo.World;
import org.spout.api.material.BlockMaterial;

public class BlueBaseObject extends WorldGeneratorObject {
	private BlockMaterial blue = BuddiesMaterials.BLUE_BASE;

	@Override
	public boolean canPlaceObject(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void placeObject(World w, int x, int y, int z) {
		w.setBlockMaterial(x, y, z, blue, (short) 0, null);
	}

}
