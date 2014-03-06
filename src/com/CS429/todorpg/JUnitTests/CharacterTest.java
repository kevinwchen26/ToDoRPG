package com.CS429.todorpg.JUnitTests;

import org.junit.Test;

import com.CS429.todorpg.CharacterOperations;
import com.CS429.todorpg.Class.Archer;
import com.CS429.todorpg.Class.Assassin;
import com.CS429.todorpg.Class.Character;
import com.CS429.todorpg.Class.Mage;
import com.CS429.todorpg.Class.Summoner;
import com.CS429.todorpg.Class.Warrior;

import junit.framework.TestCase;

public class CharacterTest extends TestCase {

	@Test
	public void testCharacterCreation() {
		Warrior testWarrior = new Warrior("Warrior");
		assertEquals(testWarrior.getName(), "Warrior");
		assertEquals(testWarrior.getSTR(), 13);
		assertEquals(testWarrior.getCON(), 13);
		
		Mage testMage = new Mage("Mage");
		assertEquals(testMage.getName(), "Mage");
		assertEquals(testMage.getWIS(), 13);
		assertEquals(testMage.getINT(), 13);
		
		Archer testArcher = new Archer("Archer");
		assertEquals(testArcher.getName(), "Archer");
		assertEquals(testArcher.getDEX(), 13);
		assertEquals(testArcher.getCHA(), 13);
		
		Summoner testSummoner = new Summoner("Summoner");
		assertEquals(testSummoner.getName(), "Summoner");
		assertEquals(testSummoner.getWIS(), 13);
		assertEquals(testSummoner.getCHA(), 13);
		
		Assassin testLog = new Assassin("Log");
		assertEquals(testLog.getName(), "Log");
		assertEquals(testLog.getDEX(), 13);
		assertEquals(testLog.getWIS(), 13);
		
		//Test character operations creation 
		Character testCreation = CharacterOperations.createCharacter("Warrior", "Test");
		assertTrue(testCreation instanceof Warrior);
		assertEquals("Test", testCreation.getName());
	}
	
	@Test
	public void testLeveling() {
		Character testCharacter = new Character("Test");
		assertEquals(testCharacter.getcurrentEXP(), 0);
		assertEquals(testCharacter.getLEVEL(), 1);

		testCharacter.gainEXP(100);
		assertEquals(testCharacter.getLEVEL(), 2);
		assertEquals(testCharacter.getnextLevelEXP(), 200);
		
		testCharacter.gainEXP(250);
		assertEquals(testCharacter.getLEVEL(), 3);
		assertEquals(testCharacter.getcurrentEXP(), 50);
		assertEquals(testCharacter.getnextLevelEXP(), 400);
		
		
		//Test character leveling up multiple times
		Character testCharacter2 = new Character("Test");
		testCharacter2.gainEXP(1500);
		assertEquals(testCharacter2.getLEVEL(), 5);
		assertEquals(testCharacter2.getcurrentEXP(), 0);
		assertEquals(testCharacter2.getnextLevelEXP(), 1600);

		
	}
	
	@Test
	public void testLevelingStats() {
		Warrior testWarrior = new Warrior("Warrior");
		testWarrior.gainEXP(100);
		assertEquals(testWarrior.getCON(), 16);
		assertEquals(testWarrior.getSTR(), 16);
		assertEquals(testWarrior.getDEX(), 12);
		assertEquals(testWarrior.getCHA(), 12);
		assertEquals(testWarrior.getINT(), 11);
		assertEquals(testWarrior.getWIS(), 11);
		
		//Test multiple level gains
		testWarrior.gainEXP(1400);
		assertEquals(testWarrior.getCON(), 25);
		assertEquals(testWarrior.getSTR(), 25);
		assertEquals(testWarrior.getDEX(), 18);
		assertEquals(testWarrior.getCHA(), 18);
		assertEquals(testWarrior.getINT(), 14);
		assertEquals(testWarrior.getWIS(), 14);
		
		
	}
	
