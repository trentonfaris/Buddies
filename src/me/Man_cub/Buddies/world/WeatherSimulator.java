package me.Man_cub.Buddies.world;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.world.misc.Sky;
import me.Man_cub.Buddies.data.BuddiesData;
import me.Man_cub.Buddies.data.Weather;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiome;

import org.spout.api.generator.biome.Biome;
import org.spout.api.geo.World;
import org.spout.api.math.GenericMath;
import org.spout.api.tickable.BasicTickable;

public class WeatherSimulator extends BasicTickable {
	private final Sky sky;
	private final AtomicBoolean forceWeatherUpdate = new AtomicBoolean(false);
	private LightningSimulator lightning;
	private final SnowSimulator snowfall;
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();

	public WeatherSimulator(Sky sky) {
		this.sky = sky;
		this.lightning = new LightningSimulator(this);
		this.snowfall = new SnowSimulator(this);
	}

	public Sky getSky() {
		return this.sky;
	}

	public World getWorld() {
		return sky.getOwner();
	}

	public Weather getCurrent() {
		return sky.getDatatable().get(BuddiesData.WORLD_WEATHER);
	}

	public Weather getForecast() {
		return sky.getDatatable().get(BuddiesData.WORLD_FORECAST);
	}

	public void setForecast(Weather weather) {
		sky.getDatatable().put(BuddiesData.WORLD_FORECAST, weather);
	}

	public void forceUpdate() {
		this.forceWeatherUpdate.lazySet(true);
	}

	/**
	 * Gets if this Weather simulator supports Lightning storms
	 * 
	 * @return True if it has lightning, False if not
	 */
	public boolean hasLightning() {
		return this.lightning != null;
	}

	/**
	 * Sets if this Weather simulator supports Lightning storms
	 * 
	 * @param hasLightning state to set to
	 */
	public void setLightning(boolean hasLightning) {
		if (hasLightning && this.lightning == null) {
			this.lightning = new LightningSimulator(this);
		} else {
			this.lightning = null;
		}
	}

	public boolean isThundering() {
		return this.getCurrent() == Weather.THUNDERSTORM;
	}

	public boolean isRaining() {
		return this.getCurrent().isRaining();
	}

	public boolean isSnowingAt(int x, int y, int z) {
		Biome biome = getWorld().getBiome(x, y, z);
		if (biome instanceof BuddiesBiome) {
			BuddiesBiome bb = (BuddiesBiome) biome;
			if (bb.getClimate().hasSnowfall()) {
				return getCurrent().isRaining() && y > getWorld().getSurfaceHeight(x, z);
			}
		}
		return false;
	}

	public boolean isRainingAt(int x, int y, int z, boolean includeSnow) {
		if (!includeSnow) {
			Biome biome = getWorld().getBiome(x, y, z);
			if (biome instanceof BuddiesBiome) {
				BuddiesBiome bb = (BuddiesBiome) biome;
				if (bb.getClimate().hasRainfall()) {
					return getCurrent().isRaining() && y > getWorld().getSurfaceHeight(x, z);
				}
			}
		}
		return false;
	}

	/**
	 * Gets the strength of the rain, which is affected by the duration
	 * 
	 * @param factor to apply to the changing states
	 * @return the strength
	 */
	public float getRainStrength(float factor) {
		final float prevRainStr = sky.getDatatable().get(BuddiesData.PREVIOUS_RAIN_STRENGTH);
		return (prevRainStr + factor * (sky.getDatatable().get(BuddiesData.CURRENT_RAIN_STRENGTH) - prevRainStr));
	}

	/**
	 * Gets the strength of the thunder storm, which is affected by the duration
	 * 
	 * @param factor to apply to the changing states
	 * @return the strength
	 */
	public float getThunderStrength(float factor) {
		return this.lightning == null ? 0.0f : this.lightning.getThunderStrength(factor) * this.getRainStrength(factor);
	}

	@Override
	public void onTick(float dt) {
		final Random random = GenericMath.getRandom();
		float secondsUntilWeatherChange = sky.getDatatable().get(BuddiesData.WEATHER_CHANGE_TIME);
		secondsUntilWeatherChange -= dt;
		if (forceWeatherUpdate.compareAndSet(true, false) || secondsUntilWeatherChange <= 0) {
			this.sky.updateWeather(getCurrent(), getForecast());
			sky.getDatatable().put(BuddiesData.WORLD_WEATHER, getForecast());
			final Weather current = getCurrent();
			Weather forecast = current;
			while (forecast == current) {
				// When Rain/Snow or Thunderstorms occur, always go to Clear after.
				if (current == Weather.RAIN || current == Weather.THUNDERSTORM) {
					forecast = Weather.CLEAR;
				} else {
					forecast = Weather.get(random.nextInt(3));
				}
				setForecast(forecast);
			}
			setForecast(forecast);
			secondsUntilWeatherChange = current.getBaseWeatherTime() + random.nextInt(current.getRandomWeatherTime());
			if (plugin.getEngine().debugMode()) {
				plugin.getEngine().getLogger().info("Weather changed to: " + current + ", next change in " + secondsUntilWeatherChange / 1000F + "s");
			}
		}
		float currentRainStrength = sky.getDatatable().get(BuddiesData.CURRENT_RAIN_STRENGTH);
		sky.getDatatable().put(BuddiesData.PREVIOUS_RAIN_STRENGTH, currentRainStrength);
		if (this.isRaining()) {
			currentRainStrength = Math.min(1.0f, currentRainStrength + 0.01f);
		} else {
			currentRainStrength = Math.max(0.0f, currentRainStrength - 0.01f);
		}
		sky.getDatatable().put(BuddiesData.CURRENT_RAIN_STRENGTH, currentRainStrength);
		if (hasLightning()) {
			lightning.onTick(dt);
		}
		if (getCurrent().isRaining()) {
			snowfall.onTick(dt);
		}
		sky.getDatatable().put(BuddiesData.WEATHER_CHANGE_TIME, secondsUntilWeatherChange);
	}

	@Override
	public boolean canTick() {
		return true;
	}
}
