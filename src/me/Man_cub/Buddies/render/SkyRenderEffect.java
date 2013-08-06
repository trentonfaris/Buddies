package me.Man_cub.Buddies.render;

import org.spout.api.math.TrigMath;
import org.spout.api.math.Vector3;
import org.spout.api.math.Vector4;
import org.spout.api.render.effect.RenderEffect;
import org.spout.api.render.effect.SnapshotRender;
import org.spout.api.render.shader.Shader;

public class SkyRenderEffect implements RenderEffect {
	private static final float lat = (float) (25.0 * TrigMath.DEGTORAD);
	private static final float sunSize = 0.2f;
	private static final Vector4 nightColor = new Vector4(1.0f, 1.0f, 1.0f, 0f);
	private static final Vector4 dayColor = new Vector4(135 / 255f, 206 / 255f, 235 / 255f, 1.0f);
	private static final Vector4 dawnColor = new Vector4(1f, 0.5f, 0.5f, 0.7f);
	private static final float cY = (float) Math.cos(lat);
	private static volatile boolean force = false;
	private static volatile float yForce = 0;

	public static void setSun(Vector3 pos) {
		if (pos == null) {
			force = false;
		} else {
			yForce = pos.getY();
			force = true;
		}
	}

	@Override
	public void preRender(SnapshotRender snapshotRender) {

		Shader s = snapshotRender.getMaterial().getShader();

		float time = (float) ((System.currentTimeMillis() % 15000) / 15000.0);

		float rads = (float) (time * TrigMath.TWO_PI);

		float y1 = (float) Math.cos(rads);

		float y = (float) (y1 * cY);

		if (force) {
			y = yForce;
		}

		s.setUniform("suny", y);
		s.setUniform("sunSize", sunSize);
		s.setUniform("dawnColor", dawnColor);
		s.setUniform("dayColor", dayColor);
		s.setUniform("nightColor", nightColor);
	}

	@Override
	public void postRender(SnapshotRender snapshotRender) {
		// TODO Auto-generated method stub

	}
}
