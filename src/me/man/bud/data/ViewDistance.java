package me.man_cub.buddies.data;

public enum ViewDistance {
	FAR(0),
	NORMAL(1),
	SHORT(2),
	TINY(3);
	final byte id;
	
	ViewDistance(int id) {
		this.id = (byte) id;
	}
	
	public int getId() {
		return id;
	}
	
	public static ViewDistance byId(int id) {
		for (ViewDistance v : values()) {
			if (v.id == id) {
				return v;
			}
		}
		return null;
	}
}
