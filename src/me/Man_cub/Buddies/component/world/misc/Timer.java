package me.Man_cub.Buddies.component.world.misc;

import me.Man_cub.Buddies.component.world.BuddiesWorldComponent;
import me.Man_cub.Buddies.data.BuddiesData;

public class Timer extends BuddiesWorldComponent {
	private int time;
	
	@Override
	public void onTick(float dt) {
		long age = getOwner().getAge();
		time = (int) Math.floor((age / 1000));				
		setTime(time);
	}
	
	public void setTime(int time) {
		getData().put(BuddiesData.WORLD_TIME, time);
	}
	
	public int getTime() {
		return getData().get(BuddiesData.WORLD_TIME).intValue();
	}
	
}
