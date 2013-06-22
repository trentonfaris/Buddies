package me.Man_cub.Buddies.material;

public interface Burnable {
	
	/**
	 * Gets the chance for this material to catch fire
	 * @return the chance, a value from 0 to 100
	 */
	public int getCombustChance();

	/**
	 * Gets the amount of power this material supplies to nearby fire<br>
	 * This material can only keep the fire alive if the value is greater than 0
	 * @return the fire power, a value from 0 to 100
	 */
	public int getBurnPower();

}
