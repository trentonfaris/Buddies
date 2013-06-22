package me.Man_cub.Buddies.data.configuration;

import java.io.File;

import me.Man_cub.Buddies.BuddiesPlugin;

import org.spout.api.util.config.ConfigurationHolderConfiguration;
import org.spout.api.util.config.yaml.YamlConfiguration;

public class InputConfiguration extends ConfigurationHolderConfiguration {

	public InputConfiguration() {
		super(new YamlConfiguration(new File(BuddiesPlugin.getInstance().getDataFolder(), "input.yml")));
	}
}
