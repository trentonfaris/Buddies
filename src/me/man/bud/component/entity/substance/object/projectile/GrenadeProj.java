package me.man_cub.buddies.component.entity.substance.object.projectile;

import me.man_cub.buddies.component.entity.substance.object.Substance;

import org.spout.api.entity.Entity;

public class GrenadeProj extends Substance implements Projectile {
	private Entity shooter;
	
	@Override
	public void onAttached() {
		//getOwner().getNetwork().setEntityProtocol(BuddiesPlugin.BUDDIES_PROTOCOL_ID, new GrenadeObjectEntityProtocol());
		super.onAttached();
	}
	
	@Override
	public Entity getShooter() {
		return shooter;
	}
	
	@Override 
	public void setShooter(Entity shooter) {
		this.shooter = shooter;
	}

}
