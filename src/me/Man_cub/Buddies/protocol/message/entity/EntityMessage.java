package me.Man_cub.Buddies.protocol.message.entity;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;
import me.Man_cub.Buddies.protocol.proxy.BuddiesConnectionInfo;

import org.spout.api.entity.Entity;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.proxy.ConnectionInfo;
import org.spout.api.protocol.proxy.TransformableMessage;

public abstract class EntityMessage extends BuddiesMainChannelMessage implements TransformableMessage {
	protected int id;
	
	public EntityMessage() {
	}
	
	public EntityMessage(Entity entity) {
		this(entity.getId());
	}
	
	public EntityMessage(int id) {
		this.id = id;
	}
	
	public int getEntityId() {
		return this.id;
	}
	
	@Override
	public Message transform(boolean upstream, int connects, ConnectionInfo info, ConnectionInfo auxChannelInfo) {
		if (this.id == ((BuddiesConnectionInfo) info).getEntityId()) {
			this.id = ((BuddiesConnectionInfo) auxChannelInfo).getEntityId();
		} else if (this.id == ((BuddiesConnectionInfo) auxChannelInfo).getEntityId()) {
			this.id = ((BuddiesConnectionInfo) info).getEntityId();
		}
		return this;
	}

}
