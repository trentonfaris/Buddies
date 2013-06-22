package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.entity.EntityEquipmentMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.inventory.ItemStack;
import org.spout.api.protocol.MessageCodec;

public final class EntityEquipmentCodec extends MessageCodec<EntityEquipmentMessage> {
	
	public EntityEquipmentCodec() {
		super(EntityEquipmentMessage.class, 0x05);
	}

	@Override
	public EntityEquipmentMessage decode(ChannelBuffer buffer) throws IOException {
		int entityId = buffer.readInt();
		ItemStack item = ChannelBufferUtils.readItemStack(buffer);
		return new EntityEquipmentMessage(entityId, item);
	}

	@Override
	public ChannelBuffer encode(EntityEquipmentMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(message.getEntityId());
		ChannelBufferUtils.writeItemStack(buffer, message.get());
		return buffer;
	}

}