	@Test
	public void testWarriorSkills(){
		Warrior testWarrior = new Warrior("Warrior");
		Character enemy = new Character("Enemey");
		
		testWarrior.attack(enemy);
		assertEquals(enemy.getHP(), 71);
		
		testWarrior.Skill_1(enemy);
		assertEquals(enemy.getHP(), 12);
		assertEquals(enemy.getCON(), 5);
		
		testWarrior.Skill_2(enemy);
		assertEquals(testWarrior.getSTR(), 38);
		
		testWarrior.Skill_3(enemy);
		assertTrue(enemy.isStun());
		
		enemy.setHP(300);
		testWarrior.Skill_4(enemy);
		assertEquals(enemy.getHP(), 104);
		assertEquals(testWarrior.getHP(), 409);
	}
	
	@Test
	public void testMageSkills(){
		Mage testMage = new Mage("Mage");
		Character enemy = new Character("Enemey");
		
		testMage.Skill_1(enemy);
		assertEquals(enemy.getHP(), 56);
		
		testMage.Skill_2(enemy);
		assertTrue(enemy.isSilence());
		assertEquals(testMage.getMP(), 46);
		
		testMage.Skill_3(enemy);
		assertEquals(enemy.getHP(), 25);
		assertTrue(enemy.isFrozen());
		
		enemy.setHP(300);
		testMage.Skill_4(enemy);
		assertEquals(enemy.getHP(), 114);
		assertEquals(enemy.getWIS(), -60);


	}
	
	@Test
	public void testArcherSkills() {
		Archer testArcher = new Archer("Archer");
		Character enemy = new Character("Enemey");
		
		testArcher.attack(enemy);
		
		enemy.setHP(100);
		
		testArcher.Skill_1(enemy);
		assertEquals(enemy.getHP(), 80);
		
		testArcher.Skill_2(enemy);
		assertEquals(enemy.getHP(), 37);
		assertTrue(enemy.isPoison());
		
		enemy.setHP(100);

		testArcher.Skill_3(enemy);
		assertEquals(enemy.getHP(), 47);
		
		enemy.setHP(300);

		testArcher.Skill_4(enemy);
		assertEquals(enemy.getHP(), 48);
		assertTrue(enemy.isStun());

	}
	
	@Test
	public void testSummonerSkills() {
		Summoner testSummoner = new Summoner("Summoner");
		Character enemy = new Character("Enemey");
		
		testSummoner.Skill_1(enemy);
		assertEquals(enemy.getHP(), 42);
		assertEquals(testSummoner.getCON(), 50);
				
		enemy.setHP(100);

		testSummoner.Skill_2(enemy);
		assertEquals(enemy.getHP(), 45);
		assertEquals(testSummoner.getINT(), 41);
		
		enemy.setHP(100);

		testSummoner.Skill_3(enemy);
		assertEquals(enemy.getHP(), 14);
		assertEquals(testSummoner.getINT(), 56);
		assertEquals(testSummoner.getSTR(), 40);
		
		enemy.setHP(300);

		testSummoner.Skill_4(enemy);
		assertEquals(enemy.getHP(), 139);
		assertTrue(enemy.isBurn());
		assertTrue(enemy.isFrozen());
		assertTrue(enemy.isPoison());
		assertTrue(enemy.isSilence());
		assertTrue(enemy.isStun());
		
		assertEquals(testSummoner.getINT(), 66);
		assertEquals(testSummoner.getSTR(), 50);
		assertEquals(testSummoner.getDEX(), 20);
		assertEquals(testSummoner.getWIS(), 23);
		assertEquals(testSummoner.getCON(), 60);
	}
	
	@Test
	public void testLogSkills() {
		Assassin testLog = new Assassin("Log");
		Character enemy = new Character("Enemey");
		
		testLog.Skill_1(enemy);
		assertEquals(enemy.getHP(), 46);
		assertTrue(enemy.isSilence());
		
		testLog.Skill_2(enemy);
		assertEquals(testLog.getCON(), 35);
		assertEquals(testLog.getWIS(), 38);
		assertEquals(testLog.getCHA(), 20);
		
		enemy.setHP(100);

		testLog.Skill_3(enemy);
		assertEquals(enemy.getHP(), 56);

		enemy.setHP(300);
		testLog.Skill_4(enemy);
		assertEquals(enemy.getHP(), 137);
		assertEquals(testLog.getHP(), 171);

	}
}
