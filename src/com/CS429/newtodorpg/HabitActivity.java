package com.CS429.newtodorpg;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.CS429.newtodorpg.controller.HabitAdapter;
import com.CS429.newtodorpg.database.DataBaseManager;
import com.CS429.newtodorpg.model.Habit;
import com.CS429.newtodorpg.model.Vice;

public class HabitActivity extends BaseActivity {
	private EditText add_habit_field;
	private ListView habit_list;
	private ArrayList<Habit> habits;
	private HabitAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.habit_activity);
		setHeader(R.id.header);
		habits = new ArrayList<Habit>();
		// new today_vice().execute();
		findViewById();
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
		habits.add(new Habit(my_habit));
		Toast.makeText(HabitActivity.this, my_habit, Toast.LENGTH_SHORT).show();
//		SetAdapter();
		adapter = new HabitAdapter(HabitActivity.this, habits);
		adapter.notifyDataSetChanged();
	}
	private void SetAdapter() {
		adapter = new HabitAdapter(HabitActivity.this, habits);
		habit_list.setAdapter(adapter);
		

	}

}

/*
private boolean addListFromEdit(EditText newtitle){
	String text = newtitle.getText().toString();
	if(text == null || text.isEmpty())
		return false;
	
	Vice vice = new Vice(text);
	vice.setImage(R.drawable.temp_images);
	//temporary due date set
	vice.setDueDate(Calendar.getInstance().get(Calendar.MONTH),
			Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 0, 0);
	vice_list.add(0, vice);
	newtitle.setText(null);
	DataBaseManager.getInstance(getApplicationContext()).insertVICE(vice);
	return true;
}

OnClickListener ButtonListener = new OnClickListener(){
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		
		case R.id.add_button:
			if(addListFromEdit(edit))
				adapter.notifyDataSetChanged();
			else
				Toast.makeText(ViceActivity.this, "type in title", Toast.LENGTH_SHORT).show();
			break;
			
		default:
			break;
		}
	}
};

class today_vice extends AsyncTask<String, String, String> {
	
	Calendar calendar;
	int month;
	int day;
	protected void onPreExecute() {
		super.onPreExecute();
		DataBaseManager.getInstance(ViceActivity.this);
		calendar = Calendar.getInstance();
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	protected String doInBackground(String... args) {
		vice_list = DataBaseManager.getInstance(ViceActivity.this).getVICE(month, day);
		if(vice_list == null)
			vice_list = new ArrayList<Vice>();
		return null;
	}
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		setViceListView();	
	}
}

}*/