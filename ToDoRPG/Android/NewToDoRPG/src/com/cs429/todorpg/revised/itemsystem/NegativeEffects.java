package com.cs429.todorpg.revised.itemsystem;

public class NegativeEffects extends StatusEffects{
	
	private int affect;
	
	
	public NegativeEffects(String name, int affect)
	{
		super(name);
		this.affect = affect;
	}
	
	public int getAffect() {
		return affect;
	}

	public void setAffect(int affect) {
		this.affect = affect;
	}
	
	public boolean equals(Object o) {
		NegativeEffects effect = (NegativeEffects) o;
		return (this.getName().equals(effect.getName()) && this.getAffect() == effect.getAffect());
	}

}
