package me.Man_cub.Buddies.render;

import org.spout.api.render.effect.RenderEffect;

public class BuddiesEffects {
	public static final LightBufferEffect LIGHTING = new LightBufferEffect();
	public static final BiomeGrassColorBufferEffect BIOME_GRASS_COLOR = new BiomeGrassColorBufferEffect();
	public static final BiomeWaterColorBufferEffect BIOME_WATER_COLOR = new BiomeWaterColorBufferEffect();
	public static final RenderEffect SKY_TIME = new LightRenderEffect();
	public static final RenderEffect SKY = new SkyRenderEffect();
}
