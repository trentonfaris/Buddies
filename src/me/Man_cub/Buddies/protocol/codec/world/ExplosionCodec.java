package me.Man_cub.Buddies.protocol.codec.world;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.world.ExplosionMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class ExplosionCodec extends MessageCodec<ExplosionMessage> {
	
	public ExplosionCodec() {
		super(ExplosionMessage.class, 0x3C);
	}

	@Override
	public ExplosionMessage decode(ChannelBuffer buffer) throws IOException {
		double x = buffer.readDouble();
		double y = buffer.readDouble();
		double z = buffer.readDouble();
		float radius = buffer.readFloat();
		int records = buffer.readInt();
		byte[] coordinates = new byte[records * 3];
		buffer.readBytes(coordinates);
		buffer.readFloat(); // unknown (x?)
		buffer.readFloat(); // unknown (y?)
		buffer.readFloat(); // unknown (z?)
		return new ExplosionMessage(x, y, z, radius, coordinates, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(ExplosionMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeDouble(message.getX());
		buffer.writeDouble(message.getY());
		buffer.writeDouble(message.getZ());
		buffer.writeFloat(message.getRadius());
		buffer.writeInt(message.getRecords());
		buffer.writeBytes(message.getCoordinates());
		buffer.writeFloat(0.0f); // unknown (x?)
		buffer.writeFloat(0.0f); // unknown (y?)
		buffer.writeFloat(0.0f); // unknown (z?)
		return buffer;
	}

}
