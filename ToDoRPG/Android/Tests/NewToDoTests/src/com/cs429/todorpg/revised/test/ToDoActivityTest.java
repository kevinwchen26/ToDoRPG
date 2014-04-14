package com.cs429.todorpg.revised.test;

import com.cs429.todorpg.revised.HabitActivity;
import com.cs429.todorpg.revised.ToDoActivity;
import com.robotium.solo.Solo;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class ToDoActivityTest extends ActivityInstrumentationTestCase2<ToDoActivity> {

	private Solo solo;
	private ToDoActivity mActivity;
	private EditText add_todo_field;
	private Button add_todo_button;
	private ListView listview;
	private ImageButton edit_button;
	private ImageButton delete_button;
	
	private SQLiteHelper db;
	
	public ToDoActivityTest(){
		super(ToDoActivity.class);
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
		add_todo_field = (EditText) solo.getView(R.id.add_todo_field);
		add_todo_button = (Button) solo.getView(R.id.add_todo_button);
		listview = (ListView) solo.getView(R.id.todo_listview);
		int original = listview.getCount();
		
		solo.clearEditText(add_todo_field);
		solo.enterText(add_todo_field, "testToDo");
		solo.clickOnView(add_todo_button);
		solo.sleep(1000);
		assertEquals(listview.getCount(), original + 1);
	}
	
	public void testDeleteHabit() throws Exception {
		listview = (ListView) solo.getView(R.id.todo_listview);
		int original = listview.getCount();
		delete_button = (ImageButton) solo.getView(R.id.todo_delete_button);
		solo.clickOnView(delete_button);
		solo.sleep(1000);
		
		assertEquals(listview.getCount(), original - 1);
	}
	
	
	
	
}
