package com.CS429.todorpg;

public class Summoner extends Character {

	// Bonus passive: all summoner abilities buff a stat
	private int buffDuration = 0;

	public Summoner(String Name) {
		// TODO Auto-generated constructor stub
		super(Name);
		this.setWIS(this.getWIS() + 3);
		this.setCHA(this.getCHA() + 3);
		this.setMP(this.getMP() + (this.getINT() + this.getWIS()));

	}

	public Summoner(String name, int HP, int MP, int Level, int CON, int STR,
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
		this.setSTR(this.getSTR() + 2);
		this.setCON(this.getCON() + 2);
		this.setHP(this.getHP() + 50);
		this.setMP(this.getMP() + (this.getINT() + this.getWIS()));
		this.setDEX(this.getDEX() + 1);
		this.setCHA(this.getCHA() + 2);
		this.setINT(this.getINT() + 3);
		this.setWIS(this.getWIS() + 3);
	}

	public void attack(Character enemy) {
		int base = 2 * this.getINT();
		int reduction = this.getCON() - enemy.getCON();
		int total = base + reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);

	}

	// Knock Knock
	// High physical damage, calculated mag stats. Raises physical def. 40 mana
	public void Skill_1(Character enemy) {
		int base = 50;
		int bonus = this.getINT() / 2;
		int total = base + bonus;
		int reduction = this.getWIS() - enemy.getCON();
		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
		this.setCON(this.getCON() + 40);
		this.setMP(this.getMP() - 40);

	}

	// Stampede
	// Low damge, applies stun. Raises INT by a base amount plus 20% damage
	// done. 50 mana
	public void Skill_2(Character enemy) {
		int base = 50;
		int bonus = this.getINT() / 4;
		int total = base + bonus;
		int reduction = this.getWIS() - enemy.getWIS();
		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
		enemy.applyStun();
		this.setINT(this.getINT() + 20 + total / 5);
		this.setMP(this.getMP() - 50);

	}

	// Double Attack
	// High dmg, both physical and magical defense used to calculate dmg. Raises
	// STR and INT. 85 mana
	public void Skill_3(Character enemy) {
		int base = 25;
		int bonus = this.getINT() / 3 + this.getSTR() / 2;
		int total = base + bonus;
		int reduction = (this.getWIS() + this.getCON())
				- (enemy.getCON() + enemy.getWIS());
		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
		this.setINT(this.getINT() + 15);
		this.setSTR(this.getSTR() + 30);
		this.setMP(this.getMP() - 85);

	}

	// All together
	// Instantly applies all status effects. Moderate damage, raises all combat
	// stats. 100 mana
	public void Skill_4(Character enemy) {
		int base = 50;
		int bonus = this.getINT() / 2 + this.getSTR() / 2;
		int total = base + bonus;
		int reduction = (this.getWIS() + this.getCON())
				- (enemy.getCON() + enemy.getWIS());
		total += reduction;
		if (total < 0)
			total = 0;
		enemy.setHP(enemy.getHP() - total);
		this.setINT(this.getINT() + 10);
		this.setSTR(this.getSTR() + 10);
		this.setDEX(this.getDEX() + 10);
		this.setWIS(this.getWIS() + 10);
		this.setCON(this.getCON() + 10);

		enemy.applyBurn();
		enemy.applyFreeze();
		enemy.applyPoison();
		enemy.applySilence();
		enemy.applyStun();
		this.setMP(this.getMP() - 100);

	}

}