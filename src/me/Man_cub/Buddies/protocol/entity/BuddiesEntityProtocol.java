package me.Man_cub.Buddies.protocol.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.Man_cub.Buddies.component.entity.misc.EntityBody;
import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.entity.EntityDestroyMessage;
import me.Man_cub.Buddies.protocol.message.entity.EntityMetadataMessage;
import me.Man_cub.Buddies.protocol.message.entity.position.EntityBodyYawMessage;
import me.Man_cub.Buddies.protocol.message.entity.position.EntityRelativePositionMessage;
import me.Man_cub.Buddies.protocol.message.entity.position.EntityRelativePositionYawMessage;
import me.Man_cub.Buddies.protocol.message.entity.position.EntityTeleportMessage;
import me.Man_cub.Buddies.protocol.message.entity.position.EntityYawMessage;

import org.spout.api.entity.Entity;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.EntityProtocol;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.Parameter;

import static me.Man_cub.Buddies.protocol.ChannelBufferUtils.protocolifyPitch;
import static me.Man_cub.Buddies.protocol.ChannelBufferUtils.protocolifyPosition;
import static me.Man_cub.Buddies.protocol.ChannelBufferUtils.protocolifyYaw;

public abstract class BuddiesEntityProtocol implements EntityProtocol {
	private List<Parameter<?>> lastMeta;

	public List<Parameter<?>> getUpdateParameters(Entity entity) {
		return Collections.emptyList();
	}

	@Override
	public final List<Message> getDestroyMessages(Entity entity) {
		return Arrays.<Message>asList(new EntityDestroyMessage(new int[]{entity.getId()}));
	}

	@Override
	public List<Message> getUpdateMessages(Entity entity, Transform liveTransform, RepositionManager rm, boolean force) {
		// Movement
		final Transform prevTransform = rm.convert(entity.getScene().getTransform());
		final Transform newTransform = rm.convert(liveTransform);

		final boolean looked = entity.getScene().isRotationDirty();

		final int lastX = protocolifyPosition(prevTransform.getPosition().getX());
		final int lastY = protocolifyPosition(prevTransform.getPosition().getY());
		final int lastZ = protocolifyPosition(prevTransform.getPosition().getZ());
		final int lastYaw = protocolifyYaw(prevTransform.getRotation().getYaw());
		final int lastPitch = protocolifyPitch(prevTransform.getRotation().getPitch());

		final int newX = protocolifyPosition(newTransform.getPosition().getX());
		final int newY = protocolifyPosition(newTransform.getPosition().getY());
		final int newZ = protocolifyPosition(newTransform.getPosition().getZ());
		final int newYaw = protocolifyYaw(newTransform.getRotation().getYaw());
		final int newPitch = protocolifyPitch(newTransform.getRotation().getPitch());

		final int deltaX = newX - lastX;
		final int deltaY = newY - lastY;
		final int deltaZ = newZ - lastZ;
		final int deltaYaw = newYaw - lastYaw;
		final int deltaPitch = newPitch - lastPitch;

		final List<Message> messages = new ArrayList<Message>();

		/*
		 * Two scenarios:
		 * - The entity moves more than 4 blocks and maybe changes rotation.
		 * - The entity moves less than 4 blocks and maybe changes rotation.
		 */
		if (force || deltaX > 128 || deltaX < -128 || deltaY > 128 || deltaY < -128 || deltaZ > 128 || deltaZ < -128) {
			messages.add(new EntityTeleportMessage(entity.getId(), newX, newY, newZ, newYaw, newPitch));
			if (force || looked) {
				messages.add(new EntityYawMessage(entity.getId(), newYaw, newPitch));
			}
		} else if (deltaX != 0 || deltaY != 0 || deltaZ != 0 || deltaYaw != 0 || deltaPitch != 0) {
			if (looked) {
				messages.add(new EntityRelativePositionYawMessage(entity.getId(), deltaX, deltaY, deltaZ, newYaw, newPitch));
			} else if (!prevTransform.getPosition().equals(newTransform.getPosition())) {
				messages.add(new EntityRelativePositionMessage(entity.getId(), deltaX, deltaY, deltaZ));
			}
		}

		// Body movement
		EntityBody body = entity.get(EntityBody.class);
		if (body != null && body.isDirty()) {
			final int headYawProt = ChannelBufferUtils.protocolifyYaw(body.getOrientation().getYaw());
			messages.add(new EntityBodyYawMessage(entity.getId(), headYawProt));
		}

		// Physics
		//TODO: Actually not used?
		/*if (physics != null && physics.isLinearVelocityDirty()) {
			messages.add(new EntityVelocityMessage(entity.getId(), new Vector3(0, 0, 0)));
		}*/

		// Extra metadata
		List<Parameter<?>> params = getUpdateParameters(entity);
		if (lastMeta == null || !lastMeta.equals(params)) {
			messages.add(new EntityMetadataMessage(entity.getId(), params));
			lastMeta = params;
		}

		return messages;
	}

	public static Vector3 getProtocolVelocity(Vector3 velocity) {
		final float x = velocity.getX() * 32000;
		final float y = velocity.getY() * 32000;
		final float z = velocity.getZ() * 32000;
		return new Vector3(x, y, z);
	}

}
