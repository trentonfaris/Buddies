package me.Man_cub.Buddies.component.entity.player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;
import me.Man_cub.Buddies.event.player.network.PlayerListEvent;

import org.spout.api.Server;
import org.spout.api.entity.Player;

public class PlayerList extends BuddiesEntityComponent {
	private Player player;
	private Server server;
	private final LinkedHashMap<String, Long> players = new LinkedHashMap<String, Long>();
	private final HashSet<String> temp = new HashSet<String>();
	private float pollPeriod = 5F; // Every 5 seconds
	private float timer = 0;
	private boolean force; // If true will force the list on the next tick
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();

	@Override
	public void onAttached() {
		if (!(getOwner() instanceof Player)) {
			throw new IllegalStateException("PlayerListComponent may only be attached to a player.");
		}
		if (!(plugin.getEngine() instanceof Server)) {
			throw new IllegalStateException("Player list components may only be attached server side");
		}
		server = (Server) plugin.getEngine();
		player = (Player) getOwner();
	}

	@Override
	public void onTick(float dt) {
		timer -= dt;
		if (timer < 0.0F) {
			pollList();
			timer += pollPeriod;
		} else if (force) {
			pollList();
			force = false;
		}
	}

	private void pollList() {
		Player[] online = server.getOnlinePlayers();
		temp.clear();
		for (int i = 0; i < online.length; i++) {
			Ping pingComponent = online[i].get(Ping.class);
			if (pingComponent != null && !player.isInvisible(online[i])) {
				String name = online[i].getDisplayName();
				long ping = (long) (1000.0F * online[i].get(Ping.class).getPing());
				temp.add(name);
				Long oldPing = players.put(name, ping);
				if (oldPing == null || !oldPing.equals(ping)) {
					//player.getNetworkSynchronizer().callProtocolEvent(new PlayerListEvent(name, ping, true));
				}
			}
		}
		Iterator<String> itr = players.keySet().iterator();
		while (itr.hasNext()) {
			String name = itr.next();
			if (!temp.contains(name)) {
				player.getNetworkSynchronizer().callProtocolEvent(new PlayerListEvent(name, 0L, false));
				itr.remove();
			}
		}
	}

	/**
	 * Force a update of the list.
	 */
	public void force() {
		force = true;
	}

}
