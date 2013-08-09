package me.man_cub.buddies.material.item.misc;

import me.man_cub.buddies.component.entity.substance.object.projectile.UziProj;
import me.man_cub.buddies.material.item.Bullet;

import org.spout.api.entity.Entity;
import org.spout.api.event.player.Action;

public class UziBulletItem extends Bullet {

	public UziBulletItem(String name, int id) {
		super(name, id, UziProj.class);
		this.setMaxStackSize(-1);
	}
	
	@Override
	public void onInteract(Entity entity, Action type) {
		super.onInteract(entity, type);
		if (type == Action.LEFT_CLICK) {
			
		}
	}

}
