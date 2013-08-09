package me.man_cub.buddies.component.world.misc;

import java.util.concurrent.atomic.AtomicLong;

import me.man_cub.buddies.BuddiesPlugin;
import me.man_cub.buddies.data.BuddiesData;
import me.man_cub.buddies.data.Weather;
import me.man_cub.buddies.event.world.TimeUpdateEvent;
import me.man_cub.buddies.event.world.WeatherChangeEvent;
import me.man_cub.buddies.render.BuddiesEffects;
import me.man_cub.buddies.util.MathHelper;
import me.man_cub.buddies.world.WeatherSimulator;

import org.spout.api.Client;
import org.spout.api.Spout;
import org.spout.api.component.world.SkydomeComponent;
import org.spout.api.entity.Player;
import org.spout.api.model.Model;

public class Sky extends Timer {
	public static final byte MIN_SKY_LIGHT = 4;
	public static final byte MAX_SKY_LIGHT = 15;
	public static final byte SKY_LIGHT_RANGE = MAX_SKY_LIGHT - MIN_SKY_LIGHT;
	private static final long REFRESH_RATE = 600;
	private AtomicLong countdown = new AtomicLong(REFRESH_RATE);
	private WeatherSimulator weather;
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();
	
	public Sky() {
		super();
		setHasWeather(true);
	}
	
	@Override
	public void onAttached() {
		super.onAttached();
		if (plugin.getEngine() instanceof Client) {
			final SkydomeComponent skydome = getOwner().add(SkydomeComponent.class);
			final Model model = Spout.getFileSystem().getResource("model://Buddies/resources/sky/skydome.spm");
			skydome.setModel(model);
			model.getRenderMaterial().addRenderEffect(BuddiesEffects.SKY);
		}
		getOwner().setSkyLight(MAX_SKY_LIGHT);
	}
	
	@Override
	public void onTick(float dt) {
		final long maxTime = getMaxTime();
		float time = getData().get(BuddiesData.WORLD_TIME).floatValue();
		time += getRate() * (dt / 50F);
		while (time >= maxTime) {
			time -= maxTime;
		}
		if (countdown.getAndDecrement() <= 0) {
			countdown.set(0);
			updateTime((long)time);
		}
		
		getData().put(BuddiesData.WORLD_TIME, time);
		
		synchronized (this) {
			if (this.weather != null) {
				this.weather.onTick(dt);
			}
		}
	}

	/**
	 * Sets the time of the sky.
	 * @param time
	 */
	public void setSkyTime(long time) {
		getData().put(BuddiesData.WORLD_TIME, time);
		countdown.set(0);
	}

	/**
	 * Gets the time of the sky
	 * @return time
	 */
	public long getSkyTime() {
		return getData().get(BuddiesData.WORLD_TIME).longValue();
	}

	/**
	 * Gets the max time of the sky. When the time reached the maxTime, the time
	 * will be set to 0.
	 * @return
	 */
	public long getMaxTime() {
		return getData().get(BuddiesData.MAX_TIME).longValue();
	}

	/**
	 * Sets the max time of the sky. When the time reaches the maxTime, the time
	 * will be set to 0.
	 * @param maxTime
	 */
	public void setMaxTime(long maxTime) {
		getData().put(BuddiesData.MAX_TIME, maxTime);
		countdown.set(0);
	}
	
	public long getRate() {
		return getData().get(BuddiesData.TIME_RATE);
	}
	
	public void setRate(long rate) {
		getData().put(BuddiesData.TIME_RATE, rate);
		countdown.set(0);
	}
	
	/**
	 * Gets the Weather Simulator
	 * @return the weather simulator, or null if no weather is enabled
	 */
	public synchronized WeatherSimulator getWeatherSimulator() {
		return this.weather;
	}

	/**
	 * Whether or not the sky can produce weather
	 * @return true if sky has weather.
	 */
	public synchronized boolean hasWeather() {
		return this.weather != null;
	}

	/**
	 * Sets whether or not the sky can produce weather.
	 * @param hasWeather
	 */
	public synchronized void setHasWeather(boolean hasWeather) {
		if (hasWeather && this.weather == null) {
			this.weather = new WeatherSimulator(this);
		} else {
			this.weather = null;
		}
	}

	/**
	 * Gets the weather of the sky.
	 * @return weather
	 */
	public synchronized Weather getWeather() {
		return this.weather == null ? Weather.CLEAR : this.weather.getCurrent();
	}

	/**
	 * Sets the forecast for the next weather change.
	 * @param forecast
	 */
	public synchronized void setWeather(Weather forecast) {
		if (this.weather != null) {
			this.weather.setForecast(forecast);
			this.weather.forceUpdate();
		}
	}

	/**
	 * Gets the forecast for the next weather change.
	 * @return forecast
	 */
	public synchronized Weather getForecast() {
		return this.weather == null ? Weather.CLEAR : this.weather.getForecast();
	}
	
	public void updateTime(long time) {
		this.updateCelestialTime(time, 1.0f);
	}
	
	public void updateWeather(Weather oldWeather, Weather newWeather) {
		WeatherChangeEvent event = plugin.getEngine().getEventManager().callEvent(new WeatherChangeEvent(getOwner(), oldWeather, newWeather));
		if (event.isCancelled()) {
			return;
		}
		if (oldWeather != newWeather) {
			for (Player player : getOwner().getPlayers()) {
				player.getNetwork().callProtocolEvent(event, player);
			}
		}
	}
	
	public void updateCelestialTime(long time, float timeFactor) {
		float celestial = MathHelper.getRealCelestialAngle(time, timeFactor);
		WeatherSimulator weather = this.getWeatherSimulator();
		if (weather != null) {
			celestial = (float) ((double) celestial * (1.0d - (double) (weather.getRainStrength(timeFactor) * 5f) / 16d));
			celestial = (float) ((double) celestial * (1.0d - (double) (weather.getThunderStrength(timeFactor) * 5f) / 16d));
		}
		getOwner().setSkyLight((byte) (celestial * (float) SKY_LIGHT_RANGE + MIN_SKY_LIGHT));

		TimeUpdateEvent event = new TimeUpdateEvent(getOwner(), time);
		for (Player player : getOwner().getPlayers()) {
			player.getNetwork().callProtocolEvent(event);
		}
	}
	
	public void updatePlayer(Player player) {
		TimeUpdateEvent event = new TimeUpdateEvent(getOwner(), getSkyTime());
		player.getNetwork().callProtocolEvent(event);
		if (Weather.CLEAR != getWeather()) {
			WeatherChangeEvent weatherEvent = new WeatherChangeEvent(getOwner(), Weather.CLEAR, getWeather());
			player.getNetwork().callProtocolEvent(weatherEvent);
		}
	}
		
}
