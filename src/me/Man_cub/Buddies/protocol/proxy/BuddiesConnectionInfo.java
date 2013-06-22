package me.Man_cub.Buddies.protocol.proxy;

import org.spout.api.protocol.proxy.ConnectionInfo;

public class BuddiesConnectionInfo implements ConnectionInfo {
	private final String playerName;
	private final int entityId;

	public BuddiesConnectionInfo(String playerName, int entityId) {
		this.playerName = playerName;
		this.entityId = entityId;
	}

	@Override
	public String toString() {
		return "BuddiesConnectionInfo{ \"" + playerName + "\" entityId: " + entityId + "}";
	}

	@Override
	public String getIdentifier() {
		return playerName;
	}

	public int getEntityId() {
		return entityId;
	}

}
