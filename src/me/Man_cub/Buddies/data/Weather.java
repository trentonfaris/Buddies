package me.Man_cub.Buddies.data;

public enum Weather {
	CLEAR(false, 240000, 240000),
	RAIN(true, 300000, 600000),
	THUNDERSTORM(true, 120000, 120000);
	final boolean raining;
	final int baseWeatherTime;
	final int randomWeatherTime;
	
	private Weather(boolean rain, int baseWeatherTime, int randomWeatherTime) {
		this.raining = rain;
		this.baseWeatherTime = baseWeatherTime;
		this.randomWeatherTime = randomWeatherTime;
	}
	
	public int getId() {
		return ordinal();
	}
	
	public int getBaseWeatherTime() {
		return baseWeatherTime;
	}
	
	public int getRandomWeatherTime() {
		return randomWeatherTime;
	}
	
	/**
	 * Gets if this Weather state has rain
	 * @return True if it has rain, false if not
	 */
	public boolean isRaining() {
		return this.raining;
	}

	public static Weather get(int id) {
		return values()[id];
	}

	public static Weather get(String name) {
		return valueOf(name.toUpperCase());
	}

}
