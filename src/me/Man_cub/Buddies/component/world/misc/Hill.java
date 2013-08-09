package me.man_cub.buddies.component.world.misc;

import me.man_cub.buddies.component.world.BuddiesWorldComponent;
import me.man_cub.buddies.material.BuddiesMaterials;
import me.man_cub.buddies.world.generator.BuddiesGenerator;
import me.man_cub.buddies.world.generator.biome.BuddiesBiomeGenerator;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

public class Hill extends BuddiesWorldComponent {
	public static int baseRadius = 48;
	private static int peakRadius;
	private static int variance = ((baseRadius / 3) * (baseRadius / 3)) * 2;
	public static int height = 23;
	
	@Override
	public void onAttached() {
		int length = getLength();
		
		int center = (length / 2) - 1;
		
		double[][] covariance = {{variance, 0}, {0, variance}};
		double[] mean = {new Integer(center).doubleValue(), new Integer(center).doubleValue()};
		
		for (int xx = center - baseRadius; xx <= center + baseRadius; xx++) {
			for (int zz = center - baseRadius; zz <= center +  baseRadius; zz++) {
				int a = xx - center;
				int b = zz - center;
				if (a * a + b * b <= baseRadius * baseRadius) {
					
					double[] density = {new Integer(xx).doubleValue(), new Integer(zz).doubleValue()};
					MultivariateNormalDistribution distribution = new MultivariateNormalDistribution(mean, covariance);
					int y = safeLongToInt(Math.round(100000 * distribution.density(density)));
					
					if (y < height) {
						getOwner().setBlockMaterial(xx, y, zz, BuddiesMaterials.GRASS, (short) 0, null);	// Set the "hill" of the mountain.
					} else if (y == height) {
						getOwner().setBlockMaterial(xx, y, zz, BuddiesMaterials.GRASS, (short) 0, null);
						if (xx == center) {
							peakRadius = Math.abs(center - zz);			// Get radius of top
						} else if (zz == center) {
							peakRadius = Math.abs(center - xx);
						}
					}
				}
			}
		}
		for (int xx = center - peakRadius; xx <= center + peakRadius; xx++) {				// Cover top
			for (int zz = center - peakRadius; zz <= center + peakRadius; zz++) {
				int a = xx - center;
				int b = zz - center;
				if (a * a + b * b <= peakRadius * peakRadius) {
					getOwner().setBlockMaterial(xx, height, zz, BuddiesMaterials.GRASS, (short) 0, null);
				}
			}
		}
	}
	
	public static int safeLongToInt(long l) {
	    int i = (int)l;
	    if ((long)i != l) {
	        throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
	    }
	    return i;
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
	
	public int getBaseRadius() {
		return baseRadius;
	}
	
	public int getPeakRadius() {
		return peakRadius;
	}
	
	public int getHeight() {
		return height;
	}
	
}
