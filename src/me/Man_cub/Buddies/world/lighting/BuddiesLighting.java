package me.Man_cub.Buddies.world.lighting;

import org.spout.api.Spout;
import org.spout.api.geo.cuboid.Block;

public class BuddiesLighting {
	public static final BuddiesSkylightLightingManager SKY_LIGHT = new BuddiesSkylightLightingManager("skylight");
	public static final BuddiesBlocklightLightingManager BLOCK_LIGHT = new BuddiesBlocklightLightingManager("block");

	public static void initialize() {
		boolean initialized = false;
		if (initialized) {
			Spout.getLogger().info("Vanilla lighting initialized more than once");
		}
	}

	// TODO - add these to World/Region/Chunk/Block
	public static byte getLight(Block b) {
		return (byte) Math.max(getBlockLight(b), getSkyLight(b));
	}

	public static byte getBlockLight(Block b) {
		return getLight(b, BLOCK_LIGHT);
	}

	public static byte getSkyLight(Block b) {
		return getLight(b, SKY_LIGHT);
	}

	public static byte getLight(Block b, BuddiesLightingManager manager) {
		BuddiesCuboidLightBuffer light = b.getChunk().getLightBuffer(manager);
		return light.get(b.getX(), b.getY(), b.getZ());
	}
}
