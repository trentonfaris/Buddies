package me.Man_cub.Buddies.world;

import java.util.Random;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.material.BuddiesBlockMaterial;
import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.material.block.natural.Snow;

import org.spout.api.component.Component;
import org.spout.api.entity.Player;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.cuboid.Region;
import org.spout.api.geo.discrete.Point;
import org.spout.api.material.BlockMaterial;
import org.spout.api.material.block.BlockFace;
import org.spout.api.math.GenericMath;
import org.spout.api.math.Vector3;
import org.spout.api.scheduler.TaskPriority;

public class SnowSimulator extends Component {
	final WeatherSimulator weather;
	private int count = 0;

	public SnowSimulator(WeatherSimulator weather) {
		this.weather = weather;
	}

	public World getWorld() {
		return this.weather.getWorld();
	}

	@Override
	public boolean canTick() {
		return true;
	}

	@Override
	public void onTick(float dt) {
		count++;
		if (count >= JUMP_TABLE.length) {
			count = 0;
		}
		for (Player player : getWorld().getPlayers()) {
			if (!player.isOnline()) {
				continue;
			}
			Region r = player.getRegion();
			r.getTaskManager().scheduleSyncDelayedTask(BuddiesPlugin.getInstance(), new SnowfallTask(player, r, JUMP_TABLE[count]), TaskPriority.LOWEST);
		}
	}

	private static final Vector3[] JUMP_TABLE = new Vector3[3];

	static {
		for (int i = 0; i < JUMP_TABLE.length; i++) {
			JUMP_TABLE[i] = countToOffset(i);
		}
	}

	private static Vector3 countToOffset(int count) {
		switch (count) {
			case 0:
				return Vector3.ZERO;
			case 1:
				return new Vector3(0, 16, 0);
			case 2:
				return new Vector3(0, -16, 0);
			default:
				return Vector3.ZERO;
		}
	}

	private class SnowfallTask implements Runnable {
		private final Player player;
		private final Region region;
		private final Vector3 offset;

		private SnowfallTask(Player player, Region region, Vector3 offset) {
			this.player = player;
			this.region = region;
			this.offset = offset;
		}

		@Override
		public void run() {
			if (!player.isOnline() || !weather.isRaining()) {
				return;
			}
			Random rand = GenericMath.getRandom();
			Point playerPos = player.getPhysics().getPosition();
			final int posX = GenericMath.floor(playerPos.getX());
			final int posY = GenericMath.floor(playerPos.getY());
			final int posZ = GenericMath.floor(playerPos.getZ());
			for (int tries = 0; tries < 10; tries++) {
				//pick a random chunk between -4, -4, to 4, 4 relative to the player's position
				int cx = (rand.nextBoolean() ? -1 : 1) * rand.nextInt(5);
				int cz = (rand.nextBoolean() ? -1 : 1) * rand.nextInt(5);

				//pick random coords to try at inside the chunk (0, 0) to (15, 15)
				int rx = rand.nextInt(16);
				int rz = rand.nextInt(16);

				//pick a offset from the player's y position (-15 - +15) of their position
				int offsetY = (rand.nextBoolean() ? -1 : 1) * rand.nextInt(15);

				int x = posX + cx * 16 + rx + offset.getFloorX();
				int y = posY + offsetY + offset.getFloorY();
				int z = posZ + cz * 16 + rz + offset.getFloorZ();
				if (region.containsBlock(x, y, z)) {
					if (weather.isSnowingAt(x, y, z)) {
						//Try to find the surface
						for (int dy = 1; dy < 16; dy++) {
							if (region.containsBlock(x, y - dy, z)) {
								Block block = region.getBlock(x, y - dy, z);
								BlockMaterial mat = block.getMaterial();
								if (mat instanceof BuddiesBlockMaterial) {
									BuddiesBlockMaterial bbm = (BuddiesBlockMaterial) mat;
									//Place snow ontop of solid
									if (bbm.canSupport(BuddiesMaterials.SNOW, BlockFace.TOP)) {
										Block above = block.translate(BlockFace.TOP);
										if (!BuddiesMaterials.SNOW.willMeltAt(above)) {
											above.setMaterial(BuddiesMaterials.SNOW);
										}
										return;
									//Try to grow snow
									} else if (bbm instanceof Snow) {
										short data = block.getBlockData();
										if (data == 0x7) {
											Block above = block.translate(BlockFace.TOP);
											if (above.getMaterial() == BlockMaterial.AIR) {
												above.setMaterial(BuddiesMaterials.SNOW);
											}
										} else {
											block.setData(data + 1);
										}
										return;
									} else {
										break;
									}
								}
							} else {
								break;
							}
						}
					}
				}
			}
		}
	}
}
