package com.cs429.todorpg.revised.test;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.TutorialBattleActivity;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class TutorialActivityTest extends ActivityInstrumentationTestCase2<TutorialBattleActivity> {

	private Solo solo;
	private TutorialBattleActivity activity;
	
	public TutorialActivityTest(){
		super(TutorialBattleActivity.class);
	}
	@Override
	public void setUp() throws Exception{
		super.setUp();
		activity = getActivity();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void test_0_InitialConditions() throws Exception{
		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "HI! Welcome to the battle tutorial.\n" + "(Tap anywhere to continue)");
	}
	
	
	public void test_1_TutStep() throws Exception{
		View view = solo.getView(R.id.tutorial_battle_view);
		solo.clickOnView(view);
		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "This is the field in which you will battle!");
	}
	public void test_2_TutStep() throws Exception{
		View view = solo.getView(R.id.tutorial_battle_view);
		solo.clickOnView(view);
		solo.clickOnView(view);

		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "On the bottom corners, you can see your player and enemy name and HP");


	}
	public void test_3_TutStep() throws Exception{
		View view = solo.getView(R.id.tutorial_battle_view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);

		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "Below you can press commands for attack and changing weapons. Changing Weapons takes up a turn!");


	}
	public void test_4_TutStep() throws Exception{
		View view = solo.getView(R.id.tutorial_battle_view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);

		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "Next we will look at status effects.");


	}
	public void test_5_TutStep() throws Exception{
		View view = solo.getView(R.id.tutorial_battle_view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "This is curse. This reduces the damage dealt by 20%.");


	}
	public void test_6_TutStep() throws Exception{
		View view = solo.getView(R.id.tutorial_battle_view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "This is poison. The character loses 10% of current HP per turn.");


	}
	public void test_7_TutStep() throws Exception{
		View view = solo.getView(R.id.tutorial_battle_view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "This is blind. The character has a 20% chance of missing their attack.");


	}
	public void test_8_TutStep() throws Exception{
		View view = solo.getView(R.id.tutorial_battle_view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "Finally, deplete your enemy's HP to win the battle!.");


	}
	public void test_9_TutStep() throws Exception{
		View view = solo.getView(R.id.tutorial_battle_view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		solo.clickOnView(view);
		TextView battleAnnouncement = (TextView) solo.getView(R.id.battle_announcement);
		assertEquals(battleAnnouncement.getText().toString(), "You have finished the tutorial, Try and defeat the boss.");


	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
}