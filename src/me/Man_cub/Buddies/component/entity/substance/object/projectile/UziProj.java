package me.Man_cub.Buddies.component.entity.substance.object.projectile;

import me.Man_cub.Buddies.component.entity.misc.Health;
import me.Man_cub.Buddies.component.entity.substance.object.Substance;

import org.spout.api.entity.Entity;
import org.spout.api.event.entity.EntityCollideEntityEvent;
import org.spout.api.event.entity.EntityCollideEvent;

public class UziProj extends Substance implements Projectile {
	private Entity shooter;
	
	@Override
	public void onAttached() {
		//getOwner().getNetwork().setEntityProtocol(BuddiesPlugin.BUDDIES_PROTOCOL_ID, new BulletObjectEntityProtocol());
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
	
	@Override
	public void onCollided(EntityCollideEvent event) {
		if (event instanceof EntityCollideEntityEvent) {
			Health health = ((EntityCollideEntityEvent) event).getCollided().get(Health.class);
			if (health != null) {
				health.damage(3);
			}
		}
		getOwner().remove();
	}

}
