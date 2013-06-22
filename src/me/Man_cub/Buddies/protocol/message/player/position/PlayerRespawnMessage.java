package me.Man_cub.Buddies.protocol.message.player.position;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerRespawnMessage extends BuddiesMainChannelMessage {
	private final byte difficulty, mode;
	private final int worldHeight, dimension;
	private final String worldType;

	public PlayerRespawnMessage(int dimension, byte difficulty, byte mode, int worldHeight, String worldType) {
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.mode = mode;
		this.worldHeight = worldHeight;
		this.worldType = worldType;
	}

	public PlayerRespawnMessage() {
		this(0, (byte) 0, (byte) 0, 0, "");
	}

	public int getDimension() {
		return dimension;
	}

	public byte getDifficulty() {
		return difficulty;
	}

	public byte getGameMode() {
		return mode;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public String getWorldType() {
		return worldType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("difficulty", difficulty).append("mode", mode).append("worldHeight", worldHeight).append("dimension", dimension).append("worldType", worldType).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PlayerRespawnMessage other = (PlayerRespawnMessage) obj;
		return new EqualsBuilder().append(this.difficulty, other.difficulty).append(this.mode, other.mode).append(this.worldHeight, other.worldHeight).append(this.dimension, other.dimension).append(this.worldType, other.worldType).isEquals();
	}
}
