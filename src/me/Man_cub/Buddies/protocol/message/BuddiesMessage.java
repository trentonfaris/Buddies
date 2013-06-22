package me.Man_cub.Buddies.protocol.message;

import org.spout.api.protocol.Message;

public abstract class BuddiesMessage implements Message {
	private final int channelId;
	
	protected BuddiesMessage(int channelId) {
		this.channelId = channelId;
	}
	
	@Override
	public int getChannelId() {
		return channelId;
	}
	
	@Override
	public boolean isAsync() {
		return false;
	}

}
