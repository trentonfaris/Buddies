package me.man_cub.buddies.data;

public enum Time {
	DAWN(0),
	DAY(9000),
	DUSK(18000),
	NIGHT(27000);
	private long time;

	private Time(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public static Time get(String name) {
		return valueOf(name.toUpperCase());
	}

}
