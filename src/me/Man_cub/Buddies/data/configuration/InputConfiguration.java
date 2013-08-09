package me.man_cub.buddies.data.configuration;

import java.io.File;

import org.spout.cereal.config.ConfigurationHolderConfiguration;
import org.spout.cereal.config.yaml.YamlConfiguration;

import me.man_cub.buddies.BuddiesPlugin;

public class InputConfiguration extends ConfigurationHolderConfiguration {

	public InputConfiguration() {
		super(new YamlConfiguration(new File(BuddiesPlugin.getInstance().getDataFolder(), "input.yml")));
	}
}
