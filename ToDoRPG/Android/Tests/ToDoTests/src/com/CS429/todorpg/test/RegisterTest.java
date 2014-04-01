package com.CS429.todorpg.test;

import com.CS429.todorpg.Register;
import com.CS429.todorpg.R;
import com.CS429.todorpg.StartMain;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.*;
import com.robotium.solo.Solo;

import android.app.Instrumentation.ActivityMonitor;
import android.os.Handler;
import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;

public class RegisterTest extends ActivityInstrumentationTestCase2<Register> {

	private Solo solo;

	public RegisterTest(Class<Register> activityClass) {
		super(activityClass);
	}

	public RegisterTest() {
		super(Register.class);
	}

	private Register activity;

	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testJoinQuest() throws Exception {
		 solo.sendKey(Solo.MENU);
         
         solo.clearEditText(0);
         solo.enterText(0, "1");
         
         solo.clearEditText(1);
         solo.enterText(1, "1");
         
         solo.clearEditText(2);
         solo.enterText(2, "1");
         
         solo.clickOnButton("Register");
         
         assertTrue(solo.waitForText("ID already exists")); // alert dialog appeared
 		 solo.waitForDialogToClose(); /*First Test case. Tests for invalid ID*/
 		 
 		solo.clearEditText(0);
        
        solo.clearEditText(1);
        solo.enterText(1, "1");
        
        solo.clearEditText(2);
        solo.enterText(2, "1");
        
        solo.clickOnButton("Register");
        
        assertTrue(solo.waitForText("ID Field is empty")); // alert dialog appeared
		solo.waitForDialogToClose(); /*Second Test case. Tests for emptyID*/
		
		solo.clearEditText(0);
		solo.enterText(0, "Fail");
        
        solo.clearEditText(1);
        
        solo.clearEditText(2);
        solo.enterText(2, "1");
        
        solo.clickOnButton("Register");
        
        assertTrue(solo.waitForText("Password Field is empty")); // alert dialog appeared
		solo.waitForDialogToClose(); /*Third Test case. Tests for emptyPassword*/
		
		solo.clearEditText(0);
		solo.enterText(0, "Fail");
        
        solo.clearEditText(1);
        solo.enterText(1, "1");
        
        solo.clearEditText(2);
        
        solo.clickOnButton("Register");
        
        assertTrue(solo.waitForText("Confirm Password Field is empty")); // alert dialog appeared
		solo.waitForDialogToClose(); /*Fourth Test case. Tests for emptyPassword*/
		
		solo.clearEditText(0);
		solo.enterText(0, "Fail");
        
        solo.clearEditText(1);
        solo.enterText(1, "1");
        
        solo.clearEditText(2);
        solo.enterText(2, "3");
        
        solo.clickOnButton("Register");
        
        assertTrue(solo.waitForText("Passwords do not match")); // alert dialog appeared
		solo.waitForDialogToClose(); /*Fifth Test case. Tests for matchingPasswords*/
		
		solo.clearEditText(0);
		solo.enterText(0, "Fail");
        
        solo.clearEditText(1);
        solo.enterText(1, "Test");
        
        solo.clearEditText(2);
        solo.enterText(2, "3");
        
        //solo.clickOnButton("Test");
        
        assertTrue(solo.waitForText("Passwords do not match")); // alert dialog appeared
		solo.waitForDialogToClose(); /*Fifth Test case. Tests for matchingPasswords*/
		
	}
	
	 @Override
	   public void tearDown() throws Exception {
	        solo.finishOpenedActivities();
	  }

}
