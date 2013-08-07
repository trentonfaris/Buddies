package me.Man_cub.Buddies.command;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.entity.living.buddy.Buddy;
import me.Man_cub.Buddies.component.entity.misc.Health;
import me.Man_cub.Buddies.component.world.misc.Dropper;
import me.Man_cub.Buddies.material.BuddiesMaterials;
import me.Man_cub.Buddies.material.block.crate.Crate;
import me.Man_cub.Buddies.material.block.crate.MegaCrate;
import me.Man_cub.Buddies.material.block.pad.blue.BluePadCenter;
import me.Man_cub.Buddies.material.item.RangedWeapon;
import me.Man_cub.Buddies.material.item.Weapon;
import me.Man_cub.Buddies.material.item.weapon.Bazooka;
import me.Man_cub.Buddies.material.item.weapon.Fireball;
import me.Man_cub.Buddies.material.item.weapon.GatlingGun;
import me.Man_cub.Buddies.material.item.weapon.Grenade;
import me.Man_cub.Buddies.material.item.weapon.Pistol;
import me.Man_cub.Buddies.material.item.weapon.Shotgun;
import me.Man_cub.Buddies.material.item.weapon.Uzi;

import org.spout.api.Client;
import org.spout.api.command.CommandArguments;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Binding;
import org.spout.api.command.annotated.CommandDescription;
import org.spout.api.command.annotated.Filter;
import org.spout.api.command.filter.PlayerFilter;
import org.spout.api.component.entity.InteractComponent;
import org.spout.api.entity.Player;
import org.spout.api.exception.ArgumentParseException;
import org.spout.api.exception.CommandException;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.discrete.Point;
import org.spout.api.input.Keyboard;
import org.spout.api.input.Mouse;
import org.spout.api.inventory.ItemStack;
import org.spout.api.material.block.BlockFace;

public class GameInputCommands {
	private final Client client;
	private final BuddiesPlugin plugin;
	
	public GameInputCommands(BuddiesPlugin plugin) {
		this.plugin = plugin;
		client = (Client) plugin.getEngine();
	}
	
	public static boolean isPressed(CommandArguments args) throws ArgumentParseException {
		return args.success("pressed", args.currentArgument("pressed").equalsIgnoreCase("+"));
	}
	
