package me.Man_cub.Buddies.component.entity.substance.object.projectile;

import me.Man_cub.Buddies.component.entity.misc.Health;
import me.Man_cub.Buddies.component.entity.substance.object.Substance;

import org.spout.api.entity.Entity;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.discrete.Point;

public class ShotgunProj extends Substance implements Projectile {
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
	public void onCollided(Point point, Entity entity) {
		Health health = entity.get(Health.class);
		if (health != null) {
			health.damage(15);
		}
		getOwner().remove();
	}

	@Override
	public void onCollided(Point point, Block block) {
		getOwner().remove();
	}
}
