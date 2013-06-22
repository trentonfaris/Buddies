package me.Man_cub.Buddies.protocol.message.player.connection;

import me.Man_cub.Buddies.protocol.message.BuddiesMainChannelMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.protocol.proxy.RedirectMessage;
import org.spout.api.util.SpoutToStringStyle;

public final class PlayerKickMessage extends BuddiesMainChannelMessage implements RedirectMessage {
	private final String reason;
	private String hostname;
	private int port;
	
	public PlayerKickMessage(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("reason", reason).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PlayerKickMessage other = (PlayerKickMessage) obj;
		return new EqualsBuilder().append(this.reason, other.reason).isEquals();
	}
	
	private static String redirectDetected(String reason) {
		String hostname = null;
		int portnum = -1;

		if (reason.indexOf("[Serverport]") != 0 && reason.indexOf("[Redirect]") != 0) {
			return null;
		}

		String[] split = reason.split(":");
		if (split.length == 3) {
			hostname = split[1].trim();
	try {
		portnum = Integer.parseInt(split[2].trim());
	} catch (Exception e) {
		portnum = -1;
	}
		} else if (split.length == 2) {
			hostname = split[1].trim();
			portnum = 25565;
		}

		int commaPos = reason.indexOf(",");
		if (commaPos >= 0) {
			return reason.substring(reason.indexOf(":") + 1).trim();
		}

		if (portnum != -1) {
			return hostname + ":" + portnum;
		} else {
			return null;
		}
	}

	@Override
	public boolean isRedirect() {
		String redirect = redirectDetected(reason);
		if (redirect == null) {
			return false;
		}
		String[] split = redirect.split(":");
		hostname = split[0];
		if (split.length < 2) {
			port = 25565;
		} else {
			port = Integer.parseInt(split[1]);
		}

		return true;
	}

	@Override
	public String getHostname() {
		return hostname;
	}

	@Override
	public int getPort() {
		return port;
	}
	
}
