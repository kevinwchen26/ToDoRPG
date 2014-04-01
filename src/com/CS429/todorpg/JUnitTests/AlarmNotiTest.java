package com.CS429.todorpg.JUnitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.CS429.todorpg.AlarmNotification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

public class AlarmNotiTest extends ActivityInstrumentationTestCase2<AlarmNotification> {

	Activity mActivity;
	Intent mIntent;
	Button mOkaybtn;
	Button mCancelbtn;
	TextView text;
	
	public AlarmNotiTest() {
		super(AlarmNotification.class);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		mIntent = new Intent(getInstrumentation().getContext(), AlarmNotification.class);
		mIntent.putExtra("noti", "noring");
		mIntent.putExtra("due date", -1);
		mIntent.putExtra("title", "hello");
		mIntent.putExtra("ringtone", "download/");
		setActivityIntent(mIntent);
		mActivity = getActivity();
		
		mOkaybtn = (Button)mActivity.findViewById(
				com.CS429.todorpg.R.id.alarm_okay_button);
		mCancelbtn = (Button)mActivity.findViewById(
				com.CS429.todorpg.R.id.alarm_cancel_button);
		text = (TextView)mActivity.findViewById(
				com.CS429.todorpg.R.id.alarm_title);
	}
	
	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@SuppressLint("NewApi")
	public void testPreCondition(){
		assertNotNull(mIntent);
		assertNotNull(mActivity);
		assertNotNull(text);
		assertEquals("hello", text.getText().toString());
		assertNotNull(mOkaybtn);
		assertTrue(mOkaybtn.hasOnClickListeners());
		assertNotNull(mCancelbtn);
		assertTrue(mCancelbtn.hasOnClickListeners());
	}
	
	@Test
	public void testButton() {
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				getInstrumentation().callActivityOnCreate(mActivity, null);
				assertTrue(mOkaybtn.isClickable());
				mOkaybtn.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();
			
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				getInstrumentation().callActivityOnCreate(mActivity, null);
				assertTrue(mCancelbtn.isClickable());
				mCancelbtn.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();
	}
	
	@Test
	public void testActivity(){
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				getInstrumentation().callActivityOnCreate(mActivity, null);
			}
		});
		getInstrumentation().waitForIdleSync();
		
		assertEquals(mActivity.getIntent().getStringExtra("title"), "hello");
		assertEquals(mActivity.getIntent().getStringExtra("noti"), "noring");
		assertEquals(mActivity.getIntent().getStringExtra("ringtone"), "download/");
		
	}

}
