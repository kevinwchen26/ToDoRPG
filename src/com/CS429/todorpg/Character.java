package com.CS429.todorpg;

public class Character {
	private String Name;
	
	
	private int HP;
	private int MP;
	private int Atk;
	private int PDef;
	private int MDef;
	
	public Character(String Name) {
		this.Name = Name;
		this.HP = 100;
		this.MP = 50;
		this.Atk = 10;
		this.PDef = 10;
		this.MDef = 10;
		
	};
	
	
}
