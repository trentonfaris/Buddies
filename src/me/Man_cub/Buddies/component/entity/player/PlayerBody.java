package me.Man_cub.Buddies.component.entity.player;

import me.Man_cub.Buddies.component.entity.misc.EntityBody;

import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.Matrix;
import org.spout.api.math.MatrixMath;
import org.spout.api.math.Vector3;
import org.spout.api.render.Camera;
import org.spout.api.render.ViewFrustum;

public class PlayerBody extends EntityBody implements Camera {
	private Matrix projection;
	private Matrix view;
	private ViewFrustum frustum = new ViewFrustum();
	private float fieldOfView = 75f;

	public void setScale(float scale) { //1/2
		projection = MatrixMath.createPerspective(fieldOfView * scale, 4.0f / 3.0f, .001f * scale, 1000f * scale);
		updateView();
	}

	@Override
	public void onAttached() {
		// TODO Get FOV
		projection = MatrixMath.createPerspective(fieldOfView, 4.0f / 3.0f, .001f, 1000f);
		updateView();
	}

	@Override
	public Matrix getProjection() {
		return projection;
	}

	@Override
	public Matrix getView() {
		return view;
	}

	@Override
	public void updateView() {
		Transform transform = getOwner().getPhysics().getTransformRender();
		Point point = transform.getPosition().add(0.0f, getHeight(), 0.0f);
		Matrix pos = MatrixMath.createTranslated(point.multiply(-1));
		Matrix rot = getRotation();
		view = pos.multiply(rot);
		frustum.update(projection, view, transform.getPosition());
	}

	@Override
	public void updateReflectedView() {
		Transform transform = getOwner().getPhysics().getTransformRender();
		Point point = transform.getPosition().add(0.0f, getHeight(), 0.0f);
		Matrix pos = MatrixMath.createTranslated(point.multiply(-1));
		Matrix rot = getRotation();
		view = MatrixMath.createScaled(new Vector3(1,-1,1)).multiply(pos).multiply(rot);
		frustum.update(projection, view, transform.getPosition());
	}

	@Override
	public boolean canTick() {
		return false;
	}

	@Override
	public ViewFrustum getFrustum() {
		return frustum;
	}

	@Override
	public Matrix getRotation(){
		return MatrixMath.createRotated(getOrientation());
	}

}
