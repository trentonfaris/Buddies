package me.Man_cub.Buddies.input;

import me.Man_cub.Buddies.component.entity.misc.EntityBody;

import org.spout.api.component.entity.PhysicsComponent;
import org.spout.api.entity.Player;
import org.spout.api.entity.state.PlayerInputState;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.input.InputExecutor;
import org.spout.api.math.QuaternionMath;
import org.spout.api.math.Vector3;

public class BuddiesInputExecutor implements InputExecutor {
	private Player player;
	private float speed = 50;;
	
	public BuddiesInputExecutor(Player player) {
		this.player = player;
	}

	@Override
	public void execute(float dt, Transform playerTransform) {
		PlayerInputState inputState = player.input();
		PhysicsComponent pc = player.getPhysics();
		Transform ts = pc.getTransform();
		
		Vector3 offset = Vector3.ZERO;
		if (inputState.getForward()) {
			offset = offset.subtract(ts.forwardVector().multiply(speed * dt));
		}
		if (inputState.getBackward()) {
			offset = offset.add(ts.forwardVector().multiply(speed * dt));
		}
		if (inputState.getLeft()) {
			offset = offset.subtract(ts.rightVector().multiply(speed * dt));
		}
		if (inputState.getRight()) {
			offset = offset.add(ts.rightVector().multiply(speed * dt));
		}
		if (inputState.getJump()) {
			offset = offset.add(ts.upVector().multiply(speed * dt));
		}
		if (inputState.getCrouch()) {
			offset = offset.subtract(ts.upVector().multiply(speed * dt));
		}
		player.get(EntityBody.class).setOrientation(QuaternionMath.rotation(inputState.pitch(), inputState.yaw(), ts.getRotation().getRoll()));
		ts.translateAndSetRotation(offset, QuaternionMath.rotation(inputState.pitch(), inputState.yaw(), ts.getRotation().getRoll()));
		pc.setTransform(ts);
	}

}
