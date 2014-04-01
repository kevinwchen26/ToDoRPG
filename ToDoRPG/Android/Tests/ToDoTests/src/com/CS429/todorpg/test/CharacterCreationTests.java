package com.CS429.todorpg.test;

import com.CS429.todorpg.CharacterCreation;
import com.CS429.todorpg.R;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class CharacterCreationTests extends ActivityInstrumentationTestCase2<CharacterCreation> {

	private CharacterCreation activity;
	private Solo solo;

	public CharacterCreationTests(Class<CharacterCreation> activityClass) {
		super(activityClass);
	}

	public CharacterCreationTests() {
		super(CharacterCreation.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
		solo = new Solo(getInstrumentation(), getActivity());

	}

	/*
	 * Tests if activity is setup correctly and the initial items are all on the
	 * screen
	 */
	public void testActivitySetup() {
		ImageView image = (ImageView) activity.findViewById(R.id.skin_selection);
		assertNotNull(image);
		TextView text = (TextView) activity.findViewById(R.id.character_name_txt);
		assertNotNull(text);
		assertEquals(activity.getText(R.string.character_name), text.getText());
		EditText namebox = (EditText) activity.findViewById(R.id.character_name);
		assertNotNull(namebox);
		assertEquals(activity.getText(R.string.character_name), namebox.getHint());
		ImageButton button = (ImageButton) activity.findViewById(R.id.left_button);
		assertNotNull(button);
		button = (ImageButton) activity.findViewById(R.id.right_button);
		assertNotNull(button);
		Spinner spinner = (Spinner) activity.findViewById(R.id.character_spinner);
		assertNotNull(spinner);
	}

	/*
	 * checks to see if the skin buttons cycles correctly, including out of
	 * bounds error
	 */
	public void testSkinButtons() {
		ImageView image = (ImageView) activity.findViewById(R.id.skin_selection);
		assertNotNull(image);
		solo.clickOnImageButton(1);// tests left button
		assertEquals("dark", image.getTag());
		solo.clickOnImageButton(0);// test right button
		assertEquals("vampire", image.getTag());
		solo.clickOnImageButton(0);
		assertEquals("green", image.getTag());
		solo.clickOnImageButton(0);
		assertEquals("green", image.getTag());
		solo.clickOnImageButton(1);
		assertEquals("vampire", image.getTag());
		solo.clickOnImageButton(1);
		assertEquals("dark", image.getTag());
	}

	/*
	 * tests if warrior selection works properly on the spinner
	 */
	public void testWarriorSpinner() {
		Spinner spinner = (Spinner) activity.findViewById(R.id.character_spinner);
		assertNotNull(spinner);
		assertEquals(activity.getText(R.string.charter_spinner_prompt), spinner.getPrompt());
		solo.pressSpinnerItem(0, 1);
		assertTrue(solo.isSpinnerTextSelected("Warrior"));
		assertTrue(solo.searchText("Warrior Skill 1"));
		assertTrue(solo.searchText("Warrior Skill 2"));
		assertTrue(solo.searchText("Warrior Skill 3"));
		assertTrue(solo.searchText("Warrior Skill 4"));
	}

	/*
	 * tests if assassins selection works properly on the spinner
	 */
	public void testAssassinSpinner() {
		Spinner spinner = (Spinner) activity.findViewById(R.id.character_spinner);
		assertNotNull(spinner);
		assertEquals(activity.getText(R.string.charter_spinner_prompt), spinner.getPrompt());
		solo.pressSpinnerItem(0, 2);
		assertTrue(solo.isSpinnerTextSelected("Assassin"));
		assertTrue(solo.searchText("Assassin Skill 1"));
		assertTrue(solo.searchText("Assassin Skill 2"));
		assertTrue(solo.searchText("Assassin Skill 3"));
		assertTrue(solo.searchText("Assassin Skill 4"));
	}

	/*
	 * tests if mage selection works properly on spinner
	 */
	public void testMageSpinner() {
		Spinner spinner = (Spinner) activity.findViewById(R.id.character_spinner);
		assertNotNull(spinner);
		assertEquals(activity.getText(R.string.charter_spinner_prompt), spinner.getPrompt());
		solo.pressSpinnerItem(0, 3);
		assertTrue(solo.isSpinnerTextSelected("Mage"));
		assertTrue(solo.searchText("Mage Skill 1"));
		assertTrue(solo.searchText("Mage Skill 2"));
		assertTrue(solo.searchText("Mage Skill 3"));
		assertTrue(solo.searchText("Mage Skill 4"));
	}

	/*
	 * tests if archer selection works properly on spinner
	 */
	public void testArcherSpinner() {
		Spinner spinner = (Spinner) activity.findViewById(R.id.character_spinner);
		assertNotNull(spinner);
		assertEquals(activity.getText(R.string.charter_spinner_prompt), spinner.getPrompt());
		solo.pressSpinnerItem(0, 4);
		assertTrue(solo.isSpinnerTextSelected("Archer"));
		assertTrue(solo.searchText("Archer Skill 1"));
		assertTrue(solo.searchText("Archer Skill 2"));
		assertTrue(solo.searchText("Archer Skill 3"));
		assertTrue(solo.searchText("Archer Skill 4"));
	}

	/*
	 * Tests if summoner selection works properly on spinner
	 */
	public void testSummonerSpinner() {
		Spinner spinner = (Spinner) activity.findViewById(R.id.character_spinner);
		assertNotNull(spinner);
		assertEquals(activity.getText(R.string.charter_spinner_prompt), spinner.getPrompt());
		solo.pressSpinnerItem(0, 5);
		assertTrue(solo.isSpinnerTextSelected("Summoner"));
		assertTrue(solo.searchText("Summon Skill 1"));
		assertTrue(solo.searchText("Summon Skill 2"));
		assertTrue(solo.searchText("Summon Skill 3"));
		assertTrue(solo.searchText("Summon Skill 4"));
	}
}
