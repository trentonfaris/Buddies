package me.Man_cub.Buddies.protocol.entity;

import java.util.ArrayList;
import java.util.List;

import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.entity.living.buddy.Buddy;
import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.entity.EntityEquipmentMessage;
import me.Man_cub.Buddies.protocol.message.player.position.PlayerSpawnMessage;

import org.spout.api.entity.Entity;
import org.spout.api.inventory.ItemStack;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.Parameter;

public class BuddyEntityProtocol extends BuddiesEntityProtocol {
	
	@Override
	public List<Message> getSpawnMessages(Entity entity, RepositionManager rm) {

		List<Message> messages = new ArrayList<Message>(6);

		Buddy buddy = entity.add(Buddy.class);

		int id = entity.getId();
		int x = ChannelBufferUtils.protocolifyPosition(rm.convertX(entity.getScene().getPosition().getX()));
		int y = ChannelBufferUtils.protocolifyPosition(rm.convertY(entity.getScene().getPosition().getY()));
		int z = ChannelBufferUtils.protocolifyPosition(rm.convertZ(entity.getScene().getPosition().getZ()));
		int r = ChannelBufferUtils.protocolifyYaw(entity.getScene().getRotation().getYaw());
		int p = ChannelBufferUtils.protocolifyPitch(entity.getScene().getRotation().getPitch());

		int item = 0;
		BuddyInventory inv = entity.get(BuddyInventory.class);
		if (inv != null) {
			if (inv.getHeldItem() != null) {
				item = inv.getHeldItem().getMaterial().getId();
			}
		}
		List<Parameter<?>> parameters = new ArrayList<Parameter<?>>();
		parameters.add(new Parameter<Short>(Parameter.TYPE_SHORT, 1, (short) 100));

		messages.add(new PlayerSpawnMessage(id, buddy.getName(), x, y, z, r, p, item, parameters));

		// Armor
		final ItemStack held;
		if (inv == null) {
			held = null;
		} else {
			held = inv.getHeldItem();
		}
		// TODO : This method needs work. Lots of it.
		if (held != null) {
			messages.add(new EntityEquipmentMessage(entity.getId(), held));
		}

		return messages;
	}

}
