package me.man_cub.buddies.event.entity.network;

import org.spout.api.entity.Entity;
import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.event.entity.EntityEvent;
import org.spout.api.inventory.ItemStack;

public class EntityEquipmentEvent extends ProtocolEvent implements EntityEvent {
	private static final HandlerList handlers = new HandlerList();
	private ItemStack item;
	private final Entity entity;
	
	public EntityEquipmentEvent(Entity e, ItemStack item) {
		this.entity = e;
		this.item = item;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public void setItem(ItemStack item) {
		this.item = item;
	}
	
	@Override
	public Entity getEntity() {
		return entity;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
