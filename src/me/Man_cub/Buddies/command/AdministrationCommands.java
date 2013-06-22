package me.Man_cub.Buddies.command;

import java.util.ArrayList;

import gnu.trove.iterator.TLongIterator;
import gnu.trove.list.linked.TLongLinkedList;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.ChatStyle;
import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.entity.living.buddy.Buddy;
import me.Man_cub.Buddies.component.entity.misc.Health;
import me.Man_cub.Buddies.component.entity.player.HUD;
import me.Man_cub.Buddies.component.world.misc.Sky;
import me.Man_cub.Buddies.data.Time;
import me.Man_cub.Buddies.data.Weather;
import me.Man_cub.Buddies.data.configuration.BuddiesConfig;
import me.Man_cub.Buddies.data.configuration.OpConfiguration;
import me.Man_cub.Buddies.event.cause.HealthChangeCause;
import me.Man_cub.Buddies.input.BuddiesInputExecutor;
import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.world.generator.BuddiesGenerator;
import me.Man_cub.Buddies.world.generator.biome.BuddiesBiomes;
import me.Man_cub.Buddies.world.generator.maps.BattleHillGenerator;
import me.Man_cub.Buddies.world.lighting.BuddiesLighting;

import org.spout.api.Client;
import org.spout.api.Server;
import org.spout.api.command.CommandArguments;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.Filter;
import org.spout.api.command.annotated.Permissible;
import org.spout.api.command.filter.PlayerFilter;
import org.spout.api.component.entity.CameraComponent;
import org.spout.api.component.entity.InteractComponent;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.generator.biome.Biome;
import org.spout.api.generator.biome.BiomeGenerator;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.api.scheduler.TaskPriority;
import org.spout.api.util.concurrent.AtomicFloat;

public class AdministrationCommands {
	private final BuddiesPlugin plugin;
	private final TicksPerSecondMonitor tpsMonitor;
	
	public AdministrationCommands(BuddiesPlugin plugin) {
		this.plugin = plugin;
		tpsMonitor = new TicksPerSecondMonitor();
		plugin.setTPSMonitor(tpsMonitor);
		plugin.getEngine().getScheduler().scheduleSyncRepeatingTask(plugin, tpsMonitor, 0, 50, TaskPriority.CRITICAL);
	}
	
	@Command(aliases = {"clear", "ci"}, usage = "[player]", desc = "Clears your inventory", min = 0, max = 1)
	@Permissible("buddies.command.clear")
	@Filter(PlayerFilter.class)
	public void clear(CommandSource source, CommandArguments args) throws CommandException {
		if (args.length() == 0) {
			BuddyInventory inv = ((Player) source).get(BuddyInventory.class);
			if (inv == null) {
				source.sendMessage(plugin.getPrefix() + ChatStyle.RED + "You have no inventory!");
				return;
			}
			inv.clear();
		}
		if (args.length() == 1) {
			Player player = plugin.getEngine().getPlayer(args.getString(0), false);
			if (player == null) {
				source.sendMessage(plugin.getPrefix() + ChatStyle.RED + "Player is not online!");
				return;
			}
			BuddyInventory inv = ((Player) source).get(BuddyInventory.class);
			if (inv == null) {
				source.sendMessage(plugin.getPrefix() + ChatStyle.RED + "Player has no inventory!");
				return;
			}
			player.sendMessage(plugin.getPrefix() + ChatStyle.AQUA + "Your inventory has been cleared.");
			if (source instanceof Player && source.equals(player)) {
				return;
			}
		}
		source.sendMessage(plugin.getPrefix() + ChatStyle.AQUA + "Inventory cleared.");
	}
	
