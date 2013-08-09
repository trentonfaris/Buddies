package me.man_cub.buddies.render;

import org.spout.api.math.TrigMath;
import org.spout.api.math.Vector3;
import org.spout.api.math.Vector4;
import org.spout.api.render.effect.RenderEffect;
import org.spout.api.render.effect.SnapshotRender;

public class LightRenderEffect implements RenderEffect {
	private static final float size = 256f;
	private static final float lat = (float) ((25.0 / 180.0) * TrigMath.PI);
	private static final float sunSize = 0.2f;
	private static final float ambient = 0.33f;
	private static final Vector4 moonColor = new Vector4(0.33f, 0.33f, 0.50f, 1.0f);
	private static final Vector4 sunColor = new Vector4(1f, 1f, 1f, 1.0f);
	private static final Vector4 dawnColor = new Vector4(1f, 0.5f, 0.5f, 1.0f);
	private static final float cY = (float) Math.cos(lat);
	private static final float cZ = (float) Math.sin(lat);
	private static volatile boolean force = false;
	private static volatile float xForce = 0;
	private static volatile float yForce = 0;
	private static volatile float zForce = 0;

	public static void setSun(Vector3 pos) {
		if (pos == null) {
			force = false;
		} else {
			xForce = pos.getX();
			yForce = pos.getY();
			zForce = pos.getZ();
			force = true;
		}
	}

	@Override
	public void preRender(SnapshotRender snapshotRender) {
		//TODO : Replace by the real color of the sky taking account of the time
		float f = (float) ((System.currentTimeMillis() % 15000) / 15000.0);

		float rads = (float) (f * 2 * TrigMath.PI);

		float x = (float) Math.sin(rads);

		float y1 = (float) Math.cos(rads);

		float y = y1 * cY;

		float z = y1 * cZ;

		if (force) {
			x = xForce;
			y = yForce;
			z = zForce;
		}

		float sunWeight;
		Vector4 skyColor;

		float yAbs = Math.abs(y);
		if (yAbs < sunSize) {
			sunWeight = (y + sunSize) / sunSize / 2.0f;
			Vector4 weightedSun;
			if (y < 0) {
				weightedSun = dawnColor;
			} else {
				float dawnWeight = y / sunSize;
				weightedSun = sunColor.multiply(dawnWeight).add(dawnColor.multiply(1 - dawnWeight));
			}
			skyColor = weightedSun.multiply(sunWeight).add(moonColor.multiply((1 - sunWeight)));
		} else {
			if (y < 0) {
				sunWeight = 0;
				skyColor = moonColor;
			} else {
				sunWeight = 1;
				skyColor = sunColor;
			}
		}

		snapshotRender.getMaterial().getShader().setUniform("ambient", ambient);
		snapshotRender.getMaterial().getShader().setUniform("skyColor", skyColor);
		snapshotRender.getMaterial().getShader().setUniform("sunColor", sunColor.multiply(sunWeight));
		snapshotRender.getMaterial().getShader().setUniform("moonColor", moonColor.multiply(1 - sunWeight));

		Vector4 sunDir = new Vector4(x * size, y * size, z * size, 1.0f);

		//Spout.getLogger().info("f = " + f + " rads = " + rads + " vector " + sunDir);

		snapshotRender.getMaterial().getShader().setUniform("sunDir", sunDir);
	}

	@Override
	public void postRender(SnapshotRender snapshotRender) {
	}
}
