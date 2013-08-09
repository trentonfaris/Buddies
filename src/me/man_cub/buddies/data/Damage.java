package me.man_cub.buddies.data;

public class Damage {
	private int amount;
	//private StatusEffectConatiner effect;
	
	public Damage() {
		setAmount(0);
		//setEffect(null);
	}
	
	public Damage(int amount) {
		this.setAmount(amount);
		//this.setEffect(effect);
	}
	
	/*
	public Damage(int amount, StatusEffectContainer effect) {
		this.setAmount(amount);
		this.setEffect(effect);
	}*/
	 
	
	
	public int getAmount() {
		return amount;
	}
	
	public Damage setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	/*
	public StatusEffectContainer getEffect() {
		return effect;
	}
	
	public Damage setEffect(StatusEffectContainer effect) {
		this.effect = effect;
		return this;
	}*/

}