	@Command(aliases = {"give"}, usage = "[player] <block> [amount] ", desc = "Lets a player spawn items", min = 1, max = 3)
	@Permissible("buddies.command.give")
	public void give(CommandSource source, CommandArguments args) throws CommandException {
		int index = 0;
		Player player = null;

		if (args.length() != 1) {
			if (plugin.getEngine() instanceof Client) {
				throw new CommandException("You cannot search for players unless you are in server mode.");
			}
			player = plugin.getEngine().getPlayer(args.getString(index++), true);
		}

		if (player == null) {
			switch (args.length()) {
				case 3:
					throw new CommandException(args.getString(0) + " is not online.");
				case 2:
					index--;
				case 1:
					if (!(source instanceof Player)) {
						throw new CommandException("You must be a player to give yourself materials!");
					}

					player = (Player) source;
					break;
			}
		}

		Material material;
		if (args.isInteger(index)) {
			material = BuddiesMaterials.getMaterial((short) args.getInteger(index));
		} else {
			String name = args.getString(index);

			if (name.contains(":")) {
				String[] parts = args.getString(index).split(":");
				material = BuddiesMaterials.getMaterial(Short.parseShort(parts[0]), Short.parseShort(parts[1]));
			} else {
				material = Material.get(args.getString(index));
			}
		}

		if (material == null) {
			throw new CommandException(args.getString(index) + " is not a block!");
		}

		int quantity = 1;
		int data = 0;

		if (args.length() > 2) {
			quantity = args.getInteger(index++);
		}

		if (args.length() > 3) {
			data = args.getInteger(index++);
		}

		BuddyInventory inventory = player.get(BuddyInventory.class);
		if (inventory != null) {
			inventory.add(new ItemStack(material, data, quantity));
			source.sendMessage(plugin.getPrefix() + ChatStyle.GREEN + "Gave "
					+ ChatStyle.WHITE + player.getName() + " " + quantity + ChatStyle.GREEN + " of " + ChatStyle.WHITE
					+ material.getDisplayName());
		} else {
			throw new CommandException(player.getName() + " doesn't have a inventory!");
		}
	}
	
	@Command(aliases = {"deop"}, usage = "<player>", desc = "Revoke a players operator status", min = 1, max = 1)
	@Permissible("buddies.command.deop")
	public void deop(CommandSource source, CommandArguments args) throws CommandException {
		if (plugin.getEngine() instanceof Client) {
			throw new CommandException("You cannot search for players unless you are in server mode.");
		}

		String playerName = args.getString(0);
		OpConfiguration ops = BuddiesConfig.OPS;
		if (!ops.isOp(playerName)) {
			source.sendMessage(plugin.getPrefix() + playerName + ChatStyle.RED + " is not an operator!");
			return;
		}
		ops.setOp(playerName, false);
		source.sendMessage(plugin.getPrefix() + playerName + ChatStyle.RED + " had their operator status revoked!");
		Player player = plugin.getEngine().getPlayer(playerName, true);
		if (player != null && !source.equals(player)) {
			player.sendMessage(plugin.getPrefix() + ChatStyle.RED + "You had your operator status revoked!");
		}
	}

	@Command(aliases = {"op"}, usage = "<player>", desc = "Make a player an operator", min = 1, max = 1)
	@Permissible("buddies.command.op")
	public void op(CommandSource source, CommandArguments args) throws CommandException {
		if (plugin.getEngine() instanceof Client) {
			throw new CommandException("You cannot search for players unless you are in server mode.");
		}

		String playerName = args.getString(0);
		OpConfiguration ops = BuddiesConfig.OPS;
		if (ops.isOp(playerName)) {
			source.sendMessage(plugin.getPrefix() + ChatStyle.RED + playerName + " is already an operator!");
			return;
		}
		ops.setOp(playerName, true);
		source.sendMessage(plugin.getPrefix() + ChatStyle.RED + playerName + " is now an operator!");
		Player player = plugin.getEngine().getPlayer(playerName, true);
		if (player != null && !source.equals(player)) {
			player.sendMessage(plugin.getPrefix() + ChatStyle.YELLOW + "You are now an operator!");
		}
	}
	
