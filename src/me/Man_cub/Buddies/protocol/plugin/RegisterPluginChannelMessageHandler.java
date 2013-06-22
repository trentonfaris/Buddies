package me.Man_cub.Buddies.protocol.plugin;


import me.Man_cub.Buddies.protocol.BuddiesProtocol;

import org.spout.api.protocol.MessageHandler;
import org.spout.api.protocol.Session;

public class RegisterPluginChannelMessageHandler extends MessageHandler<RegisterPluginChannelMessage> {
	
	@Override
	public void handle(boolean upstream, Session session, RegisterPluginChannelMessage message) {
		session.getDataMap().get(BuddiesProtocol.REGISTERED_CUSTOM_PACKETS).addAll(message.getTypes());
	}

}
