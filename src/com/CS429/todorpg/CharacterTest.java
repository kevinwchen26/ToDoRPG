package com.CS429.todorpg;

import org.junit.Test;

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
		
		Log testLog = new Log("Log");
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
		assertEquals(testWarrior.getSTR(), 17);
		assertEquals(testWarrior.getDEX(), 12);
		assertEquals(testWarrior.getCHA(), 12);
		assertEquals(testWarrior.getINT(), 11);
		assertEquals(testWarrior.getWIS(), 11);
		
		//Test multiple level gains
		testWarrior.gainEXP(1400);
		assertEquals(testWarrior.getCON(), 25);
		assertEquals(testWarrior.getSTR(), 29);
		assertEquals(testWarrior.getDEX(), 18);
		assertEquals(testWarrior.getCHA(), 18);
		assertEquals(testWarrior.getINT(), 14);
		assertEquals(testWarrior.getWIS(), 14);
		
		
	}
	
	@Test
	public void testWarriorSkills(){
		Warrior testWarrior = new Warrior("Warrior");

	}
	
	@Test
	public void testMageSkills(){
		Mage testMage = new Mage("Mage");

	}
	
	@Test
	public void testArcherSkills() {
		Archer testArcher = new Archer("Archer");

	}
	
	@Test
	public void testSummonerSkills() {
		Summoner testSummoner = new Summoner("Summoner");

	}
	
	@Test
	public void testLogSkills() {
		Log testLog = new Log("Log");
	}
}
