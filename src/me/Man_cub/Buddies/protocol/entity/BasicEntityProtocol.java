package me.Man_cub.Buddies.protocol.entity;

import java.util.ArrayList;
import java.util.List;

import org.spout.api.entity.Entity;
import org.spout.api.util.Parameter;

public abstract class BasicEntityProtocol extends BuddiesEntityProtocol {
	protected final int typeId;

	public BasicEntityProtocol(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}

	public List<Parameter<?>> getSpawnParameters(Entity entity) {
		return new ArrayList<Parameter<?>>();
	}

}