	@Command(aliases = {"time"}, usage = "<add|set> <0-36000|day|night|dawn|dusk> [world]", desc = "Set the time of the server", min = 2, max = 3)
	@Permissible("buddies.command.time")
	public void time(CommandSource source, CommandArguments args) throws CommandException {
		long time;
		boolean relative = args.getString(0).equalsIgnoreCase("add");
		if (args.isInteger(1)) {
			time = args.getInteger(1);
		} else {
			if (relative) {
				throw new CommandException("Argument to 'add' must be an integer.");
			}
			try {
				time = Time.get(args.getString(1)).getTime();
			} catch (Exception e) {
				throw new CommandException("'" + args.getString(1) + "' is not a valid time.");
			}
		}

		World world;
		if (args.length() == 3) {
			world = plugin.getEngine().getWorld(args.getString(2));
			if (world == null) {
				throw new CommandException("'" + args.getString(2) + "' is not a valid world.");
			}
		} else if (source instanceof Player) {
			Player player = (Player) source;
			world = player.getWorld();
		} else {
			throw new CommandException("You must specify a world.");
		}

		if (!(source instanceof Player)) {
			plugin.getLogger().info("Only players can use this command without specifying a world!");
			return;
		}
		
		Sky sky = ((Player) source).getWorld().get(Sky.class);
		if (sky == null) {
			throw new CommandException("The sky for " + world.getName() + " is not available.");
		}

		sky.setSkyTime(relative ? (sky.getTime() + time) : time);
		if (plugin.getEngine() instanceof Client) {
			source.sendMessage(plugin.getPrefix() + ChatStyle.GREEN + "You set " + ChatStyle.WHITE + world.getName() + ChatStyle.GREEN + " to time: " + ChatStyle.WHITE +
					sky.getTime());
		} else {
			((Server) plugin.getEngine()).broadcastMessage(plugin.getPrefix() + ChatStyle.WHITE + world.getName() + ChatStyle.GREEN + " set to: " + ChatStyle.WHITE + sky.getTime());
		}
	}
	
	@Command(aliases = "weather", usage = "<0|1|2> (0 = CLEAR, 1 = RAIN/SNOW, 2 = THUNDERSTORM) [world]", desc = "Changes the weather", min = 1, max = 2)
	@Permissible("buddies.command.weather")
	public void weather(CommandSource source, CommandArguments args) throws CommandException {
		World world;
		if (source instanceof Player && args.length() == 1) {
			world = ((Player) source).getWorld();
		} else if (args.length() == 2) {
			world = plugin.getEngine().getWorld(args.getString(1));

			if (world == null) {
				throw new CommandException("Invalid world '" + args.getString(1) + "'.");
			}
		} else {
			throw new CommandException("You need to specify a world.");
		}

		Weather weather;
		try {
			if (args.isInteger(0)) {
				weather = Weather.get(args.getInteger(0));
			} else {
				weather = Weather.get(args.getString(0).replace("snow", "rain"));
			}
		} catch (Exception e) {
			throw new CommandException("Weather must be a mode between 0 and 2, 'CLEAR', 'RAIN', 'SNOW', or 'THUNDERSTORM'");
		}

		if (!(source instanceof Player)) {
			return;
		}
		Sky sky = ((Player) source).getWorld().get(Sky.class);
		if (sky == null) {
			throw new CommandException("The sky of world '" + world.getName() + "' is not availible.");
		}

		sky.setWeather(weather);
		String message;
		switch (weather) {
			case RAIN:
				message = plugin.getPrefix() + ChatStyle.GREEN + "Weather set to " + ChatStyle.WHITE + "RAIN/SNOW" + ChatStyle.GREEN + ".";
				break;
			default:
				message = plugin.getPrefix() + ChatStyle.GREEN + "Weather set to " + ChatStyle.WHITE + weather.name() + ChatStyle.GREEN + ".";
				break;
		}
		if (plugin.getEngine() instanceof Client) {
			source.sendMessage(message);
		} else {
			for (Player player : ((Server) plugin.getEngine()).getOnlinePlayers()) {
				if (player.getWorld().equals(world)) {
					player.sendMessage(message);
				}
			}
		}
	}
	
