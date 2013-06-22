package me.Man_cub.Buddies.protocol.codec.world;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.world.ParticleEffectMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public class ParticleEffectCodec extends MessageCodec<ParticleEffectMessage> {
	
	public ParticleEffectCodec() {
		super(ParticleEffectMessage.class, 0x3F);
	}

	@Override
	public ParticleEffectMessage decode(ChannelBuffer buffer) throws IOException {
		String name = ChannelBufferUtils.readString(buffer);
		float x = buffer.readFloat();
		float y = buffer.readFloat();
		float z = buffer.readFloat();
		float xOffset = buffer.readFloat();
		float yOffset = buffer.readFloat();
		float zOffset = buffer.readFloat();
		float velocity = buffer.readFloat();
		int amount = buffer.readInt();
		return new ParticleEffectMessage(name, x, y, z, xOffset, yOffset, zOffset, velocity, amount, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(ParticleEffectMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getName());
		buffer.writeFloat(message.getX());
		buffer.writeFloat(message.getY());
		buffer.writeFloat(message.getZ());
		buffer.writeFloat(message.getXOffset());
		buffer.writeFloat(message.getYOffset());
		buffer.writeFloat(message.getZOffset());
		buffer.writeFloat(message.getVelocity());
		buffer.writeInt(message.getAmount());
		return buffer;
	}

}
