package me.Man_cub.Buddies.data.configuration;

import java.io.File;
import java.util.logging.Level;

import org.spout.cereal.config.ConfigurationException;
import org.spout.cereal.config.ConfigurationHolder;
import org.spout.cereal.config.ConfigurationHolderConfiguration;
import org.spout.cereal.config.yaml.YamlConfiguration;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiomes;

public class BuddiesConfig extends ConfigurationHolderConfiguration {
	//General
	public static final ConfigurationHolder SPAWN_RADIUS = new ConfigurationHolder(1, "general", "spawn-radius");
	public static final ConfigurationHolder CHUNK_INIT = new ConfigurationHolder("client", "general", "chunk-init");
	//Messages
	public static final ConfigurationHolder MOTD = new ConfigurationHolder("Official Buddies Server", "message", "motd");
	public static final ConfigurationHolder OUTDATED_SERVER_MESSAGE = new ConfigurationHolder("Outdated server!", "message", "outdated-server");
	public static final ConfigurationHolder OUTDATED_CLIENT_MESSAGE = new ConfigurationHolder("Outdated client!", "message", "outdated-client");
	//Component-specific
	public static final ConfigurationHolder ITEM_PICKUP_RANGE = new ConfigurationHolder(2, "component", "item-pickup-range");
	public static final ConfigurationHolder ITEM_SPAWN_TIME = new ConfigurationHolder(300, "component", "item-spawn-time");
	//Encryption
	public static final ConfigurationHolder ENCRYPT_KEY_ALGORITHM = new ConfigurationHolder("RSA", "encrypt", "key-algorithm");
	public static final ConfigurationHolder ENCRYPT_KEY_SIZE = new ConfigurationHolder(1024, "encrypt", "key-size");
	public static final ConfigurationHolder ENCRYPT_KEY_PADDING = new ConfigurationHolder("PKCS1", "encrypt", "key-padding");
	public static final ConfigurationHolder ENCRYPT_STREAM_ALGORITHM = new ConfigurationHolder("AES", "encrypt", "stream-algorithm");
	public static final ConfigurationHolder ENCRYPT_STREAM_WRAPPER = new ConfigurationHolder("CFB8", "encrypt", "stream-wrapper");
	//Protocol
	public static final ConfigurationHolder USERNAME = new ConfigurationHolder("Buddy", "client", "username");
	public static final ConfigurationHolder PASSWORD = new ConfigurationHolder("Buddy", "client", "password");
	public static final ConfigurationHolder HOST = new ConfigurationHolder("127.0.0.1", "server", "host");
	public static final ConfigurationHolder PORT = new ConfigurationHolder(25565, "server", "port");
	//Chunk-cache
	public static final ConfigurationHolder USE_CHUNK_CACHE = new ConfigurationHolder(true, "cache", "chunks");
	//sub-configs
	public static final OpConfiguration OPS = new OpConfiguration(BuddiesPlugin.getInstance().getDataFolder());
	public static final WorldConfiguration WORLDS = new WorldConfiguration(BuddiesPlugin.getInstance().getDataFolder());
	public static final YamlConfiguration BIOMES = new YamlConfiguration(new File(BuddiesPlugin.getInstance().getDataFolder(), "biomes.yml"));
	public static final InputConfiguration INPUT = new InputConfiguration();
	
	public BuddiesConfig(File dataFolder) {
		super(new YamlConfiguration(new File(dataFolder, "config.yml")));
	}
	
	@Override
	public void load() {
		try {
			BuddiesBiomes.load(BIOMES);
			BIOMES.save();
			OPS.load();
			OPS.save();
			WORLDS.load();
			WORLDS.save();
			INPUT.load();
			INPUT.save();
			super.load();
			super.save();
		} catch (ConfigurationException e) {
			BuddiesPlugin.getInstance().getLogger().log(Level.WARNING, "Error loading Buddies configuration: ", e);
		}
	}
	
	@Override
	public void save() {
		try {
			BuddiesBiomes.save(BIOMES);
			OPS.save();
			WORLDS.save();
			INPUT.save();
			super.save();
		} catch (ConfigurationException e) {
			BuddiesPlugin.getInstance().getLogger().log(Level.WARNING, "Error saving Buddies configuration: ", e);
		}
	}

}
