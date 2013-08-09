package me.man_cub.buddies.component.world.misc;

import me.man_cub.buddies.component.world.BuddiesWorldComponent;
import me.man_cub.buddies.material.BuddiesMaterials;
import me.man_cub.buddies.world.generator.BuddiesGenerator;
import me.man_cub.buddies.world.generator.biome.BuddiesBiomeGenerator;

public class Border extends BuddiesWorldComponent {
	public static int height = 100;
	
	@Override
	public void onAttached() {		
		int length = getLength();
		for (int x = 0; x < length; x++) {
			for (int z = 0; z < length; z++) {
				for (int y = 0; y < height; y++) {
					if ((x == 0 && y >= 3 && y <= height && z >= 0 && z < length) || (x == 127 && y >= 3 && y <= height && z >= 0 && z < length) || (x >= 0 && x < length && y >= 3 && y <= height && z == 0) || (x >= 0 && x < length && y >= 3 && y <= height && z == 127) || (y == height)) {
						getOwner().setBlockMaterial(x, y, z, BuddiesMaterials.BORDER, (short) 0, null);
					}
				}
			}
		}
	}
	
	public int getLength() {
		if (!(getOwner().getGenerator() instanceof BuddiesGenerator)) {
			return 0;
		}
		
		BuddiesGenerator generator = (BuddiesGenerator) getOwner().getGenerator();
		if (!(generator instanceof BuddiesBiomeGenerator)) {
			return 0;
		}
		
		BuddiesBiomeGenerator biomeGenerator = (BuddiesBiomeGenerator) generator;
		return biomeGenerator.getLength();
	}
	
	public int getHeight() {
		return height;
	}

}
