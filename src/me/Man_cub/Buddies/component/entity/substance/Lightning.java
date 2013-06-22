package me.Man_cub.Buddies.component.entity.substance;

import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;

public class Lightning extends BuddiesEntityComponent {
	private int timeLeft = 20;

	@Override
	public void onAttached() {
		//getOwner().getNetwork().setEntityProtocol(BuddiesPlugin.BUDDIES_PROTOCOL_ID, new LightningEntityProtocol());
	}

	@Override
	public void onTick(float dt) {
		if (timeLeft-- <= 0) {
			getOwner().remove();
		}
	}
}
