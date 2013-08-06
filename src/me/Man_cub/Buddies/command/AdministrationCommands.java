package me.Man_cub.Buddies.command;

import gnu.trove.iterator.TLongIterator;
import gnu.trove.list.linked.TLongLinkedList;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.ChatStyle;
import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.entity.misc.Health;
import me.Man_cub.Buddies.component.world.misc.Sky;
import me.Man_cub.Buddies.data.Time;
import me.Man_cub.Buddies.data.Weather;
import me.Man_cub.Buddies.data.configuration.BuddiesConfig;
import me.Man_cub.Buddies.data.configuration.OpConfiguration;
import me.Man_cub.Buddies.event.cause.HealthChangeCause;

import org.spout.api.Client;
import org.spout.api.Engine;
import org.spout.api.Server;
import org.spout.api.command.CommandArguments;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.CommandDescription;
import org.spout.api.command.annotated.Filter;
import org.spout.api.command.annotated.Permissible;
import org.spout.api.command.filter.PlayerFilter;
import org.spout.api.entity.Player;
import org.spout.api.exception.ArgumentParseException;
import org.spout.api.exception.CommandException;
import org.spout.api.generator.biome.Biome;
import org.spout.api.generator.biome.BiomeGenerator;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.inventory.Inventory;
import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
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
	
	private Engine getEngine() {
		return plugin.getEngine();
	}
	
	@CommandDescription (aliases = "clear", usage = "[player] [item] [data]", desc = "Clears your inventory")
	@Permissible("buddies.command.clear")
	public void clear(CommandSource source, CommandArguments args) throws CommandException {
		Player player = args.popPlayerOrMe("player", source);
		Material filter = BuddiesArgumentTypes.popMaterial("filter", args);
		Integer data = args.popInteger("data");
		args.assertCompletelyParsed();
		
		BuddyInventory inv = player.get(BuddyInventory.class);
		if (inv == null) {
			throw new CommandException(player.getName() + " doesn't have an inventory!");
		} else {
			//Count the items and clear the inventory
			Inventory inventory = inv.getInv();
			int cleared = 0;
			for (int i = 0; i < inventory.size(); i++) {
				if (inventory.get(i) != null && (filter == null || inventory.get(i).isMaterial(filter)) && (data == null || inventory.get(i).getData() == data)) {
					cleared += inventory.get(i).getAmount();
					inventory.set(i, null);
				}
			}
			
			if (cleared == 0) {
				throw new CommandException("Inventory is already empty.");
			}
			
			source.sendMessage(plugin.getPrefix() + ChatStyle.GREEN + "Cleared the inventory of " + ChatStyle.WHITE + player.getName() + ChatStyle.GREEN + ", removing " + ChatStyle.WHITE + cleared + ChatStyle.GREEN + " items.");
			
		}
	}
	
	@CommandDescription (aliases = "give", usage = "[player] <block> [amount] [data]", desc = "Lets a player spawn items")
	@Permissible("buddies.command.give")
	public void give(CommandSource source, CommandArguments args) throws CommandException {
		Player player;
		if (args.length() != 1) {
			player = args.popPlayerOrMe("player", source);
		} else {
			player = args.checkPlayer(source);
		}
		
		Material material = BuddiesArgumentTypes.popMaterial("material", args);
		int quantity = args.popInteger("quantity");
		int data = args.popInteger("data");
		args.assertCompletelyParsed();
		
		BuddyInventory inventory = player.get(BuddyInventory.class);
		if (inventory != null) {
			inventory.add(new ItemStack(material, data, quantity));
			source.sendMessage(plugin.getPrefix() + ChatStyle.GREEN + "Gave " + ChatStyle.WHITE + player.getName() + " " + quantity + ChatStyle.GREEN + " of " + ChatStyle.WHITE + material.getDisplayName());
		} else {
			throw new CommandException(player.getName() + " doesn't have an inventory!");
		}
	}
	
	@CommandDescription (aliases = "deop", usage = "<player>", desc = "Revoke a players operator status")
	@Permissible("buddies.command.deop")
	public void deop(CommandSource source, CommandArguments args) throws CommandException {
		String playerName = args.popString("player");
		args.assertCompletelyParsed();
		
		OpConfiguration ops = BuddiesConfig.OPS;
		if (!ops.isOp(playerName)) {
			source.sendMessage(plugin.getPrefix() + playerName + ChatStyle.RED + " is not an operator!");
			return;
		}
		
		ops.setOp(playerName, false);
		source.sendMessage(plugin.getPrefix() + playerName + ChatStyle.RED + " had their operator status revoked!");
		if (getEngine() instanceof Server) {
			Player player = ((Server) getEngine()).getPlayer(playerName, true);
			if (player != null && !source.equals(player)) {
				player.sendMessage(plugin.getPrefix() + ChatStyle.RED + "You had your operator status revoked!");
			}
		}
	}

	@CommandDescription (aliases = "op", usage = "<player>", desc = "Make a player an operator")
	@Permissible("buddies.command.op")
	public void op(CommandSource source, CommandArguments args) throws CommandException {
		String playerName = args.popString("player");
		args.assertCompletelyParsed();
		
		OpConfiguration ops = BuddiesConfig.OPS;
		if (ops.isOp(playerName)) {
			source.sendMessage(plugin.getPrefix() + ChatStyle.RED + playerName + " is already an operator!");
			return;
		}
		
		ops.setOp(playerName, true);
		source.sendMessage(plugin.getPrefix() + ChatStyle.RED + playerName + " is now an operator!");
		if (getEngine() instanceof Server) {
			Player player = ((Server) getEngine()).getPlayer(playerName, true);
			if (player != null && !source.equals(player)) {
				player.sendMessage(plugin.getPrefix() + ChatStyle.YELLOW + "You are now an operator!");
			}
		}
	}
	
	@CommandDescription (aliases = "time", usage = "<add|set> <0-24000|day|night|dawn|dusk> [world]", desc = "Set the time of the server")
	@Permissible("buddies.command.time")
	public void time(CommandSource source, CommandArguments args) throws CommandException {
		String relativeCheck = args.currentArgument("relative");
		boolean relative;
		if (relativeCheck.equalsIgnoreCase("add") || relativeCheck.equalsIgnoreCase("set")) {
			relative = args.success("relative", relativeCheck.equalsIgnoreCase("add"));
		} else {
			throw args.failure("relative", "Value must be either 'add' or 'set'", true, "add", "set");
		}
		
		long time;
		try {
			time = args.popInteger("time");
		} catch (ArgumentParseException ex) {
			if (!args.hasMore() || relative) {
				throw ex;
			} else {
				try {
					time = args.success("time", Time.get(args.currentArgument("time")).getTime());
				} catch (IllegalArgumentException e) {
					throw args.failure("time", args.currentArgument("time") + " is not a valid time.", true);
				}
			}
		}
		
		World world = args.popWorld("world", source);
		args.assertCompletelyParsed();
		
		Sky sky = world.get(Sky.class);
		if (sky == null) {
			throw new CommandException("The sky for " + world.getName() + " is not available.");
		}
		sky.setSkyTime(relative ? (sky.getTime() + time) : time);
		if (getEngine() instanceof Client) {
			source.sendMessage(plugin.getPrefix() + ChatStyle.GREEN + "You set " + ChatStyle.WHITE + world.getName() + ChatStyle.GREEN + " to time: " + ChatStyle.WHITE + sky.getTime());
		} else {
			((Server) getEngine()).broadcastMessage(plugin.getPrefix() + ChatStyle.WHITE + world.getName() + ChatStyle.GREEN + " set to: " + ChatStyle.WHITE + sky.getTime());
		}
	}
	
	@CommandDescription (aliases = "weather", usage = "<0|1|2> (0 = CLEAR, 1 = RAIN/SNOW, 2 = THUNDERSTORM) [world]", desc = "Changes the weather")
	@Permissible("buddies.command.weather")
	public void weather(CommandSource source, CommandArguments args) throws CommandException {
		Weather weather = args.popEnumValue("weather", Weather.class);
		World world = args.popWorld("world", source);
		args.assertCompletelyParsed();
		
		Sky sky = world.get(Sky.class);
		if (sky == null) {
			throw new CommandException("The sky of world '" + world.getName() + "' is not available.");
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
		
		if (getEngine() instanceof Client) {
			source.sendMessage(message);
		} else {
			for (Player player : ((Server) getEngine()).getOnlinePlayers()) {
				if (player.getWorld().equals(world)) {
					player.sendMessage(message);
				}
			}
		}
	}
	
	@CommandDescription (aliases = "kill", usage = "[player]", desc = "Kill yourself or another player")
	@Permissible("buddies.command.kill")
	public void kill(CommandSource source, CommandArguments args) throws CommandException {
		Player player = args.popPlayerOrMe("player", source);
		args.assertCompletelyParsed();
		
		Health health = player.get(Health.class);
		if (health == null) {
			throw new CommandException(player.getDisplayName() + " can not be killed.");
		}
		health.kill(HealthChangeCause.COMMAND);
	}
	
	@CommandDescription (aliases = "version", usage = "", desc = "Print out the version information for Buddies")
	@Permissible("buddies.command.version")
	public void getVersion(CommandSource source, CommandArguments args) throws ArgumentParseException {
			args.assertCompletelyParsed();
			source.sendMessage("Buddies " + plugin.getDescription().getVersion() + " (Implementing Buddies protocol v" + plugin.getDescription().getData("protocol") + ")");
			source.sendMessage("Powered by Spout " + getEngine().getVersion() + " (Implementing SpoutAPI " + getEngine().getAPIVersion() + ")");
	}
	
	@CommandDescription (aliases = "biome", usage = "", desc = "Print out the name of the biome in the current world")
	@Permissible("buddies.command.biome")
	@Filter(PlayerFilter.class)
	public void getBiomeName(CommandSource source, CommandArguments args) throws CommandException {
		args.assertCompletelyParsed();
		
		Player player = (Player) source;
		if (!(player.getPhysics().getPosition().getWorld().getGenerator() instanceof BiomeGenerator)) {
			throw new CommandException("This map does not appear to have any biome data.");
		}
		Point pos = player.getPhysics().getPosition();
		Biome biome = pos.getWorld().getBiome(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
		source.sendMessage(plugin.getPrefix() + ChatStyle.GREEN + "Current biome: " + ChatStyle.WHITE + (biome != null ? biome.getName() : "none"));
	}
	
	@CommandDescription (aliases = "tps", usage = "", desc = "Print out the current engine ticks per second")
	@Permissible("buddies.command.tps")
	public void getTPS(CommandSource source, CommandArguments args) throws ArgumentParseException {
		args.assertCompletelyParsed();
		source.sendMessage("TPS: " + tpsMonitor.getTPS());
		source.sendMessage("Average TPS: " + tpsMonitor.getAvgTPS());
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
