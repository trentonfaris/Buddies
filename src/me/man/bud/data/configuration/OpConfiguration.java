package me.man_cub.buddies.data.configuration;

import java.io.File;
import java.util.List;

import org.spout.cereal.config.ConfigurationException;
import org.spout.cereal.config.yaml.YamlConfiguration;

public class OpConfiguration {
	private final YamlConfiguration config;
	
	/**
	 * Creates a new OpConfiguration and instantiates a YamlConfiguration<br>
	 * containing the operators.
	 */
	public OpConfiguration(File dataFolder) {
		config = new YamlConfiguration(new File(dataFolder, "ops.yml"));
	}

	/**
	 * Gets the operators.
	 * @return The operator-names as a List.
	 */
	public List<String> getOps() {
		return config.getNode("ops").getStringList();
	}

	/**
	 * Sets a player as an operator.
	 * @param playerName Player to op/deop.
	 * @param op If true, the player gets opped, if not, then deopped.
	 * @return true if no exception occured during saving the config, false if
	 *         one occured.
	 */
	public boolean setOp(String playerName, boolean op) {
		List<String> list = getOps();
		if (op) {
			list.add(playerName.toLowerCase());
		} else {
			list.remove(playerName.toLowerCase());
		}

		config.getNode("ops").setValue(list);

		try {
			this.save();
			return true;
		} catch (ConfigurationException e) {
			return false;
		}
	}

	/**
	 * Checks wether the passed player is an operator.
	 * @param playerName The name of the player to check.
	 * @return true if player is op, false when not.
	 */
	public boolean isOp(String playerName) {
		return getOps().contains(playerName.toLowerCase());
	}

	/**
	 * Saves the YamlConfiguration containing the operators.
	 * @throws ConfigurationException
	 */
	public void save() throws ConfigurationException {
		config.save();
	}

	/**
	 * Loads the config-values.
	 * @throws ConfigurationException
	 */
	public void load() throws ConfigurationException {
		config.load();
	}

}
