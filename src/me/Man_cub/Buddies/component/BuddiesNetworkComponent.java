package me.Man_cub.Buddies.component;

import org.spout.api.protocol.EntityProtocol;

public interface BuddiesNetworkComponent {

	/**
	 * Returns the {@link EntityProtocol} for this type of entity
	 *
	 * @return The entity protocol for the specified id.
	 */
	EntityProtocol getEntityProtocol();

	/**
	 * Registers the {@code protocol} as the Entity's protocol
	 *
	 * @param protocol The protocol to set
	 */
	void setEntityProtocol(EntityProtocol protocol);
	
}
