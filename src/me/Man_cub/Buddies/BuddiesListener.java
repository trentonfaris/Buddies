package me.Man_cub.Buddies;

import me.Man_cub.Buddies.component.entity.living.buddy.Buddy;
import me.Man_cub.Buddies.component.entity.misc.Ammo;
import me.Man_cub.Buddies.component.entity.misc.EntityBody;
import me.Man_cub.Buddies.component.entity.misc.Health;
import me.Man_cub.Buddies.component.entity.player.HUD;
import me.Man_cub.Buddies.component.entity.player.PlayerBody;
import me.Man_cub.Buddies.component.entity.player.hud.CrosshairWidget;
import me.Man_cub.Buddies.component.entity.player.hud.TimerWidget;
import me.Man_cub.Buddies.component.world.misc.Sky;
import me.Man_cub.Buddies.input.BuddiesInputExecutor;

import org.spout.api.Client;
import org.spout.api.component.entity.CameraComponent;
import org.spout.api.component.entity.InteractComponent;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.engine.EngineStartEvent;
import org.spout.api.event.player.PlayerJoinEvent;
import org.spout.api.Platform;

public class BuddiesListener implements Listener {
	private final BuddiesPlugin plugin;
	
	public BuddiesListener(BuddiesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClientEnable(EngineStartEvent event) {
		if (plugin.getEngine().getPlatform() != Platform.CLIENT) {
			return;
		}
		
		Player player = ((Client) plugin.getEngine()).getPlayer();
		
		if (player != null) {
			
			HUD HUD = player.add(HUD.class);
			player.add(TimerWidget.class);
			//player.add(Minimap.class);
			player.add(CrosshairWidget.class);
			HUD.openHUD();
			
			player.add(Buddy.class);
			player.detach(EntityBody.class);
			player.detach(CameraComponent.class);
			
			player.add(PlayerBody.class);
			player.add(Health.class);
			player.add(Ammo.class);
			player.add(InteractComponent.class).setRange(5f);
			
			Sky sky = player.getWorld().get(Sky.class);
			if (sky != null) {
				sky.updatePlayer(player);			// Spout client doesn't have network synchronizer done yet.
			}
			
			((Client) plugin.getEngine()).getInputManager().addInputExecutor(new BuddiesInputExecutor(player));
			player.add(PlayerBody.class);
	
		} else {
			plugin.getEngine().getLogger().info("Obviously null.");
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
	}
	
}
