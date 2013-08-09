package me.man_cub.buddies.component.entity.player.hud;

import java.awt.Color;

import me.man_cub.buddies.BuddiesPlugin;
import me.man_cub.buddies.ChatStyle;
import me.man_cub.buddies.component.entity.BuddiesEntityComponent;
import me.man_cub.buddies.component.entity.player.HUD;
import me.man_cub.buddies.component.world.misc.Timer;
import me.man_cub.buddies.data.BuddiesResources;

import org.spout.api.Client;
import org.spout.api.component.widget.LabelComponent;
import org.spout.api.component.widget.RenderPartComponent;
import org.spout.api.entity.Player;
import org.spout.api.gui.Widget;
import org.spout.api.math.Rectangle;
import org.spout.api.Platform;

public class TimerWidget extends BuddiesEntityComponent {	
	protected static final float SCALE = 0.75f;
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();
	
	//Client only
	private Widget timer;
	private Widget time;
	
	public TimerWidget() {
		if (plugin.getEngine().getPlatform() == Platform.CLIENT) {
			timer = ((Client) plugin.getEngine()).getScreenStack().createWidget();
			time = ((Client) plugin.getEngine()).getScreenStack().createWidget();
		}
	}
	
	@Override
	public boolean canTick() {
		return true;
	}
	
	@Override
	public void onAttached() {
		if (plugin.getEngine() instanceof Client && getOwner() instanceof Player) {			
			final RenderPartComponent timerRect = timer.add(RenderPartComponent.class);
			timerRect.setRenderMaterial(BuddiesResources.ICONS_MAT);
			timerRect.setColor(Color.WHITE);
			timerRect.setSprite(new Rectangle(-0.2f, 0.8f, 0.4f, 0.2f));
			timerRect.setSource(new Rectangle(16f / 256f, 16f / 256f, 40f / 256f, 24f / 256f));
			timerRect.attachTo(timer);
			
			time.getTransform().setPosition(-0.155f, 0.88f);
			time.getTransform().setScale(2.5f);
			LabelComponent timerMessage = time.add(LabelComponent.class);
			timerMessage.setFont(BuddiesResources.FONT);
									
			getOwner().get(HUD.class).attachWidget(timer);
			getOwner().get(HUD.class).attachWidget(time);
		}
	}
	
	@Override
	public void onTick(float dt) {
		int worldTime = getOwner().getWorld().get(Timer.class).getTime();
		
		int seconds = worldTime % 60;
		int minutes = ((int) Math.floor(worldTime / 60)) % 60;
		int hours = (int) Math.floor(worldTime / 3600);
		
		time.get(LabelComponent.class).setText(ChatStyle.WHITE + getTimeValue(seconds, minutes, hours));
		
	}
	
	public String getTimeValue(int seconds, int minutes, int hours) {
		String secondValue = new Integer(seconds).toString();
		if (seconds < 10) {
			secondValue = "0" + secondValue; 
		}
		
		String minuteValue = new Integer(minutes).toString();
		if (minutes < 10) {
			minuteValue = "0" + minuteValue;
		}
		
		String hourValue = new Integer(hours).toString();
		if (hours < 10) {
			hourValue = "0" + hourValue;
		}
		
		String timeValue = hourValue + ":" + minuteValue + ":" + secondValue;
		return timeValue;
	}
	
}
