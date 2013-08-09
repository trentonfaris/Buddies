package me.man_cub.buddies.component.world.misc;

import me.man_cub.buddies.component.world.BuddiesWorldComponent;
import me.man_cub.buddies.world.generator.BuddiesGenerator;
import me.man_cub.buddies.world.generator.biome.BuddiesBiomeGenerator;
import me.man_cub.buddies.world.generator.objects.pad.BluePadObject;
import me.man_cub.buddies.world.generator.objects.pad.GreenPadObject;
import me.man_cub.buddies.world.generator.objects.pad.RedPadObject;
import me.man_cub.buddies.world.generator.objects.pad.YellowPadObject;

public class Base extends BuddiesWorldComponent {

	@Override
	public void onAttached() {
		int length = getLength();
		
		//BlueBaseObject blueBase = new BlueBaseObject();
		BluePadObject bluePad = new BluePadObject();
		//GreenBaseObject greenBase = new GreenBaseObject();
		GreenPadObject greenPad = new GreenPadObject();
		//RedBaseObject redBase = new RedBaseObject();
		RedPadObject redPad = new RedPadObject();
		//YellowBaseObject yellowBase = new YellowBaseObject();
		YellowPadObject yellowPad = new YellowPadObject();
		
		//blueBase.placeObject(getOwner(), length - 110, 10, length - 110);
		bluePad.placeObject(getOwner(), length - 120, 2, length - 120);
		//greenBase.placeObject(getOwner(), 54, 5, 54);
		greenPad.placeObject(getOwner(), length - 8, 2, length - 120);
		//redBase.placeObject(getOwner(), 54, 5, 54);
		redPad.placeObject(getOwner(), length - 120, 2, length - 8);
		//yellowBase.placeObject(getOwner(), 54, 5, 54);
		yellowPad.placeObject(getOwner(), length - 8, 2, length - 8);
		
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
}
