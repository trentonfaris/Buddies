package me.Man_cub.Buddies.data.configuration;

import java.io.File;

import org.spout.cereal.config.ConfigurationHolderConfiguration;
import org.spout.cereal.config.yaml.YamlConfiguration;

import me.Man_cub.Buddies.BuddiesPlugin;

public class InputConfiguration extends ConfigurationHolderConfiguration {

	public InputConfiguration() {
		super(new YamlConfiguration(new File(BuddiesPlugin.getInstance().getDataFolder(), "input.yml")));
	}
}
