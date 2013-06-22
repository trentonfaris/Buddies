package me.Man_cub.Buddies.protocol.netcache.protocol;

import me.Man_cub.Buddies.component.entity.player.Ping;
import me.Man_cub.Buddies.protocol.BuddiesProtocol;

import org.spout.api.protocol.MessageHandler;
import org.spout.api.protocol.Session;

public class ChunkCacheHandler extends MessageHandler<ChunkCacheMessage> {
	
	@Override
	public void handleServer(Session session, ChunkCacheMessage message) {
		Ping ping = session.getPlayer().get(Ping.class);
		if (ping != null) {
			ping.refresh();
		}
		session.getDataMap().get(BuddiesProtocol.CHUNK_NET_CACHE).handleCustomPacket(message.getChannel(), message.getData());
	}

}
