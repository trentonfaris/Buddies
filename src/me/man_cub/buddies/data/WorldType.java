package me.man_cub.buddies.data;

public enum WorldType {
	DEFAULT("Default"),
	FLAT("Flat");
	private final String name;
	
	WorldType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
