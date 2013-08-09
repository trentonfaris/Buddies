package me.man_cub.buddies.component.entity.misc;

import me.man_cub.buddies.component.entity.BuddiesEntityComponent;
import me.man_cub.buddies.data.BuddiesData;

import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.Quaternion;
import org.spout.api.math.QuaternionMath;
import org.spout.api.math.Vector3;
import org.spout.api.math.VectorMath;
import org.spout.api.util.BlockIterator;

public class EntityBody extends BuddiesEntityComponent {
	private Quaternion lastRotation = Quaternion.IDENTITY;

	@Override
	public void onAttached() {

	}

	/**
	 * Checks whether this head has changed rotation since last tick
	 * @return True if the head rotation is dirty, False if not
	 */
	public boolean isDirty() {
		return !lastRotation.equals(getOrientation());
	}

	/**
	 * Sets the rotation of the head to look into a certain direction
	 * @param lookingAt {@link org.spout.api.math.Vector3} to look at
	 */
	public void setLooking(Vector3 lookingAt) {
		setOrientation(QuaternionMath.rotationTo(Vector3.ZERO, lookingAt));
	}

	/**
	 * Gets the {@link Vector3} the head is currently looking at.
	 * @return Head direction vector
	 */
	public Vector3 getLookingAt() {
		return VectorMath.getDirection(getOrientation());
	}

	/**
	 * Sets the rotation of the head
	 * @param rotation to set to
	 */
	public void setOrientation(Quaternion rotation) {
		lastRotation = getOrientation();
		getData().put(BuddiesData.BODY_ROTATION, rotation);
	}

	/**
	 * Gets the rotation of the head
	 * @return Head rotation
	 */
	public Quaternion getOrientation() {
		return getData().get(BuddiesData.BODY_ROTATION);
	}

	/**
	 * Sets the current height of the head above the main position
	 * @param height
	 */
	public void setHeight(int height) {
		getData().put(BuddiesData.BODY_HEIGHT, height);
	}

	/**
	 * Gets the current height of the head above the main position
	 * @return Head height
	 */
	public int getHeight() {
		return getData().get(BuddiesData.BODY_HEIGHT);
	}

	/**
	 * Gets the position of the head in the world
	 * @return Head position
	 */
	public Point getPosition() {
		return getOwner().getPhysics().getPosition().add(0.0f, this.getHeight(), 0.0f);
	}

	/**
	 * Gets the transform of this head in the world
	 * @return Head transform
	 */
	public Transform getBodyTransform() {
		Transform trans = new Transform();
		trans.setPosition(this.getPosition());
		trans.setRotation(getOrientation());
		return trans;
	}

	/**
	 * Gets a block iterator that iterates the blocks this head can see<br>
	 * The view distance is limited to the reach of the Entity
	 * @return Block iterator
	 */
	public BlockIterator getBlockView() {
		return getBlockView(getData().get(BuddiesData.INTERACT_REACH));
	}

	/**
	 * Gets a block iterator that iterates the blocks this head can see
	 * @param maxDistance the blocks can be iterated
	 * @return Block iterator
	 */
	public BlockIterator getBlockView(int maxDistance) {
		return new BlockIterator(getOwner().getWorld(), getBodyTransform(), maxDistance);
	}

}
