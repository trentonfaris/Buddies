package me.man_cub.buddies.world.generator.maps;

import me.man_cub.buddies.world.generator.BuddiesGenerator;

import org.spout.api.generator.EmptyWorldGenerator;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;

public class LobbyGenerator extends EmptyWorldGenerator implements BuddiesGenerator {

	@Override
	public Point getSafeSpawn(World world) {
		return new Point(world, 0f, 0f, 0f);
	}
	
	@Override
	public String getName() {
		return "lobby";
	}

}
