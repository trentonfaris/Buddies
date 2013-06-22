package me.Man_cub.Buddies.event.world;

import me.Man_cub.Buddies.data.Weather;

import org.spout.api.event.Cancellable;
import org.spout.api.event.HandlerList;
import org.spout.api.event.world.WorldEvent;
import org.spout.api.geo.World;
import org.spout.api.protocol.event.ProtocolEvent;

public class WeatherChangeEvent extends WorldEvent implements Cancellable, ProtocolEvent {
	private static HandlerList handlers = new HandlerList();
	private Weather current, weather;

	public WeatherChangeEvent(World world, Weather current, Weather weather) {
		super(world);
		this.current = current;
		this.weather = weather;
	}

	/**
	 * Gets the weather at the time the event is called.
	 * @return the current weather.
	 */
	public Weather getCurrentWeather() {
		return current;
	}

	/**
	 * Gets the new weather set after the event.
	 * @return the new weather.
	 */
	public Weather getNewWeather() {
		return weather;
	}

	/**
	 * Sets the outcome of the event.
	 * @param weather
	 */
	public void setNewWeather(Weather weather) {
		this.weather = weather;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
