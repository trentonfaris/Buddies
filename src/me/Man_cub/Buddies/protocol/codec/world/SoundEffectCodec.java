package me.Man_cub.Buddies.protocol.codec.world;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.world.SoundEffectMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.reposition.NullRepositionManager;

public final class SoundEffectCodec extends MessageCodec<SoundEffectMessage> {
	
	public SoundEffectCodec() {
		super(SoundEffectMessage.class, 0x3e);
	}

	/*
	 * Note to the people that like to move the mathematics involved:
	 * THIS IS PART OF ENCODING AND DECODING
	 * This task is not part of the one actually wishing to send or handle a message.
	 * So don't move this component. Thank you.
	 */

	@Override
	public SoundEffectMessage decode(ChannelBuffer buffer) throws IOException {
		String soundName = ChannelBufferUtils.readString(buffer);
		float x = (float) buffer.readInt() / 8.0f;
		float y = (float) buffer.readInt() / 8.0f;
		float z = (float) buffer.readInt() / 8.0f;
		float volume = buffer.readFloat();
		float pitch = 63f / (float) buffer.readUnsignedByte();
		return new SoundEffectMessage(soundName, x, y, z, volume, pitch, NullRepositionManager.getInstance());
	}

	@Override
	public ChannelBuffer encode(SoundEffectMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getSoundName());
		buffer.writeInt((int) (message.getX() * 8.0f));
		buffer.writeInt((int) (message.getY() * 8.0f));
		buffer.writeInt((int) (message.getZ() * 8.0f));
		buffer.writeFloat(message.getVolume());
		buffer.writeByte((byte) (message.getPitch() * 63f));
		return buffer;
	}

}
