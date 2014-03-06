package com.CS429.todorpg.Class;


public class Warrior extends Character {

	// Bonus passive: warrior gains bonus hp per level based on defense
	private int bonusCounter = 0;

	public Warrior(String Name) {
		// TODO Auto-generated constructor stub
		super(Name);
		this.setSTR(this.getSTR() + 3);
		this.setCON(this.getCON() + 3);
		this.setHP(this.getCON() * 20 + this.getHP());

	}

	public Warrior(String name, int HP, int MP, int Level, int CON, int STR,
			int DEX, int INT, int WIS, int CHA, int currentEXP, int NextLevelExp) {
		super(name);
		this.setHP(HP);
		this.setMP(MP);
		this.setLEVEL(Level);
		this.setCON(CON);
		this.setSTR(STR);
		this.setDEX(DEX);
		this.setINT(INT);
		this.setWIS(WIS);
		this.setCHA(CHA);
		this.setCurrentEXP(currentEXP);
		this.setNextLevelEXP(NextLevelExp);

	}

	public void LevelStats() {
		this.setSTR(this.getSTR() + 3);
		this.setCON(this.getCON() + 3);
		this.setHP(this.getCON() * 15 + this.getHP());
		this.setMP(this.getMP() + 10);
		this.setDEX(this.getDEX() + 2);
		this.setCHA(this.getCHA() + 2);
		this.setINT(this.getINT() + 1);
		this.setWIS(this.getWIS() + 1);

	}

	// Crushing Blow
	// High physical damage, lowers enemy def permenantly 20 mana
	public void Skill_1(Character enemy) {
		this.setMP(this.getMP() - 20);
		int base = 50;
		int bonus = this.getSTR() / 2;
		int total = base + bonus;

		// Calculate def
		int reduction = this.getCON() - enemy.getCON();

		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
		enemy.setCON(enemy.getCON() - 5);

	}

	// Overpower
	// Passive skill, gains bonus str for 5 turns, 35 mana
	public void Skill_2(Character enemy) {
		this.setSTR(this.getSTR() + 25);
		this.setMP(this.getMP() - 35);

	}

	// Sit Still
	// Stuns enemy, they skip next turn, 50 mana
	public void Skill_3(Character enemy) {
		// enemy skips turns
		enemy.applyStun();
		this.setMP(this.getMP() - 50);

	}

	// Heaven's Descent
	// Ult: High physical dmg, ignores some physical def, warrior heals for 25%
	// of damage done, 75 mana
	public void Skill_4(Character enemy) {
		int base = 100;
		int bonus = this.getSTR();
		int total = base + bonus;

		// Calculate def
		int reduction = (this.getCON() + 50) - enemy.getCON();

		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
		System.out.println(total);
		this.setHP(this.getHP() + total / 4);
		this.setMP(this.getMP() - 75);

	}

}