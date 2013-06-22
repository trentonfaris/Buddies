package me.Man_cub.Buddies.data;

import me.Man_cub.Buddies.inventory.DropInventory;
import me.Man_cub.Buddies.inventory.EntityInventory;
import me.Man_cub.Buddies.world.LightningSimulator.Intensity;

import org.spout.api.map.DefaultedKey;
import org.spout.api.map.DefaultedKeyFactory;
import org.spout.api.map.DefaultedKeyImpl;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;

public class BuddiesData {
	//Component-specific
	public static final DefaultedKey<Integer> DEATH_TICKS = new DefaultedKeyImpl<Integer>("death_ticks", 0);
	public static final DefaultedKey<Integer> FIRE_TICKS = new DefaultedKeyImpl<Integer>("fire_ticks", 0);
	public static final DefaultedKey<Boolean> FLAMMABLE = new DefaultedKeyImpl<Boolean>("flammable", false);
	public static final DefaultedKey<Integer> TEAM = new DefaultedKeyImpl<Integer>("team", 0);
	public static final DefaultedKey<Integer> HEALTH = new DefaultedKeyImpl<Integer>("health", 1);
	public static final DefaultedKey<Integer> MAX_HEALTH = new DefaultedKeyImpl<Integer>("max_health", 1);
	public static final DefaultedKey<Vector3> MAX_SPEED = new DefaultedKeyImpl<Vector3>("max_speed", Vector3.ZERO);
	public static final DefaultedKey<Vector3> MOVEMENT_SPEED = new DefaultedKeyImpl<Vector3>("movement_speed", Vector3.ZERO);
	public static final DefaultedKey<Integer> INTERACT_REACH = new DefaultedKeyImpl<Integer>("interact_reach", 5);
	public static final DefaultedKey<Vector3> VELOCITY = new DefaultedKeyImpl<Vector3>("velocity", Vector3.ZERO);
	public static final DefaultedKey<Integer> AMMO = new DefaultedKeyImpl<Integer>("ammo", 0);
	public static final DefaultedKey<Integer> DAMAGES = new DefaultedKeyImpl<Integer>("damages", 0);
	public static final DefaultedKey<Integer> CRATES = new DefaultedKeyImpl<Integer>("crates", 0);
	//Entity data
	public static final DefaultedKey<Integer> ATTACHED_COUNT = new DefaultedKeyImpl<Integer>("attached_count", 0);
	public static final DefaultedKey<Boolean> IS_FALLING = new DefaultedKeyImpl<Boolean>("is_falling", false);
	public static final DefaultedKey<Boolean> IS_ON_GROUND = new DefaultedKeyImpl<Boolean>("is_on_Ground", true);
	public static final DefaultedKey<Boolean> IS_JUMPING = new DefaultedKeyImpl<Boolean>("is_jumping", false);
	public static final DefaultedKey<Boolean> HAS_DEATH_ANIMATION = new DefaultedKeyImpl<Boolean>("has_death_animation", true);
	public static final DefaultedKey<DropInventory> DROP_INVENTORY = new DefaultedKeyFactory<DropInventory>("DropInventory", DropInventory.class);
	public static final DefaultedKey<Boolean> IS_RIDING = new DefaultedKeyImpl<Boolean>("is_riding", false);
	public static final DefaultedKey<Float> FIRE_TICK = new DefaultedKeyImpl<Float>("is_on_fire", 0.0f);
	public static final DefaultedKey<Boolean> FIRE_HURT = new DefaultedKeyImpl<Boolean>("fire_hurt", false);
	//Buddy-specific
	public static final DefaultedKey<Boolean> IS_SPRINTING = new DefaultedKeyImpl<Boolean>("is_sprinting", false);
	public static final DefaultedKey<Boolean> IS_SNEAKING = new DefaultedKeyImpl<Boolean>("is_sneaking", false);
	public static final DefaultedKey<Boolean> IS_FLYING = new DefaultedKeyImpl<Boolean>("is_flying", false);
	public static final DefaultedKey<Boolean> CAN_FLY = new DefaultedKeyImpl<Boolean>("can_fly", false);
	public static final DefaultedKey<Boolean> GOD_MODE = new DefaultedKeyImpl<Boolean>("god_mode", false);
	public static final DefaultedKey<Number> FLYING_SPEED = new DefaultedKeyImpl<Number>("flying_speed", (byte) 12);
	public static final DefaultedKey<Number> WALKING_SPEED = new DefaultedKeyImpl<Number>("walking_speed", (byte) 25);
	//Inventory
	public static final DefaultedKey<EntityInventory> INVENTORY = new DefaultedKeyFactory<EntityInventory>("inventory", EntityInventory.class);
	//Item-specific
	public static final DefaultedKey<Number> UNCOLLECTABLE_TICKS = new DefaultedKeyImpl<Number>("uncollectable_ticks", (long) 0);
	//Body-specific
	public static final DefaultedKey<Integer> BODY_HEIGHT = new DefaultedKeyImpl<Integer>("head_height", 1);
	public static final DefaultedKey<Quaternion> BODY_ROTATION = new DefaultedKeyImpl<Quaternion>("head_rotation", Quaternion.IDENTITY);
	//Sky-specific
	public static final DefaultedKey<Weather> WEATHER = new DefaultedKeyImpl<Weather>("weather", Weather.CLEAR);
	public static final DefaultedKey<Integer> WORLD_TIME = new DefaultedKeyImpl<Integer>("timer", 0);
	public static final DefaultedKey<Number> SKY_TIME = new DefaultedKeyImpl<Number>("time_countdown", 0F);
	public static final DefaultedKey<Long> MAX_TIME = new DefaultedKeyImpl<Long>("max_time", 36000L);
	public static final DefaultedKey<Long> TIME_RATE = new DefaultedKeyImpl<Long>("time_rate", 1L);
	public static final DefaultedKey<Weather> WORLD_WEATHER = new DefaultedKeyImpl<Weather>("world_weather", Weather.CLEAR);
	public static final DefaultedKey<Weather> WORLD_FORECAST = new DefaultedKeyImpl<Weather>("world_forecast", Weather.CLEAR);
	public static final DefaultedKey<Float> WEATHER_CHANGE_TIME = new DefaultedKeyImpl<Float>("weather_change_time", 120000F);
	public static final DefaultedKey<Float> CURRENT_RAIN_STRENGTH = new DefaultedKeyImpl<Float>("current_rain_strength", 0F);
	public static final DefaultedKey<Float> PREVIOUS_RAIN_STRENGTH = new DefaultedKeyImpl<Float>("previous_rain_strength", 0F);
	public static final DefaultedKey<Intensity> STORM_INTENSITY = new DefaultedKeyImpl<Intensity>("storm_intensity", null);
	public static final DefaultedKey<Float> CURRENT_LIGHTNING_STRENGTH = new DefaultedKeyImpl<Float>("current_lightning_strength", 0F);
	public static final DefaultedKey<Float> PREVIOUS_LIGHTNING_STRENGTH = new DefaultedKeyImpl<Float>("previous_lightning_strength", 0F);
}
