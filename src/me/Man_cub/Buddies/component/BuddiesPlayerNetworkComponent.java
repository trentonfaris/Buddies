package me.Man_cub.Buddies.component;

//import org.spout.api.component.entity.PlayerNetworkComponent;
import org.spout.api.event.Listener;
import org.spout.api.protocol.EntityProtocol;

public class BuddiesPlayerNetworkComponent /*extends PlayerNetworkComponent */implements BuddiesNetworkComponent, Listener {
	private EntityProtocol protocol;

	/**
	 * Returns the {@link EntityProtocol} for this type of entity
	 *
	 * @return The entity protocol for the specified id.
	 */
	@Override
	public EntityProtocol getEntityProtocol() {
		return protocol;
	}

	/**
	 * Registers the {@code protocol} as the Entity's protocol
	 *
	 * @param protocol The protocol to set
	 */
	@Override
	public void setEntityProtocol(EntityProtocol protocol) {
		this.protocol = protocol;
	}

}
