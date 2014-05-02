package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cs429.todorpg.revised.controller.HabitAdapter;
import com.cs429.todorpg.revised.model.Habit;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
/**
 * 
 * @author hlim10, ssong25
 *
 */
public class HabitActivity extends BaseActivity {
	private EditText add_habit_field;
	private ListView habit_list;
	private ArrayList<Habit> habits;
	private HabitAdapter adapter;
	private SQLiteHelper db;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.habit_activity);
		setHeader(R.id.header);
		// new today_vice().execute();
		findViewById();
		db = new SQLiteHelper(getBaseContext());
		setHabitList();
		SetAdapter();
	}

	private void findViewById() {
		add_habit_field = (EditText) findViewById(R.id.add_habit_field);
		habit_list = (ListView) findViewById(R.id.habit_listview);
		findViewById(R.id.add_habit_button).setOnClickListener(ButtonHandler);
		
	}
	
	Button.OnClickListener ButtonHandler = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch(view.getId()) {
				case R.id.add_habit_button:
					String my_habit = add_habit_field.getText().toString();
					if(my_habit.isEmpty()) {
						Toast.makeText(HabitActivity.this, "Fill in the blank", Toast.LENGTH_SHORT).show();
						return;
					}
					AddMyHabit(habits, my_habit);
					add_habit_field.setText("");
					SetAdapter();
					break;
			}
		}
		
	};
	private void AddMyHabit(ArrayList<Habit> habits, String my_habit) {
		for(int i = 0; i < habits.size(); i++) {
			if(habits.get(i).getHabit().equals(my_habit)) {
				Toast.makeText(HabitActivity.this, "\"" + my_habit +"\" is alreay in your habit list", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		Habit habit = new Habit(my_habit);
		habits.add(habit);
		int id = db.addHabit(habit);
		habit.setKey(id);
		
		Toast.makeText(HabitActivity.this, my_habit, Toast.LENGTH_SHORT).show();
//		SetAdapter();
		adapter = new HabitAdapter(HabitActivity.this, habits);
		adapter.notifyDataSetChanged();
	}
	private void SetAdapter() {
		adapter = new HabitAdapter(HabitActivity.this, habits);
		habit_list.setAdapter(adapter);
		

	}

	private void setHabitList(){
		habits = db.getHabits();
		if(habits == null)
			habits = new ArrayList<Habit>();
	}
}
