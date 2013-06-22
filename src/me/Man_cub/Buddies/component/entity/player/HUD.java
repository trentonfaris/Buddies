package me.Man_cub.Buddies.component.entity.player;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;

import org.spout.api.Client;
import org.spout.api.entity.Player;
import org.spout.api.gui.Screen;
import org.spout.api.gui.Widget;
import org.spout.api.Platform;

public class HUD extends BuddiesEntityComponent {
	private final Screen hud = new Screen();
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();
		
	@Override
	public void onAttached() {
		if (!(getOwner() instanceof Player)) {
			throw new IllegalStateException("May only attach this component to players!");
		}
		if (plugin.getEngine().getPlatform() != Platform.CLIENT) {
			throw new IllegalStateException("This component is only attached to clients!");
		}
	}

	public void attachWidget(Widget widget) {
		hud.attachWidget(BuddiesPlugin.getInstance(), widget);
	}
	
	public void openHUD() {
		hud.setTakesInput(false);
		((Client) plugin.getEngine()).getScreenStack().openScreen(hud);
	}
	
	public void closeHUD() {
		((Client) plugin.getEngine()).getScreenStack().closeScreen(hud);
	}
	
}
