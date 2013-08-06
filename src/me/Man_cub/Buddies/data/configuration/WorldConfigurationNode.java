package me.Man_cub.Buddies.data.configuration;

import java.util.Map;

import org.spout.cereal.config.ConfigurationException;
import org.spout.cereal.config.ConfigurationHolder;
import org.spout.cereal.config.ConfigurationHolderConfiguration;
import org.spout.cereal.config.ConfigurationNode;
import org.spout.cereal.config.MapConfiguration;

public class WorldConfigurationNode extends ConfigurationHolderConfiguration {
	public final ConfigurationHolder LOAD = new ConfigurationHolder(true, "load");
	public final ConfigurationHolder GENERATOR  = new ConfigurationHolder("normal", "generator");
	public final ConfigurationHolder LOADED_SPAWN = new ConfigurationHolder(false, "keep-spawn-loaded");
	public final ConfigurationHolder SKY_TYPE = new ConfigurationHolder("normal", "sky-type");
	public final ConfigurationHolder MIN_Y = new ConfigurationHolder(32, "min-y");
	public final ConfigurationHolder MAX_Y = new ConfigurationHolder(224, "max-y");
	public final ConfigurationHolder STEP_Y = new ConfigurationHolder(160, "step-y");
	private final String name;
	private final WorldConfiguration parent;
	
	public WorldConfigurationNode(WorldConfiguration parent, String worldName) {
		super(new MapConfiguration(parent.getNode("worlds", worldName).getValues()));
		this.parent = parent;
		this.name = worldName;
	}
	
	public String getWorldName() {
		return this.name;
	}
	
	public WorldConfiguration getParent() {
		return this.parent;
	}
	
	public WorldConfigurationNode setDefaults(String sky, String generator) {
		this.SKY_TYPE.setDefaultValue(sky);
		this.GENERATOR.setDefaultValue(generator);
		return this;
	}
	
	public WorldConfigurationNode shouldLoad(boolean load) {
		LOAD.setDefaultValue(load);
		return this;
	}

	@Override
	public void load() throws ConfigurationException {
		this.setConfiguration(new MapConfiguration(this.getParent().getNode("worlds", this.getWorldName()).getValues()));
		super.load();
	}

	@Override
	public void save() throws ConfigurationException {
		super.save();
		ConfigurationNode node = this.getParent().getNode("worlds", this.getWorldName());
		for (Map.Entry<String, Object> entry : this.getValues().entrySet()) {
			node.getNode(entry.getKey()).setValue(entry.getValue());
		}
	}

}