	@CommandDescription (aliases = "attack", desc = "Shoots/swings currently held weapon")
	@Binding(mouse = Mouse.BUTTON_LEFT)
	@Filter(PlayerFilter.class)
	public void attack(CommandSource source, CommandArguments args) throws CommandException {
		if (!isPressed(args)) {
			return;
		}
		args.assertCompletelyParsed();
		
		Player player = (Player) source;
		BuddyInventory inv = player.get(BuddyInventory.class);
		ItemStack item = inv.getHeldItem();
		if (item.getMaterial() instanceof Crate || item.getMaterial() instanceof MegaCrate) {
			return;
		} else {
			if (item.getMaterial() instanceof RangedWeapon) {
				RangedWeapon weapon = (RangedWeapon) item.getMaterial();
				if (weapon instanceof Bazooka) {
					Bazooka bazooka = (Bazooka) weapon;
					bazooka.shoot(player);
				} else if (weapon instanceof Fireball) {
					Fireball fireball = (Fireball) weapon;
					fireball.shoot(player);
				} else if (weapon instanceof GatlingGun) {
					GatlingGun gatlingGun = (GatlingGun) weapon;
					gatlingGun.shoot(player);
				} else if (weapon instanceof Grenade) {
					Grenade grenade = (Grenade) weapon;
					grenade.shoot(player);
				} else if (weapon instanceof Pistol) {
					Pistol pistol = (Pistol) weapon;
					pistol.shoot(player);
				} else if (weapon instanceof Shotgun) {
					Shotgun shotgun = (Shotgun) weapon;
					shotgun.shoot(player);
				} else if (weapon instanceof Uzi) {
					Uzi uzi = (Uzi) weapon;
					uzi.shoot(player);
				}
			} else {
				InteractComponent interact = player.get(InteractComponent.class);
				if (interact != null) {
					final Block target = interact.getTargetBlock();
					if (target instanceof Buddy) {
						if (item.getMaterial().equals(BuddiesMaterials.AIR)) {
							client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
								@Override
								public void run() {
									Player playerTarget = (Player) target;
									playerTarget.get(Health.class).damage(1);
								}
							});
						} else {
							final Weapon weapon = (Weapon) item.getMaterial();
							client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
								@Override
								public void run() {
									Player playerTarget = (Player) target;
									playerTarget.get(Health.class).damage(weapon.getDamage());
								}
							});
						}
					} else {
						client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
							@Override
							public void run() {
							}
						});
					}
				}
			}
		}
	}
	
	@CommandDescription (aliases = "crate", desc = "Picks up/places a crate at target location.")
	@Binding(mouse = Mouse.BUTTON_RIGHT)
	@Filter(PlayerFilter.class)
	public void crate(CommandSource source, CommandArguments args) throws CommandException {
		if (!isPressed(args)) {
			return;
		}
		args.assertCompletelyParsed();
		
		Player player = (Player) source;
		final BuddyInventory inv = ((Player) source).get(BuddyInventory.class);
		final ItemStack item = inv.getHeldItem();
		InteractComponent interact = player.get(InteractComponent.class);
		if (interact != null) {
			final Block target = interact.getTargetBlock();
			if (item.getMaterial() instanceof Crate) {
				final BlockFace clicked = interact.getTargetFace();
				client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
					@Override
					public void run() {
						inv.remove(0);
						target.translate(clicked).setMaterial(BuddiesMaterials.CRATE);
					}
				});
			} else if (item.getMaterial() instanceof MegaCrate) {
				final BlockFace clicked = interact.getTargetFace();
				client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
					@Override
					public void run() {
						inv.remove(0);
						target.translate(clicked).setMaterial(BuddiesMaterials.MEGACRATE);
					}
				});
			} else {
				if (target.getMaterial() instanceof Crate || target.getMaterial() instanceof MegaCrate) {
					client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
						@Override
						public void run() {
							inv.add(new ItemStack(target.getMaterial(), 1));
							target.setMaterial(BuddiesMaterials.AIR);
						}
					});
				}
			}
		}
	}
	
	// TODO : Finish this. Need to test
	@CommandDescription (aliases = "breakCrate", desc = "Breaks a crate at target location.")
	@Binding(Keyboard.KEY_Q)
	@Filter(PlayerFilter.class)
	public void breakCrate(CommandSource source, CommandArguments args) throws CommandException {
		if (!isPressed(args)) {
			return;
		}
		args.assertCompletelyParsed();
		
		Player player = (Player) source;
		if (player.get(BuddyInventory.class).getHeldItem().getMaterial() == BuddiesMaterials.CRATE || player.get(BuddyInventory.class).getHeldItem().getMaterial() == BuddiesMaterials.MEGACRATE) {
			BuddiesPlugin.getInstance().getEngine().getLogger().info("You can't break a crate while holding one.");
		}
		InteractComponent interact = player.get(InteractComponent.class);
		if (interact != null) {
			final Block target = interact.getTargetBlock();
			// Make them be next to it.
			if (target.getMaterial() instanceof Crate) {
				if (player.getWorld().getBlockMaterial(target.getX(), target.getY() - 1, target.getZ()) instanceof BluePadCenter) {
					Point point = checkPad(player, target.getX(), target.getY() - 1, target.getZ());
					client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
						@Override
						public void run() {
							target.setMaterial(BuddiesMaterials.AIR);
							Dropper dropper = target.getWorld().get(Dropper.class);
							dropper.setTotal(dropper.getTotal() - 1);
						}
					});
				} else {
					client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
						@Override
						public void run() {
							target.setMaterial(BuddiesMaterials.AIR);
							Dropper dropper = target.getWorld().get(Dropper.class);
							dropper.setTotal(dropper.getTotal() - 1);
						}
					});
				}
			} else if (target.getMaterial() instanceof MegaCrate) {
				if (player.getWorld().getBlockMaterial(target.getX(), target.getY() - 1, target.getZ()) instanceof BluePadCenter) {
					Point point = checkPad(player, target.getX(), target.getY() - 1, target.getZ());
					client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
						@Override
						public void run() {
							target.setMaterial(BuddiesMaterials.AIR);
							Dropper dropper = target.getWorld().get(Dropper.class);
							dropper.setTotal(dropper.getTotal() - 1);
						}
					});
				} else {
					client.getScheduler().safeRun(BuddiesPlugin.getInstance(), new Runnable() {
						@Override
						public void run() {
							target.setMaterial(BuddiesMaterials.AIR);
							Dropper dropper = target.getWorld().get(Dropper.class);
							dropper.setTotal(dropper.getTotal() - 1);
						}
					});
				}
			} else if (!(target.getMaterial() instanceof Crate) && !(target.getMaterial() instanceof MegaCrate)){
			}
		}
	}
	
	private void checkPlayer(CommandSource source) throws CommandException {
		if (!(source instanceof Player)) {
			throw new CommandException("Only players may use this command.");
		}
	}
	
	private Point checkPad(Player player, int x, int y, int z) {
		// Get the color of the player's team. Then check against that. Just using Blue for now.
		if (player.getWorld().getBlockMaterial(x - 1, y, z - 1) instanceof BluePadCenter) {
			return new Point(player.getWorld(), x - 1, y, z - 1);
		} else if (player.getWorld().getBlockMaterial(x - 1, y, z + 1) instanceof BluePadCenter) {
			return new Point(player.getWorld(), x - 1, y, z + 1);
		} else if (player.getWorld().getBlockMaterial(x + 1, y, z - 1) instanceof BluePadCenter) {
			return new Point(player.getWorld(), x + 1, y, z - 1);
		} else {
			return new Point(player.getWorld(), x + 1, y, z + 1);
		}
	}
}
