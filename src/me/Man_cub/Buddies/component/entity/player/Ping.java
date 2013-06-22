package me.Man_cub.Buddies.component.entity.player;

import me.Man_cub.Buddies.ChatStyle;
import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;
import me.Man_cub.Buddies.event.player.network.PlayerPingEvent;

import org.spout.api.entity.Player;
import org.spout.api.math.GenericMath;

public class Ping extends BuddiesEntityComponent {
	private Player player;
	private final float timeout = 30;
	private final float longTimeout = 120;
	private final int repeatRate = 8;
	private final long[] pingTime = new long[repeatRate];
	private final int[] pingHash = new int[repeatRate];
	private float pingTimer = timeout;
	private float kickTimer = 0;
	private float longKickTimer = 0;
	private float ping = 0;
	private int lastRequestCount = 0;

	@Override
	public void onAttached() {
		if (!(getOwner() instanceof Player)) {
			throw new IllegalStateException("PingComponent may only be attached to a player.");
		}
		player = (Player) getOwner();
	}

	@Override
	public void onTick(float dt) {
		pingTimer += dt;
		kickTimer += dt;
		longKickTimer += dt;
		float period = timeout / repeatRate;
		if (pingTimer > period) {
			pingTimer -= period;
			request();
		}
		if (kickTimer > timeout) {
			player.kick(ChatStyle.RED + "Connection timed out!");
		}
		if (longKickTimer > longTimeout) {
			player.kick(ChatStyle.RED + "Connection timed out due to no ping response!");
		}
	}

	/**
	 * Gets the Player Ping in seconds
	 * @return player ping in seconds
	 */
	public float getPing() {
		return ping;
	}

	/**
	 * Re-sets the long timeout timer by validating the received hash code
	 * @param hash of the message sent to validate against
	 */
	public void response(int hash) {
		refresh();
		for (int i = 0; i < pingTime.length; i++) {
			if (hash == pingHash[i]) {
				ping = (System.currentTimeMillis() - pingTime[i]) / 1000.0F;
				longKickTimer = 0;
				return;
			}
		}
	}

	/**
	 * Re-sets the short timeout timer when any data is received from the client
	 */
	public void refresh() {
		kickTimer = 0;
	}

	/**
	 * Sends a new request to the client to return a ping message.
	 */
	public void request() {
		int hash = GenericMath.getRandom().nextInt(Integer.MAX_VALUE);
		pingTime[lastRequestCount] = System.currentTimeMillis();
		pingHash[lastRequestCount] = hash;
		lastRequestCount++;
		if (lastRequestCount >= pingTime.length) {
			lastRequestCount = 0;
		}
		player.getNetworkSynchronizer().callProtocolEvent(new PlayerPingEvent(hash));
	}
}
