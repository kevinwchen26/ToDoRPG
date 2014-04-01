package com.CS429.todorpg.Class;


public class Assassin extends Character {

	// Bonus passive: all skill deal hybrid damage , bonus true damage is added
	// from CHA
	public Assassin(String Name) {
		super(Name);
		this.setDEX(this.getDEX() + 3);
		this.setWIS(this.getWIS() + 3);
		this.setMaxHP(this.getHP());
		this.setMaxMP(this.getMP());
	}

	public Assassin(String name, int HP, int MP, int Level, int CON, int STR,
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
		this.setMaxHP(HP);
		this.setMaxMP(MP);
	}

	public void LevelStats() {
		this.setSTR(this.getSTR() + 2);
		this.setCON(this.getCON() + 2);
		this.setHP(this.getHP() + 50);
		this.setMP(this.getMP() + 10);
		this.setDEX(this.getDEX() + 2);
		this.setCHA(this.getCHA() + 2);
		this.setINT(this.getINT() + 2);
		this.setWIS(this.getWIS() + 2);
	}

	// Be Quiet
	// Moderate damage, silences enemy. 35 mana.
	public boolean Skill_1(Character enemy) {
		if(this.getMP() < 35)
			return false;
		this.setMP(this.getMP() - 35);
		int base = 35;
		int bonus = this.getINT() / 3 + this.getSTR() / 3;
		int total = base + bonus;
		int reduction = (this.getWIS() + this.getCON())
				- (enemy.getCON() + enemy.getWIS());
		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total - this.getCHA());
		enemy.applySilence();
		return true;
	}

	// Focus
	// Raises CON, WIS, CHA for one turn
	public boolean Skill_2(Character enemy) {
		this.setWIS(this.getWIS() + 25);
		this.setCON(this.getCON() + 25);
		this.setCHA(this.getCHA() + 10);
		return true;
	}

	// Fist Up
	// Low damage, ignores defenses
	public boolean Skill_3(Character enemy) {
		if(this.getMP() < 20)
			return false;
		this.setMP(this.getMP() - 20);
		
		int base = 20;
		int bonus = this.getINT() / 4 + this.getSTR() / 4;
		int total = base + bonus;

		enemy.setHP(enemy.getHP() - total - this.getCHA());
		return true;
	}

	// Cauterize
	// Ult: High damage, heals for 50% dmg done and applies burn
	public boolean Skill_4(Character enemy) {
		if(this.getMP() < 100)
			return false;
		this.setMP(this.getMP() - 100);
		int base = 80;
		int bonus = this.getINT() / 2 + this.getSTR() / 2;
		int total = base + bonus;
		int reduction = (this.getWIS() + this.getCON())
				- (enemy.getCON() + enemy.getWIS());
		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total - this.getCHA());
		this.setHP(this.getHP() + total / 2);
		return true;
	}
}