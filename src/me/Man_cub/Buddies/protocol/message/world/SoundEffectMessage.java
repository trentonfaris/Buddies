package me.Man_cub.Buddies.protocol.message.world;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.math.Vector3;
import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.util.SpoutToStringStyle;

public class SoundEffectMessage extends BuddiesMainChannelMessage {
	private final float x, y, z, volume, pitch;
	private final String soundName;

	public SoundEffectMessage(String soundName, Vector3 position, float volume, float pitch, RepositionManager rm) {
		this(soundName, position.getX(), position.getY(), position.getZ(), volume, pitch, rm);
	}

	public SoundEffectMessage(String soundName, float x, float y, float z, float volume, float pitch, RepositionManager rm) {
		this.x = rm.convertX(x);
		this.y = rm.convertY(y);
		this.z = rm.convertZ(z);
		this.soundName = soundName;
		this.volume = volume;
		this.pitch = pitch;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getZ() {
		return this.z;
	}

	public String getSoundName() {
		return this.soundName;
	}

	public float getVolume() {
		return this.volume;
	}

	public float getPitch() {
		return this.pitch;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("sound", soundName)
				.append("x", x)
				.append("y", y)
				.append("z", z)
				.append("volume", volume)
				.append("pitch", pitch)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SoundEffectMessage other = (SoundEffectMessage) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.soundName, other.soundName)
				.append(this.x, other.x)
				.append(this.y, other.y)
				.append(this.z, other.z)
				.append(this.volume, other.volume)
				.append(this.pitch, other.pitch)
				.isEquals();
	}

}
