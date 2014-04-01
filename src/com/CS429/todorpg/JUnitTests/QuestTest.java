package com.CS429.todorpg.JUnitTests;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.CS429.todorpg.Quest;
import com.CS429.todorpg.Class.Warrior;

public class QuestTest {

	@Test
	public void testCreation() {
		Warrior leader = new Warrior("Leader");
		String description = "This is a test quest";
		ArrayList<String> milestones = new ArrayList<String>();
		milestones.add("Finish problem 1");
		milestones.add("Finish problem 2");
		milestones.add("Finish problem 3");
		
		//Quest test = new Quest("Test", "Sibel Catacombs", leader, description, 4, milestones);
		//assertEquals("Leader ", test.getParty());
		
	}

}
