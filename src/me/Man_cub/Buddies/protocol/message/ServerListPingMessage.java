package me.Man_cub.Buddies.protocol.message;

public class ServerListPingMessage extends BuddiesMainChannelMessage {
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{}";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && getClass().equals(obj.getClass());
	}
	
	@Override
	public boolean isAsync() {
		return true;
	}

}
