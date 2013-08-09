package me.man_cub.buddies.event.world;

import me.man_cub.buddies.data.Weather;

import org.spout.api.event.Cancellable;
import org.spout.api.event.HandlerList;
import org.spout.api.event.ProtocolEvent;
import org.spout.api.event.world.WorldEvent;
import org.spout.api.geo.World;

public class WeatherChangeEvent extends ProtocolEvent implements Cancellable, WorldEvent {
	private static final HandlerList handlers = new HandlerList();
	private final Weather current;
	private Weather weather;
	private final World world;

	public WeatherChangeEvent(World world, Weather current, Weather weather) {
		this.world = world;
		this.current = current;
		this.weather = weather;
	}

	/**
	 * Gets the weather at the time the event is called.
	 *
	 * @return the current weather.
	 */
	public Weather getCurrentWeather() {
		return current;
	}

	/**
	 * Gets the new weather set after the event.
	 *
	 * @return the new weather.
	 */
	public Weather getNewWeather() {
		return weather;
	}

	/**
	 * Sets the outcome of the event.
	 */
	public void setNewWeather(Weather weather) {
		this.weather = weather;
	}

	@Override
	public World getWorld() {
		return world;
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