	@Command(aliases = {"kill"}, usage = "[player]", desc = "Kill yourself or another player", min = 0, max = 1)
	@Permissible("buddies.command.kill")
	public void kill(CommandSource source, CommandArguments args) throws CommandException {
		Player player;
		if (args.length() == 0) {
			if (!(source instanceof Player)) {
				throw new CommandException("Don't be silly...you cannot kill yourself as the console.");
			}
			player = (Player) source;
		} else {
			if (plugin.getEngine() instanceof Client) {
				throw new CommandException("You cannot search for players unless you are in server mode.");
			}
			player = plugin.getEngine().getPlayer(args.getString(0), true);
		}
		if (player == null) {
			throw new CommandException(args.getString(0) + " is not online.");
		}
		Health health = player.get(Health.class);
		if (health == null) {
			throw new CommandException(player.getDisplayName() + " can not be killed.");
		}
		health.kill(HealthChangeCause.COMMAND);
	}
	
	@Command(aliases = {"version", "vr"}, usage = "", desc = "Print out the version information for Buddies", min = 0, max = 0)
	@Permissible("buddies.command.version")
	public void getVersion(CommandSource source, CommandArguments args) {
			source.sendMessage("Buddies " + plugin.getDescription().getVersion());
			source.sendMessage("Powered by Spout " + plugin.getEngine().getVersion() + " (Implementing SpoutAPI " + plugin.getEngine().getAPIVersion() + ")");
	}
	
