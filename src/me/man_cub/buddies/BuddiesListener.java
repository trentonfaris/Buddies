package me.man_cub.buddies;

import me.man_cub.buddies.component.entity.inventory.BuddyInventory;
import me.man_cub.buddies.component.entity.living.buddy.Buddy;
import me.man_cub.buddies.component.entity.misc.Ammo;
import me.man_cub.buddies.component.entity.misc.EntityBody;
import me.man_cub.buddies.component.entity.misc.Health;
import me.man_cub.buddies.component.entity.misc.PlayerItemCollector;
import me.man_cub.buddies.component.entity.player.HUD;
import me.man_cub.buddies.component.entity.player.PlayerBody;
import me.man_cub.buddies.component.entity.player.PlayerList;
import me.man_cub.buddies.component.entity.player.hud.CrosshairWidget;
import me.man_cub.buddies.component.entity.player.hud.TimerWidget;
import me.man_cub.buddies.component.world.misc.Sky;
import me.man_cub.buddies.data.configuration.BuddiesConfig;
import me.man_cub.buddies.event.entity.EntityDeathEvent;
import me.man_cub.buddies.event.player.PlayerDeathEvent;
import me.man_cub.buddies.input.BuddiesInputExecutor;

import org.spout.api.Client;
import org.spout.api.component.entity.CameraComponent;
import org.spout.api.component.entity.InteractComponent;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.Result;
import org.spout.api.event.engine.EngineStartEvent;
import org.spout.api.event.player.ClientPlayerConnectedEvent;
import org.spout.api.event.player.PlayerJoinEvent;
import org.spout.api.event.server.permissions.PermissionNodeEvent;
import org.spout.api.Platform;

public class BuddiesListener implements Listener {
	private final BuddiesPlugin plugin;
	
	public BuddiesListener(BuddiesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler (order = Order.EARLIEST)
	public void onPermissionNode(PermissionNodeEvent event) {
		if (BuddiesConfig.OPS.isOp(event.getSubject().getName())) {
			event.setResult(Result.ALLOW);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		player.add(Buddy.class).setName(player.getName());
		player.add(BuddyInventory.class);
		player.add(PlayerList.class);
		
		/*
		if (player.getNetwork() instanceof BuddiesPlayerNetworkComponent) {
			player.add(Ping.class);
		}*/
		
		player.add(PlayerItemCollector.class);
		//player.getNetwork().getSession().setUncaughtExceptionHandler(new PasteExceptionHandler(player.getNetwork().getSession()));
		Sky sky = player.getWorld().get(Sky.class);
		if (sky != null) {
			sky.updatePlayer(player);
		}
	}
	
	@EventHandler
	public void onGameStart(EngineStartEvent event) {
		if (plugin.getEngine().getPlatform() != Platform.CLIENT) {
			return;
		}
		
		Player player = ((Client) plugin.getEngine()).getPlayer();
		
		HUD HUD = player.add(HUD.class);
		player.add(TimerWidget.class);
		//player.add(Minimap.class);
		player.add(CrosshairWidget.class);
		HUD.openHUD();
		
		((Client) player.getEngine()).getInputManager().addInputExecutor(new BuddiesInputExecutor(player));
		
		//Remove Body and default Camera
		player.detach(EntityBody.class);
		player.detach(CameraComponent.class);
		
		player.add(PlayerBody.class);
	}
	
	@EventHandler
	public void onClientConnect(ClientPlayerConnectedEvent event) {
		if (plugin.getEngine().getPlatform() != Platform.CLIENT) {
			return;
		}
		Player player = ((Client) plugin.getEngine()).getPlayer();
		
		player.add(Buddy.class);
		player.detach(EntityBody.class);
		player.detach(CameraComponent.class);
		
		player.add(PlayerBody.class);
		player.add(Health.class);
		player.add(Ammo.class);
		player.add(InteractComponent.class).setRange(5f);
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
	}
}
