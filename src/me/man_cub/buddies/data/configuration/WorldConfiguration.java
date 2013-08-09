package me.man_cub.buddies.data.configuration;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.spout.api.geo.World;
import org.spout.cereal.config.ConfigurationException;
import org.spout.cereal.config.yaml.YamlConfiguration;

public class WorldConfiguration extends YamlConfiguration {
	private final Map<String, WorldConfigurationNode> worldNodes = new HashMap<String, WorldConfigurationNode>();
	public static WorldConfigurationNode LOBBY;
	public static WorldConfigurationNode NORMAL;
	public static WorldConfigurationNode BATTLEHILL;
	
	public WorldConfiguration(File dataFolder) {
		super(new File(dataFolder, "worlds.yml"));
		// TODO: Allow the creation of sub-sections for configuration holders
		//LOBBY = get("lobby").setDefaults("", "lobby");
		//NORMAL = get("normal").setDefaults("normal", "normal");
		BATTLEHILL = get("battlehill").setDefaults("normal", "battlehill");
	}
	
	public Collection<WorldConfigurationNode> getAll() {
		return worldNodes.values();
	}
	
	public WorldConfigurationNode get(World world) {
		return get(world.getName());
	}
	
	public WorldConfigurationNode get(String worldName) {
		synchronized (worldNodes) {
			WorldConfigurationNode node = worldNodes.get(worldName);
			if (node == null) {
				node = new WorldConfigurationNode(this, worldName);
				worldNodes.put(worldName, node);
			}
			return node;
		}
	}
	
	@Override
	public void load() throws ConfigurationException {
		super.load();
		for (WorldConfigurationNode node : getAll()) {
			node.load();
		}
	}
	
	@Override
	public void save() throws ConfigurationException {
		for (WorldConfigurationNode node : getAll()) {
			node.save();
		}
		super.save();
	}
}
