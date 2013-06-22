package me.Man_cub.Buddies.protocol.message;

public abstract class BuddiesBlockDataChannelMessage extends BuddiesMessage {
	public static final int CHANNEL_ID = 1;

	protected BuddiesBlockDataChannelMessage() {
		super(CHANNEL_ID);
	}

	protected BuddiesBlockDataChannelMessage(int channelId) {
		super(channelId);
	}

}
