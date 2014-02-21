package com.CS429.todorpg;

import android.annotation.SuppressLint;

public class CharacterOperations {
	
	public static Character createCharacter(String Class, String Name) {
		if(Class == "Warrior")
			return new Warrior(Name);
		else if(Class == "Archer")
			return new Archer(Name);
		else if(Class == "Mage")
			return new Mage(Name);
		else if(Class == "Summoner")
			return new Summoner(Name);
		else if(Class == "Log")
			return new Log(Name);
		else
			return null;
	
	}
	
	public static Character pullCharacter(String Class, String Name, int HP, int MP, int Level, int CON, int STR, int DEX, int INT, int WIS, int CHA, int currentEXP, int nextLevelEXP)
	{
		if(Class == "Warrior")
			return new Warrior(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Class == "Archer")
			return new Archer(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Class == "Mage")
			return new Mage(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Class == "Summoner")
			return new Summoner(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else if(Class == "Log")
			return new Log(Name, HP, MP, Level, CON, STR, DEX, INT, WIS, CHA, currentEXP, nextLevelEXP);
		else
			return null;
		
	}
}
