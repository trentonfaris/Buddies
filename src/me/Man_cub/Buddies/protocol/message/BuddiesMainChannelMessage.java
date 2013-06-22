package me.Man_cub.Buddies.protocol.message;

public abstract class BuddiesMainChannelMessage extends BuddiesMessage {
	protected BuddiesMainChannelMessage() {
		super(DEFAULT_CHANNEL);
	}
	
	protected BuddiesMainChannelMessage(int channelId) {
		super(channelId);
	}

}
