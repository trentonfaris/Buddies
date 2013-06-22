package me.Man_cub.Buddies.world.lighting;

import me.Man_cub.Buddies.BuddiesPlugin;

import org.spout.api.geo.cuboid.Block;

public class BuddiesLighting {
	public static BuddiesSkylightLightingManager SKY_LIGHT = new BuddiesSkylightLightingManager("skylight");
	public static BuddiesBlocklightLightingManager BLOCK_LIGHT = new BuddiesBlocklightLightingManager("block");
	
	public static void initialize() {
		boolean initialized = false;
		if (initialized) {
			BuddiesPlugin.getInstance().getEngine().getLogger().info("Buddies lighting initialized more than once.");
		}
	}
	
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
