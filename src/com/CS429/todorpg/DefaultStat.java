package com.CS429.todorpg;

public class DefaultStat {
	private int STRENGTH, DEXTERITY, INTELLIGENCE, WISDOM, CHARISMA, CONSTITUTION;
	
	public DefaultStat(int STRENGTH, int DEXTERITY, int INTELLIGENCE, int WISDOM, int CHARISMA, int CONSTITUTION) {
		this.STRENGTH = STRENGTH;
		this.DEXTERITY = DEXTERITY;
		this.INTELLIGENCE = INTELLIGENCE;
		this.WISDOM = WISDOM;
		this.CHARISMA = CHARISMA;
		this.CONSTITUTION = CONSTITUTION;
	}
	
	public int getStrength() { return STRENGTH; }
	public int getDexterity() { return DEXTERITY; }
	public int getIntelligence() { return INTELLIGENCE; }
	public int getWisdom() { return WISDOM; }
	public int getCharisma() { return CHARISMA; }
	public int getConstitution() { return CONSTITUTION; }
	public void setStrength(int Str) { STRENGTH = Str; }
	public void setDexterity(int Dex) { DEXTERITY = Dex; }
	public void setIntelligence(int Int) { INTELLIGENCE = Int; }
	public void setWisdom(int Wis) { WISDOM = Wis; }
	public void setCharisma(int Cha) { CHARISMA = Cha; }
	public void setConstitution(int Con) { CONSTITUTION = Con; }
}
