package me.man_cub.buddies.render;

import gnu.trove.list.array.TFloatArrayList;

import me.man_cub.buddies.world.lighting.BuddiesCuboidLightBuffer;
import me.man_cub.buddies.world.lighting.BuddiesLighting;

import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.geo.cuboid.ChunkSnapshot;
import org.spout.api.geo.cuboid.ChunkSnapshotModel;
import org.spout.api.material.BlockMaterial;
import org.spout.api.render.BufferContainer;
import org.spout.api.render.effect.BufferEffect;

public class LightBufferEffect implements BufferEffect {

	@Override
	public void post(ChunkSnapshotModel chunkModel, BufferContainer container) {
		TFloatArrayList vertexBuffer = (TFloatArrayList) container.getBuffers().get(0);
	
		/*
		 * Use a shader light (2) and skylight (4)
		 * 
		 * WE NEED TO USE 2 BECAUSE WE DON'T USE COLOR
		 * OPENGL 2 NEED TO USE LAYOUT IN THE ORDER
		 * WE CAN'T USE 3 IF 2 ISN'T USED
		 * 
		 * One float per vertice
		 * file://Vanilla/shaders/terrain.120.vert 
		 * file://Vanilla/shaders/terrain.330.vert
		 */

		TFloatArrayList lightBuffer = (TFloatArrayList) container.getBuffers().get(1);
		TFloatArrayList skylightBuffer = (TFloatArrayList) container.getBuffers().get(4);

		if (lightBuffer == null) {
			lightBuffer = new TFloatArrayList(vertexBuffer.size() / 4);
			container.setBuffers(1, lightBuffer);
		}

		if (skylightBuffer == null) {
			skylightBuffer = new TFloatArrayList(vertexBuffer.size() / 4);
			container.setBuffers(4, skylightBuffer);
		}

		for (int i = 0; i < vertexBuffer.size(); ) {
			float x = vertexBuffer.get(i++);
			float y = vertexBuffer.get(i++);
			float z = vertexBuffer.get(i++);
			i++; // w component

			//TODO : Create a buffer for each light registred by plugin

			generateLightOnVertices(chunkModel, x, y, z, lightBuffer, skylightBuffer);
		}
	}
	
	/**
	 * Compute the light for one vertex
	 * @param chunkModel
	 * @param x
	 * @param y
	 * @param z
	 * @param lightBuffer
	 */
	private void generateLightOnVertices(ChunkSnapshotModel chunkModel, float x, float y, float z, TFloatArrayList lightBuffer, TFloatArrayList skylightBuffer) {
		int xi = (int) x;
		int yi = (int) y;
		int zi = (int) z;
		if (chunkModel != null) {
			float light = 0;
			float skylight = 0;
			int count = 0;

			int xs = (x == xi) ? (xi - 1) : xi;
			int ys = (y == yi) ? (yi - 1) : yi;
			int zs = (z == zi) ? (zi - 1) : zi;

			for (int xx = xs; xx <= xi; xx++) {
				for (int yy = ys; yy <= yi; yy++) {
					int zOld = 0;
					ChunkSnapshot chunk = null;
					BuddiesCuboidLightBuffer blockLight = null;
					BuddiesCuboidLightBuffer skyLight = null;
					for (int zz = zs; zz <= zi; zz++) {
						int zChunk = zz >> Chunk.BLOCKS.BITS;
						if (zChunk != zOld || chunk == null) {
							chunk = chunkModel.getChunkFromBlock(xx, yy, zz);
							blockLight = (BuddiesCuboidLightBuffer) chunk.getLightBuffer(BuddiesLighting.BLOCK_LIGHT.getId());
							skyLight = (BuddiesCuboidLightBuffer) chunk.getLightBuffer(BuddiesLighting.SKY_LIGHT.getId());
							zOld = zChunk;
						}
						BlockMaterial m = chunk.getBlockMaterial(xx, yy, zz);
						if (!m.isOpaque()) {
							light += blockLight.get(xx, yy, zz);
							skylight += skyLight.get(xx, yy, zz); //use the SkyLightRaw, the real sky state would be apply by the shader
							count++;
						}
					}
				}
			}

			if (count == 0) {
				count++;
			}

			light /= count;
			skylight /= count;
			light /= 16;
			skylight /= 16;

			//TODO : To replace by 2 byte buffer for Vanilla

			lightBuffer.add(light);
			skylightBuffer.add(skylight);
		} else {
			lightBuffer.add(1f);
			skylightBuffer.add(1f);
		}
	}
}
