package me.Man_cub.Buddies.component.entity.player.hud;

import java.awt.Color;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;
import me.Man_cub.Buddies.component.entity.player.HUD;

import org.spout.api.Client;
import org.spout.api.component.widget.RenderPartComponent;
import org.spout.api.entity.Player;
import org.spout.api.gui.Widget;
import org.spout.api.math.Rectangle;
import org.spout.api.Platform;

public class MinimapWidget extends BuddiesEntityComponent {
	private Widget map;
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();
	
	public MinimapWidget() {
		if (plugin.getEngine().getPlatform() == Platform.CLIENT) {
			map = ((Client) plugin.getEngine()).getScreenStack().createWidget();
		}
	}
	
	@Override
	public boolean canTick() {
		return true;
	}
	
	@Override
	public void onAttached() {
		if (plugin.getEngine() instanceof Client && getOwner() instanceof Player) {
			final RenderPartComponent mapRect = map.add(RenderPartComponent.class);
			mapRect.setColor(new Color(0, 0, 0, 0.3f));
			mapRect.setSprite(new Rectangle(0.7f, 0.7f, 0.3f, 0.3f));
			mapRect.attachTo(map);
			
			getOwner().get(HUD.class).attachWidget(map);
		}
	}

}