	@Command(aliases = {"biome"}, usage = "", desc = "Print out the name of the biome at the current location", min = 0, max = 0)
	@Permissible("buddies.command.biome")
	@Filter(PlayerFilter.class)
	public void getBiomeName(CommandSource source, CommandArguments args) throws CommandException {
		Player player = (Player) source;
		if (!(player.getScene().getPosition().getWorld().getGenerator() instanceof BiomeGenerator)) {
			throw new CommandException("This map does not appear to have any biome data.");
		}
		Point pos = player.getScene().getPosition();
		Biome biome = pos.getWorld().getBiome(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
		source.sendMessage(plugin.getPrefix() + ChatStyle.GREEN + "Current biome: " + ChatStyle.WHITE + (biome != null ? biome.getName() : "none"));
	}
	
	@Command(aliases = {"tps"}, usage = "", desc = "Print out the current engine ticks per second", min = 0, max = 0)
	@Permissible("buddies.command.tps")
	public void getTPS(CommandSource source, CommandArguments args) throws CommandException {
		source.sendMessage("TPS: " + tpsMonitor.getTPS());
		source.sendMessage("Average TPS: " + tpsMonitor.getAvgTPS());
	}
	
	@Command(aliases = {"end_game"}, usage = "", desc = "Ends the current game.", min = 0, max = 0)
	@Permissible("buddies.command.endgame")
	public void endGame(CommandSource source, CommandArguments args) throws CommandException {
		if (!(source instanceof Player)) {
			throw new CommandException("Only a player may call this command.");
		}
		Player commander = (Player) source;
		World world = commander.getWorld();
		
		if (world.getName().equals("empty")) {
			throw new CommandException("You cannot end a game if you aren't in one!");
		}
				
		//Close HUD, inventory, kill, open LobbyScreen again, and move to empty world.
		for (Player player : world.getPlayers()) {
			HUD hud = player.get(HUD.class);
			hud.closeHUD();
			BuddyInventory items = player.get(BuddyInventory.class);
			if (items == null) {
				source.sendMessage(BuddiesPlugin.getInstance().getPrefix() + ChatStyle.RED + "You have no inventory!");
				return;
			}
			items.clear();
			player.get(Health.class).kill(HealthChangeCause.COMMAND);
			player.detach(Buddy.class);
			player.detach(BuddyInventory.class);
			player.detach(CameraComponent.class);
			player.detach(Health.class);
			player.detach(InteractComponent.class);
			
			Point point = player.getScene().getPosition();
			player.getScene().setPosition(new Point(point, world));
			player.getNetworkSynchronizer().setPositionDirty();
		}
		plugin.getEngine().unloadWorld(world.getName(), false);
		
	}
	
	/**
	 * This is a temporary fix.
	 */
	@Command(aliases = {"start_game"}, usage = "", desc = "Starts a current 'game'.", min = 0, max = 0)
	@Permissible("buddies.command.startgame")
	public void startGame(CommandSource source, CommandArguments args) throws CommandException {
		if (!(source instanceof Player)) {
			throw new CommandException("Only a player may call this command.");
		}
		Player player = (Player) source;
		World currentWorld = player.getWorld();
		
		if (!currentWorld.getName().equals("empty")) {
			throw new CommandException("You cannot start a game if you are already in one!");
		}
		
		String name = null;
		
		ArrayList<World> worlds = new ArrayList<World>();
		for (World world : plugin.getEngine().getWorlds()) {
			if (world.getName().equals("empty")) {
				continue;
			} else {
				worlds.add(world);
			}
		}
		
		if (!worlds.isEmpty()) {
			for (int i = 0; i <= worlds.size() - 1; i++) {
				for (World world : worlds) {
					String worldName = world.getName();
					if (worldName.equals("map" + new Integer(i).toString())) {
						continue;
					} else {
						name = "map" + new Integer(i).toString();
					}
				}
			}
			if (name == null) {
				name = "map" + new Integer(worlds.size()).toString();
			}
		} else {
			name = "map0";
		}
		BattleHillGenerator generator = new BattleHillGenerator(BuddiesBiomes.PLAINS, 128);
		World world = plugin.getEngine().loadWorld(name, generator);
		world.addLightingManager(BuddiesLighting.BLOCK_LIGHT);
		world.addLightingManager(BuddiesLighting.SKY_LIGHT);
		
		if (world.getAge() <= 0) {
			Point spawn = ((BuddiesGenerator) world.getGenerator()).getSafeSpawn(world);
			world.setSpawnPoint(new Transform(spawn, Quaternion.IDENTITY, Vector3.ONE));
		}
		
		world.add(Sky.class);
		
		name = null;
		
		
		HUD HUD = player.add(HUD.class);
		HUD.openHUD();
		
		player.add(Buddy.class);
		player.add(BuddyInventory.class);
		player.add(CameraComponent.class);
		player.add(Health.class);
		player.add(InteractComponent.class).setRange(5f);
		
		((Client) plugin.getEngine()).getInputManager().addInputExecutor(new BuddiesInputExecutor(player));
		
		Point spawn = ((BuddiesGenerator) world.getGenerator()).getSafeSpawn(world);
		player.getScene().setPosition(new Point(spawn, world));
		player.getNetworkSynchronizer().setPositionDirty();
		
	}
	
	private static class TicksPerSecondMonitor implements Runnable, TPSMonitor {
		private static final int MAX_MEASUREMENTS = 20 * 60;
		private TLongLinkedList timings = new TLongLinkedList();
		private long lastTime = System.currentTimeMillis();
		private final AtomicFloat ticksPerSecond = new AtomicFloat(20);
		private final AtomicFloat avgTicksPerSecond = new AtomicFloat(20);

		@Override
		public void run() {
			long time = System.currentTimeMillis();
			timings.add(time - lastTime);
			lastTime = time;
			if (timings.size() > MAX_MEASUREMENTS) {
				timings.removeAt(0);
			}
			final int size = timings.size();
			if (size > 20) {
				TLongIterator i = timings.iterator();
				int count = 0;
				long last20 = 0;
				long total = 0;
				while (i.hasNext()) {
					long next = i.next();
					if (count > size - 20) {
						last20 += next;
					}
					total += next;
					count++;
				}
				ticksPerSecond.set(1000F / (last20 / 20F));
				avgTicksPerSecond.set(1000F / (total / ((float) size)));
			}
		}

		public float getTPS() {
			return ticksPerSecond.get();
		}

		public float getAvgTPS() {
			return avgTicksPerSecond.get();
		}
	}
	
	public static interface TPSMonitor {
		public float getTPS();
		public float getAvgTPS();
	}

}
