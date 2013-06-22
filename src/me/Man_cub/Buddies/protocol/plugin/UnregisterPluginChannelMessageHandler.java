package me.Man_cub.Buddies.protocol.plugin;

import me.Man_cub.Buddies.protocol.BuddiesProtocol;

import org.spout.api.protocol.MessageHandler;
import org.spout.api.protocol.Session;

public class UnregisterPluginChannelMessageHandler extends MessageHandler<UnregisterPluginChannelMessage> {
	
	@Override
	public void handle(boolean upstream, Session session, UnregisterPluginChannelMessage message) {
		if (message.getTypes().contains("REGISTER") || message.getTypes().contains("UNREGISTER")) {
			throw new IllegalArgumentException("Plugin channels REGISTER and UNREGISTER cannot be unregistered!");
		}
		session.getDataMap().get(BuddiesProtocol.REGISTERED_CUSTOM_PACKETS).removeAll(message.getTypes());
	}

}
