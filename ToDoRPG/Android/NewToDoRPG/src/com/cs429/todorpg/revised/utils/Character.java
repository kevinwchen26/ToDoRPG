package com.cs429.todorpg.revised.utils;

public class Character {
	private String name;
	private int gold;
	private int HP;
	private int STR;
	private int DEX;
	private int CON;

	public Character(String name, int gold) {
		this.setName(name);
		this.setGold(gold);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getSTR() {
		return STR;
	}

	public void setSTR(int sTR) {
		STR = sTR;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getCON() {
		return CON;
	}

	public void setCON(int cON) {
		CON = cON;
	}

	public int getDEX() {
		return DEX;
	}

	public void setDEX(int dEX) {
		DEX = dEX;
	}

	public boolean equals(Object o) {
		Character other = (Character) o;
		return this.name.equals(other.name) && this.gold == other.gold;
	}
}
