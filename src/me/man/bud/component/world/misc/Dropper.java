package me.man_cub.buddies.component.world.misc;

import java.util.Random;

import me.man_cub.buddies.component.world.BuddiesWorldComponent;
import me.man_cub.buddies.data.BuddiesData;
import me.man_cub.buddies.material.BuddiesMaterials;
import me.man_cub.buddies.world.generator.BuddiesGenerator;
import me.man_cub.buddies.world.generator.biome.BuddiesBiomeGenerator;

public class Dropper extends BuddiesWorldComponent {
	public static int timeChangeCheck;
	public static int dropHeight;
	public static int squareRadius;
	
	@Override
	public void onAttached() {
		int peakDiameter = getOwner().get(Hill.class).getPeakRadius() * 2;
		squareRadius = (int) Math.floor(Math.sqrt(((peakDiameter * peakDiameter) / 2)) / 2);
		dropHeight = getOwner().get(Border.class).getHeight();
	}
	
	@Override
	public void onTick(float dt) {
		int worldTime = getOwner().get(Timer.class).getTime();
		if (worldTime % 5 != 0) {
			return;
		}
		if (timeChangeCheck == worldTime) {
			return;
		}
		if (getTotal() >= 50) {
			return;
		}
		timeChangeCheck = worldTime;
			
		Random random = new Random();
		int amountToDrop = random.nextInt(3) + 1;
		setTotal(getTotal() + amountToDrop);
			
		int center = getLength() / 2 - 1;
			
		for (int x = 0; x < amountToDrop; x++) {
			int xx = random.nextInt(squareRadius * 2) - squareRadius;
			int zz = random.nextInt(squareRadius * 2) - squareRadius;
			if (isRareDrop()) {
				getOwner().setBlockMaterial(center + xx, dropHeight, center + zz, BuddiesMaterials.MEGACRATE, (short) 0, null);
			} else {
				getOwner().setBlockMaterial(center + xx, dropHeight, center + zz, BuddiesMaterials.CRATE, (short) 0, null);
			}
		}
	}
	
	public void setTotal(int newTotal) {
		getData().put(BuddiesData.CRATES, newTotal);
	}
	
	public int getTotal() {
		return getData().get(BuddiesData.CRATES);
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
	
	private boolean isRareDrop() {
		Random random = new Random();
		int rareDrop = random.nextInt(20) + 1;
		if (rareDrop == 1) {
			return true;
		}
		return false;
	}

}
