package me.Man_cub.Buddies.world.generator.objects.pad;

import java.util.HashSet;
import java.util.Set;

import me.Man_cub.Buddies.material.BuddiesMaterials;

import org.spout.api.generator.WorldGeneratorObject;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.BlockMaterial;
import org.spout.api.material.block.BlockFace;

public class RedPadObject extends WorldGeneratorObject {
	//Main materials
	private BlockMaterial center = BuddiesMaterials.RED_PAD_CENTER;
	private BlockMaterial edge = BuddiesMaterials.RED_PAD_EDGE;
	private BlockMaterial vertex = BuddiesMaterials.RED_PAD_VERTEX;
	//Materials we can place it on
	private BlockMaterial placeableOn = BuddiesMaterials.GRASS;
	//Materials we can override
	private final Set<BlockMaterial> overridable = new HashSet<BlockMaterial>();
	
	public RedPadObject() {
		overridable.add(BuddiesMaterials.GRASS);
		overridable.add(BuddiesMaterials.DIRT);
		overridable.add(BuddiesMaterials.STONE);
	}

	@Override
	public boolean canPlaceObject(World w, int x, int y, int z) {
		if (w.getBlockMaterial(x, y, z) != placeableOn) {
			return false;
		}
		for (int xx = x - 3; xx < x + 2; xx++) {
			for (int zz = z - 3; zz < z + 2; zz++) {
				for (int yy = y + 1; yy < y + 4; yy++) {
					if (w.getBlockMaterial(xx, yy, zz) != BuddiesMaterials.AIR) {
						return false;
					}
					final Block block = w.getBlock(xx, y, zz);
					if (!overridable.contains(block.getMaterial()) || !overridable.contains(block.translate(BlockFace.BOTTOM).getMaterial())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void placeObject(World w, int x, int y, int z) {
		for (int xx = x - 2; xx < x + 2; xx++) {
			for (int zz = z - 2; zz < z + 2; zz++) {
				if (zz == z - 2 && (xx == x || xx == x - 1)) {
					w.setBlockMaterial(xx, y, zz, edge, (short) 0, null);
				} else if (xx == x - 2 && (zz == z || zz == z - 1)) {
					w.setBlockMaterial(xx, y, zz, edge, (short) 0, null);
					//Need to rotate this to face east (when looking from above)
				} else if (zz == z + 1 && (xx == x || xx == x - 1)) {
					w.setBlockMaterial(xx, y, zz, edge, (short) 0, null);
					//Need to rotate this to face south (when looking from above)
				} else if (xx == x + 1 && (zz == z || zz == z - 1)) {
					w.setBlockMaterial(xx, y, zz, edge, (short) 0, null);
					//Need to rotate this to face west (when looking from above)
				} else if (xx == x - 2 && zz == z - 2) {
					w.setBlockMaterial(xx, y, zz, vertex, (short) 0, null);
				} else if (xx == x - 2 && zz == z + 1) {
					w.setBlockMaterial(xx, y, zz, vertex, (short) 0, null);
					//Need to rotate this block 90 degrees (when looking from above)
				} else if (xx == x + 1 && zz == z + 1) {
					w.setBlockMaterial(xx, y, zz, vertex, (short) 0, null);
					//Need to rotate this block 180 degrees (when looking from above)
				} else if (xx == x + 1 && zz == z - 2) {
					w.setBlockMaterial(xx, y, zz, vertex, (short) 0, null);
					//Need to rotate this block 270 degrees or -90 degrees (when looking from above)
				} else if (xx == x && zz == z) {
					w.setBlockMaterial(xx, y, zz, center, (short) 0, null);
				} else if (xx == x && zz == z - 1) {
					w.setBlockMaterial(xx, y, zz, center, (short) 0, null);
					//Need to rotate this block 90 degrees (when looking from above)
				} else if (xx == x - 1 && zz == z - 1) {
					w.setBlockMaterial(xx, y, zz, center, (short) 0, null);
				} else if (xx == x - 1 && zz == z) {
					w.setBlockMaterial(xx, y, zz, center, (short) 0, null);
					//Need to rotate this block 90 degrees (when looking from above)
				}
			}
		}
	}
	
	public void setCenter(BlockMaterial center) {
		this.center = center;
	}
	
	public void setEdge(BlockMaterial edge) {
		this.edge = edge;
	}
	
	public void setVertex(BlockMaterial vertex) {
		this.vertex = vertex;
	}
	
	public void setPlaceableOn(BlockMaterial placeableOn) {
		this.placeableOn = placeableOn;
	}
	
	public Set<BlockMaterial> getOverridableMaterials() {
		return overridable;
	}

}
