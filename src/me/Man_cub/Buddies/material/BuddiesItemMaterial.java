package me.man_cub.buddies.material;

import me.man_cub.buddies.BuddiesPlugin;
import me.man_cub.buddies.render.BuddiesEffects;

import org.spout.api.Platform;
import org.spout.api.material.Material;

public class BuddiesItemMaterial extends Material implements BuddiesMaterial {
	private int meleeDamage = 1;
	private final int buddiesId;
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();
	
	public BuddiesItemMaterial(String name, int id, String model) {
		super((short) 0, name, model);
		this.buddiesId = id;
		if (plugin.getEngine().getPlatform() == Platform.CLIENT) {
			if (!getModel().getRenderMaterial().getRenderEffects().contains(BuddiesEffects.SKY_TIME)) {
				getModel().getRenderMaterial().addRenderEffect(BuddiesEffects.SKY_TIME);
				getModel().getRenderMaterial().addBufferEffect(BuddiesEffects.LIGHTING);
			}
		}
	}
	
	@Override 
	public final int getBuddiesId() {
		return buddiesId;
	}
	
	@Override
	public int getDamage() {
		return this.meleeDamage;
	}
	
	@Override
	public BuddiesItemMaterial setDamage(int damage) {
		this.meleeDamage = damage;
		return this;
	}

}
