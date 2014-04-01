package com.CS429.todorpg.test;

import com.CS429.todorpg.CharacterCreation;
import com.CS429.todorpg.R;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageView;
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

	public void testActivitySetup() {
		ImageView image = (ImageView) activity.findViewById(R.id.skin_selection);
		assertNotNull(image);
		TextView text = (TextView)activity.findViewById(R.id.character_name_txt);
		assertNotNull(text);
		assertEquals(text.getText(),activity.getText(R.string.character_name));
		EditText namebox =(EditText)activity.findViewById(R.id.character_name);
		assertNotNull(namebox);
		assertEquals(namebox.getHint(),R.string.character_name);
	}

	public void testSkinButtons() {
		ImageView image = (ImageView) activity.findViewById(R.id.skin_selection);
		assertNotNull(image);
		solo.clickOnImageButton(0);
		assertEquals(image.getTag(),"vampire");
	}

}
