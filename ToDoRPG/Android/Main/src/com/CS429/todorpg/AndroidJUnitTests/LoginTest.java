package com.CS429.todorpg.AndroidJUnitTests;

import com.CS429.todorpg.Login;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class LoginTest extends ActivityInstrumentationTestCase2<Login> {

	private Solo solo;

	public LoginTest(Class<Login> activityClass) {
		super(activityClass);
	}

	public LoginTest() {
		super(Login.class);
	}

	private Login activity;

	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testJoinQuest() throws Exception {
		 solo.sendKey(Solo.MENU);
         
         solo.clickOnText("Write your ID");
         solo.clearEditText(0);
         solo.enterText(0, "failed");
         
         solo.clickOnText("write password");
         solo.clearEditText(1);
         solo.enterText(1, "b");
         
         solo.clickOnButton("Login");
         
         assertTrue(solo.waitForText("No such ID exists")); // alert dialog appeared
 		 solo.waitForDialogToClose(); /*First Test case. Tests for invalid ID*/
 		 
 		solo.clearEditText(0);
        solo.enterText(0, "1");
        
        solo.clearEditText(1);
        solo.enterText(1, "2");
        
        assertTrue(solo.waitForText("ID and Password do not match")); // alert dialog appeared
		solo.waitForDialogToClose(); /*Second Test Case. Tests for invalid ID/PW pair*/
		
		solo.clearEditText(0);
        solo.enterText(0, "1");
        
        solo.clearEditText(1);
        solo.enterText(1, "1");
        
        /*Third Test Case. Tests for valid ID/PW pair*/
		 
        solo.goBack();
        /*solo.clickOnText("Edit File Extensions");
        Assert.assertTrue(solo.searchText("application/robotium"));*/
		
	}
	
	 @Override
	   public void tearDown() throws Exception {
	        solo.finishOpenedActivities();
	  }

}
