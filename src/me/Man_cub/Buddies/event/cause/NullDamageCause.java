package me.Man_cub.Buddies.event.cause;

import org.spout.api.event.Cause;

public class NullDamageCause extends Cause<Object> implements DamageCause<Object> {
	private final DamageType type;
	
	public NullDamageCause(DamageType type) {
		this.type = type;
	}
	
	@Override
	public DamageType getType() {
		return type;
	}
	
	@Override
	public Object getSource() {
		return null;
	}

}
