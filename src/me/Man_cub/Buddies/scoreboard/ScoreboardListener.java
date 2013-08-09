package me.man_cub.buddies.scoreboard;

import me.man_cub.buddies.component.entity.misc.Health;
import me.man_cub.buddies.event.entity.EntityDeathEvent;
import me.man_cub.buddies.event.entity.EntityHealthChangeEvent;

import org.spout.api.Engine;
import org.spout.api.Server;
import org.spout.api.Spout;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;

public class ScoreboardListener implements Listener {
	
	@EventHandler(order = Order.LATEST)
	public void entityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		Engine engine = Spout.getEngine();

		// kill count criteria
		Object lastDamager = event.getLastDamager();
		if (lastDamager instanceof Player) {
			Player killer = (Player) lastDamager;
			String killerName = killer.getName();
			String[] criteria = {Objective.CRITERIA_TOTAL_KILL_COUNT, Objective.CRITERIA_PLAYER_KILL_COUNT};
			if (entity instanceof Player) {
				// killed a player? update total and player kill count
				evaluateCriteria(killerName, 1, true, criteria);
			} else {
				// just an entity? only update total kill count
				evaluateCriteria(killerName, 1, true, criteria[0]);
			}
		}

		if (!(entity instanceof Player) || !(engine instanceof Server)) {
			return;
		}

		// player death criteria
		Player player = (Player) entity;
		evaluateCriteria(player.getName(), 1, true, Objective.CRITERIA_DEATH_COUNT);
	}

	@EventHandler(order = Order.LATEST)
	public void entityHealth(EntityHealthChangeEvent event) {
		Entity entity = event.getEntity();
		Engine engine = Spout.getEngine();
		if (!(entity instanceof Player) || !(engine instanceof Server)) {
			return;
		}

		Player player = (Player) entity;
		evaluateCriteria(player.getName(), player.get(Health.class).getHealth() + event.getChange(), false, Objective.CRITERIA_HEALTH);
	}

	private void evaluateCriteria(String key, int value, boolean add, String... criteria) {
		for (Player player : ((Server) Spout.getEngine()).getOnlinePlayers()) {
			Scoreboard scoreboard = player.get(Scoreboard.class);
			if (scoreboard != null) {
				scoreboard.evaluateCriteria(key, value, add, criteria);
			}
		}
	}

}
