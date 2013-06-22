package me.Man_cub.Buddies.material.block.natural;

import java.util.Random;

import me.Man_cub.Buddies.data.resources.BuddiesMaterialModels;
import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.material.block.GroundAttachable;
import me.Man_cub.Buddies.world.lighting.BuddiesLighting;

import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.BlockMaterial;
import org.spout.api.material.DynamicMaterial;
import org.spout.api.material.block.BlockFace;
import org.spout.api.material.range.EffectRange;

public class Snow extends GroundAttachable implements DynamicMaterial {
	private static final long POLL_TIME = 60000;
	private static final byte MIN_MELT_LIGHT = 11;

	public Snow(String name, int id) {
		super(name, id, BuddiesMaterialModels.SNOW);
		setTransparent();
		setOpacity(1);
	}

	@Override
	public void onUpdate(BlockMaterial oldMaterial, Block block) {
		if (block.translate(BlockFace.BOTTOM).isMaterial(BuddiesMaterials.AIR)) {
			block.setMaterial(BuddiesMaterials.AIR);
		}
	}

	public final boolean willMeltAt(Block block) {
		return BuddiesLighting.getBlockLight(block) > MIN_MELT_LIGHT;
	}

	@Override
	public EffectRange getDynamicRange() {
		return EffectRange.THIS;
	}

	@Override
	public void onFirstUpdate(Block b, long currentTime) {
		//TODO : Delay before next check ?
		b.dynamicUpdate(60000 + currentTime, true);
	}

	@Override
	public void onDynamicUpdate(Block block, long updateTime, int data) {
		if (willMeltAt(block)) {
			short dataBlock = block.getData();
			if (dataBlock > 0) {
				block.setData(dataBlock - 1);
			} else {
				block.setMaterial(BuddiesMaterials.AIR);
			}
		} else { // not warm enough to melt the snow and last poll was a long time ago, might as well skip repeated polls
			long age = block.getWorld().getAge();
			if (age - updateTime > POLL_TIME) {
				block.dynamicUpdate(age + new Random().nextInt((int) POLL_TIME), true);
				return;
			}
		}
		block.dynamicUpdate(updateTime + POLL_TIME, true);
		//TODO : Delay before next check ?
	}
}
