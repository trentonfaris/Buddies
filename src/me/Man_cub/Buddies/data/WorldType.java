package me.Man_cub.Buddies.data;

public enum WorldType {
	FLAT("Flat");
	private final String name;
	
	WorldType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
