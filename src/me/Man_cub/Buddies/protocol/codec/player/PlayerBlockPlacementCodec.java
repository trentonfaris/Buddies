package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.player.PlayerBlockPlacementMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.inventory.ItemStack;
import org.spout.api.material.block.BlockFace;
import org.spout.api.material.block.BlockFaces;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class PlayerBlockPlacementCodec extends MessageCodec<PlayerBlockPlacementMessage> {
	
	public PlayerBlockPlacementCodec() {
		super(PlayerBlockPlacementMessage.class, 0x0F);
	}

	@Override
	public PlayerBlockPlacementMessage decode(ChannelBuffer buffer) throws IOException {
		int x = buffer.readInt();
		int y = buffer.readUnsignedByte();
		int z = buffer.readInt();
		BlockFace direction = BlockFaces.BTEWNS.get(buffer.readUnsignedByte(), BlockFace.THIS);

		ItemStack heldItem = ChannelBufferUtils.readItemStack(buffer);

		float dx = ((float) (buffer.readByte() & 0xFF)) / 16.0F;
		float dy = ((float) (buffer.readByte() & 0xFF)) / 16.0F;
		float dz = ((float) (buffer.readByte() & 0xFF)) / 16.0F;

		return new PlayerBlockPlacementMessage(x, y, z, direction, new Vector3(dx, dy, dz), heldItem, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(PlayerBlockPlacementMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(message.getX());
		buffer.writeByte(message.getY());
		buffer.writeInt(message.getZ());
		buffer.writeByte(BlockFaces.BTEWNS.indexOf(message.getDirection(), 255));
		ChannelBufferUtils.writeItemStack(buffer, message.getHeldItem());
		buffer.writeByte((int) (message.getFace().getX() * 16.0F));
		buffer.writeByte((int) (message.getFace().getY() * 16.0F));
		buffer.writeByte((int) (message.getFace().getZ() * 16.0F));
		return buffer;
	}

}
