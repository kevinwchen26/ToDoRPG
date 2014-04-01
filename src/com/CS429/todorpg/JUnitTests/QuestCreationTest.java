package com.CS429.todorpg.JUnitTests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.CS429.todorpg.AlarmActivity;
import com.CS429.todorpg.AlarmNotification;
import com.CS429.todorpg.CalendarView;
import com.CS429.todorpg.QuestCreation;
import com.CS429.todorpg.QuestMilestone;
import com.CS429.todorpg.R;
import com.CS429.todorpg.StartMain;
import com.google.android.gms.internal.co;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class QuestCreationTest extends ActivityInstrumentationTestCase2<QuestCreation> {

	private Activity mActivity;
	private Spinner capacitySpinner;
	private SpinnerAdapter capacityItem;
	private Spinner locationSpinner;
	private SpinnerAdapter locationItem;
	private EditText title;
	private EditText Description;
	private EditText due_date;
	
	private Button dateBtn;
	private Button SubmitBtn;
	private Button MileBtn;
	private Button AlarmBtn;
	
	private static final int CAPACITY_COUNT = 10;
	private static final int LOCATION_COUNT = 3;
	
	
	public QuestCreationTest() {
		super(QuestCreation.class);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		//disable touch mode in the device
		setActivityInitialTouchMode(false);
		//set up
		mActivity = getActivity();
		capacitySpinner = (Spinner)mActivity.findViewById(
				com.CS429.todorpg.R.id.creation_quest_maximum_number);
		capacityItem = capacitySpinner.getAdapter();
		locationSpinner = (Spinner)mActivity.findViewById(
				com.CS429.todorpg.R.id.creation_quest_location);
		locationItem = locationSpinner.getAdapter();
		title = (EditText)mActivity.findViewById(
				com.CS429.todorpg.R.id.creation_quest_title);
		Description = (EditText)mActivity.findViewById(
				com.CS429.todorpg.R.id.creation_quest_description);
		due_date = (EditText)mActivity.findViewById(
				com.CS429.todorpg.R.id.due_date_edit);
		
		dateBtn = (Button)mActivity.findViewById(
				com.CS429.todorpg.R.id.due_date);
		SubmitBtn = (Button)mActivity.findViewById(
				com.CS429.todorpg.R.id.creation_quest_submit);
		MileBtn = (Button)mActivity.findViewById(
				com.CS429.todorpg.R.id.creation_milestone_btn);
		AlarmBtn = (Button)mActivity.findViewById(
				com.CS429.todorpg.R.id.creation_alarm);
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@SuppressLint("NewApi")
	public void testPreConditions(){
		assertTrue(capacitySpinner.getOnItemSelectedListener() != null);
	    assertTrue(capacityItem != null);
	    assertEquals(capacityItem.getCount(), CAPACITY_COUNT);

		assertTrue(locationSpinner.getOnItemSelectedListener() != null);
	    assertTrue(locationItem != null);
	    assertEquals(locationItem.getCount(), LOCATION_COUNT);	    
	    
	    assertTrue(dateBtn.hasOnClickListeners());
	    assertTrue(SubmitBtn.hasOnClickListeners());
	    assertTrue(MileBtn.hasOnClickListeners());
	}

	
	@Test
	public void testSpinnerUI() {
/*		
		mActivity.runOnUiThread(new Runnable(){
			@Override
			public void run(){
				capacitySpinner.requestFocus();				
				capacitySpinner.setSelection(9, true);
				assertEquals("10", capacitySpinner.getSelectedItem().toString());
				
				locationSpinner.requestFocus();			
				locationSpinner.setSelection(2, true);
				assertEquals("No", locationSpinner.getSelectedItem().toString());
				mActivity.finish();
			}
		});
*/
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				capacitySpinner.requestFocus();				
				capacitySpinner.setSelection(1, true);
				assertEquals("2", capacitySpinner.getSelectedItem().toString());
				
				locationSpinner.requestFocus();			
				locationSpinner.setSelection(2, true);
				assertEquals("No", locationSpinner.getSelectedItem().toString());
			}
		});
		getInstrumentation().waitForIdleSync();
	}
	
	@Test
	public void testEditTextUI(){
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				title.requestFocus();
			}
		});
		getInstrumentation().waitForIdleSync();
		getInstrumentation().sendStringSync("Hello");
		assertEquals("Hello", title.getText().toString());
		
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				Description.requestFocus();
			}
		});
		getInstrumentation().waitForIdleSync();
		getInstrumentation().sendStringSync("Hello");
		assertEquals("Hello", Description.getText().toString());
		
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				due_date.requestFocus();
			}
		});
		getInstrumentation().waitForIdleSync();
		getInstrumentation().sendStringSync("12345");
		assertEquals("12345", due_date.getText().toString());
		
	}
	
	@Test
	public void testDateButtonUI(){
		assertTrue(dateBtn.isClickable());
				
		ActivityMonitor receiveCalendarView = 
				getInstrumentation().addMonitor(CalendarView.class.getName(), null, false);
		TouchUtils.clickView(this, dateBtn);
		CalendarView calendarActivity = (CalendarView)receiveCalendarView.waitForActivityWithTimeout(2000);
		
		assertNotNull("calendarActivity is null", calendarActivity);
		assertEquals("Monitor for calendarActivity has not been called",
		        1, receiveCalendarView.getHits());
		assertEquals("Activity is of wrong type",
		        CalendarView.class, calendarActivity.getClass());
	
		calendarActivity.finish();
	}
	
	@Test
	public void testMileButtonUI(){
		assertTrue(MileBtn.isClickable());
		ActivityMonitor receiveMileView = 
				getInstrumentation().addMonitor(QuestMilestone.class.getName(), null, false);
		TouchUtils.clickView(this, MileBtn);
		QuestMilestone mileActivity = (QuestMilestone)receiveMileView.waitForActivityWithTimeout(2000);
		
		assertNotNull(mileActivity);
		assertEquals(1, receiveMileView.getHits());
		assertEquals(QuestMilestone.class, mileActivity.getClass());
		
		getInstrumentation().removeMonitor(receiveMileView);
		mileActivity.finish();
	}
	
	@Test
	public void testSubmitButtonUI(){
		assertTrue(SubmitBtn.isClickable());
		TouchUtils.clickView(this, SubmitBtn);
		assertTrue(!mActivity.isFinishing());
	}
	
	@Test
	public void testAlarmButtonUI(){
		assertTrue(AlarmBtn.isClickable());
		ActivityMonitor receiveAlarmAct =
				getInstrumentation().addMonitor(AlarmActivity.class.getName(), null, false);
		TouchUtils.clickView(this, AlarmBtn);
		AlarmActivity alarmAct = (AlarmActivity)receiveAlarmAct.waitForActivityWithTimeout(2000);
		
		assertNotNull(alarmAct);
		assertEquals(1, receiveAlarmAct.getHits());
		assertEquals(AlarmActivity.class, alarmAct.getClass());
		getInstrumentation().removeMonitor(receiveAlarmAct);
		
		View currentView = alarmAct.findViewById(
				com.CS429.todorpg.R.id.alarm_okay);
		TouchUtils.clickView(this, currentView);
		assertTrue(alarmAct.isFinishing());
	}
	
	@Test
	public void testSuccessfulCreation(){
		//spinner
/*		
		mActivity.runOnUiThread(new Runnable(){
			@Override
			public void run(){
				capacitySpinner.requestFocus();				
				capacitySpinner.setSelection(1, true);
				assertEquals("2", capacitySpinner.getSelectedItem().toString());
				
				locationSpinner.requestFocus();			
				locationSpinner.setSelection(2, true);
				assertEquals("No", locationSpinner.getSelectedItem().toString());
			}
		});
*/
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				capacitySpinner.requestFocus();				
				capacitySpinner.setSelection(1, true);
				assertEquals("2", capacitySpinner.getSelectedItem().toString());
				
				locationSpinner.requestFocus();			
				locationSpinner.setSelection(2, true);
				assertEquals("No", locationSpinner.getSelectedItem().toString());
			}
		});
		getInstrumentation().waitForIdleSync();
		
		TouchUtils.clickView(this, title);
		getInstrumentation().sendStringSync("Hello");
		getInstrumentation().waitForIdleSync();
		TouchUtils.clickView(this, Description);
		getInstrumentation().sendStringSync("Hello");
		getInstrumentation().waitForIdleSync();
		TouchUtils.clickView(this, due_date);
		getInstrumentation().sendStringSync("4/1/2014");
		getInstrumentation().waitForIdleSync();
		
		
		//mile button
		assertTrue(MileBtn.isClickable());
		ActivityMonitor receiveMileView = 
				getInstrumentation().addMonitor(QuestMilestone.class.getName(), null, false);
		TouchUtils.clickView(this, MileBtn);
		QuestMilestone mileActivity = (QuestMilestone)receiveMileView.waitForActivityWithTimeout(2000);
		
		assertNotNull(mileActivity);
		assertEquals(1, receiveMileView.getHits());
		assertEquals(QuestMilestone.class, mileActivity.getClass());
		
		View currentView = mileActivity.findViewById(
				com.CS429.todorpg.R.id.mEdit);
		TouchUtils.clickView(this, currentView);
		getInstrumentation().sendStringSync("Hello");
		getInstrumentation().waitForIdleSync();
		
		currentView = mileActivity.findViewById(
				com.CS429.todorpg.R.id.milestone_upload);
		TouchUtils.clickView(this, currentView);
		
		getInstrumentation().removeMonitor(receiveMileView);
		getInstrumentation().waitForIdleSync();
		
		//alarm button
		assertTrue(AlarmBtn.isClickable());

		ActivityMonitor receiveAlarmAct =
				getInstrumentation().addMonitor(AlarmActivity.class.getName(), null, false);

		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				AlarmBtn.performClick();
			}
		});
		AlarmActivity alarmAct = (AlarmActivity)receiveAlarmAct.waitForActivityWithTimeout(2000);
		
		assertNotNull(alarmAct);
		assertEquals(1, receiveAlarmAct.getHits());
		assertEquals(AlarmActivity.class, alarmAct.getClass());
		getInstrumentation().removeMonitor(receiveAlarmAct);

		getInstrumentation().waitForIdleSync();
		currentView = alarmAct.findViewById(
				com.CS429.todorpg.R.id.alarm_once);
		TouchUtils.clickView(this, currentView);
		
		currentView = alarmAct.findViewById(
				com.CS429.todorpg.R.id.alarm_ringtone);
		TouchUtils.clickView(this, currentView);
		
		currentView = alarmAct.findViewById(
				com.CS429.todorpg.R.id.alarm_okay);
		TouchUtils.clickView(this, currentView);
		assertTrue(alarmAct.isFinishing());
		
		//alarmnoti test
		ActivityMonitor receiveAlarmNoti = 
				getInstrumentation().addMonitor(AlarmNotification.class.getName(), null, false);
		assertTrue(SubmitBtn.isClickable());	
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				SubmitBtn.performClick();
			}
		});
		assertTrue(mActivity.isFinishing());
		AlarmNotification alarmNoti = (AlarmNotification)receiveAlarmNoti.waitForActivity();
		
		assertNotNull(alarmNoti);
		assertEquals(AlarmNotification.class, alarmNoti.getClass());
		getInstrumentation().waitForIdleSync();
		
		getInstrumentation().runOnMainSync(new Runnable(){
			@Override
			public void run(){
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.add(Calendar.SECOND, 2);
				while(System.currentTimeMillis() <= calendar.getTimeInMillis()){}
			}
		});
		getInstrumentation().waitForIdleSync();
		
		currentView = alarmNoti.findViewById(
				com.CS429.todorpg.R.id.alarm_okay_button);
		TouchUtils.clickView(this, currentView);
		assertTrue(alarmNoti.isFinishing());
	}
}
