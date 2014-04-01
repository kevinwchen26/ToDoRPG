package com.CS429.todorpg.Class;

public class Enemy extends Character {
	
	private int exp;
	public Enemy(String Name) {
		super(Name);
		exp = 100;
	}
	
	public int getExp(){
		return exp;
	}
	

}
