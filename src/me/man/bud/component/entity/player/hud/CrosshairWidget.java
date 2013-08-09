package me.man_cub.buddies.component.entity.player.hud;

import java.awt.Color;

import me.man_cub.buddies.BuddiesPlugin;
import me.man_cub.buddies.component.entity.BuddiesEntityComponent;
import me.man_cub.buddies.component.entity.player.HUD;
import me.man_cub.buddies.data.BuddiesResources;

import org.spout.api.Client;
import org.spout.api.component.widget.RenderPartComponent;
import org.spout.api.entity.Player;
import org.spout.api.gui.Widget;
import org.spout.api.math.Rectangle;
import org.spout.api.Platform;

public class CrosshairWidget extends BuddiesEntityComponent {
	private static final float SCALE = 0.75f;
	private Widget crosshair;
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();
	
	public CrosshairWidget() {
		if (plugin.getEngine().getPlatform() == Platform.CLIENT) {
			crosshair = ((Client) plugin.getEngine()).getScreenStack().createWidget();
		}
	}
	
	@Override
	public boolean canTick() {
		return true;
	}
	
	@Override
	public void onAttached() {
		if (plugin.getEngine() instanceof Client && getOwner() instanceof Player) {
			final RenderPartComponent crosshairRect = crosshair.add(RenderPartComponent.class);
			crosshairRect.setRenderMaterial(BuddiesResources.ICONS_MAT);
			crosshairRect.setColor(Color.WHITE);
			crosshairRect.setSprite(new Rectangle(-0.025f * SCALE, -0.025f, 0.05f * SCALE, 0.05f));
			crosshairRect.setSource(new Rectangle(0f / 256f, 0f / 256f, 16f / 256f, 16f / 256f));
			crosshairRect.attachTo(crosshair);
			
			getOwner().get(HUD.class).attachWidget(crosshair);
			
		}
	}

}
