package me.man_cub.buddies.world.generator;

import org.spout.api.generator.WorldGenerator;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;

public interface BuddiesGenerator extends WorldGenerator {

	public abstract Point getSafeSpawn(World world);
	
}
