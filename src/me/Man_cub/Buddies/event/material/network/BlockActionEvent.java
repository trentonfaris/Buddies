package me.Man_cub.Buddies.event.material.network;

import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.BlockMaterial;

public class BlockActionEvent extends ProtocolEvent {
	private static final HandlerList handlers = new HandlerList();
	private final Block block;
	private final BlockMaterial material;
	private final byte data1;
	private final byte data2;

	public BlockActionEvent(Block block, BlockMaterial material, byte data1, byte data2) {
		this.block = block;
		this.data1 = data1;
		this.data2 = data2;
		this.material = material;
	}

	public Block getBlock() {
		return this.block;
	}

	public byte getData1() {
		return this.data1;
	}

	public byte getData2() {
		return this.data2;
	}

	public BlockMaterial getMaterial() {
		return this.material;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
