package com.cs429.todorpg.revised.test;

import com.cs429.todorpg.revised.DailyActivity;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.ToDoActivity;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class DailyActivityTest extends ActivityInstrumentationTestCase2<DailyActivity> {

	private Solo solo;
	private DailyActivity mActivity;
	private EditText add_daily_field;
	private Button add_daily_button;
	private ListView listview;
	private ImageButton edit_button;
	private ImageButton delete_button;
	
	private SQLiteHelper db;
	
	public DailyActivityTest(){
		super(DailyActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getActivity().getBaseContext(), "test_");
		db = new SQLiteHelper(context);
		
		mActivity = getActivity();
		solo = new Solo(getInstrumentation(), mActivity);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}


	public void testAddHabit() throws Exception {
		add_daily_field = (EditText) solo.getView(R.id.add_daily_field);
		add_daily_button = (Button) solo.getView(R.id.add_daily_button);
		listview = (ListView) solo.getView(R.id.daily_listview);
		int original = listview.getCount();
		
		solo.clearEditText(add_daily_field);
		solo.enterText(add_daily_field, "testDaily");
		solo.clickOnView(add_daily_button);
		solo.sleep(1000);
		assertEquals(listview.getCount(), original + 1);
	}
	
	public void testDeleteHabit() throws Exception {
		listview = (ListView) solo.getView(R.id.daily_listview);
		int original = listview.getCount();
		delete_button = (ImageButton) solo.getView(R.id.daily_delete_button);
		solo.clickOnView(delete_button);
		solo.sleep(1000);
		
		assertEquals(listview.getCount(), original - 1);
	}
	
	

	
}
