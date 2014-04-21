package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.cs429.todorpg.revised.controller.ToDoAdapter;
import com.cs429.todorpg.revised.model.ToDo;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class StatusActivity extends BaseActivity {
	TextView current_level, current_hp, current_exp, completed_quests, current_money, total_battles;
	private ListView completed_quest_list;
	private SQLiteHelper db;
	private ArrayList<ToDo> todos;
	private ToDoAdapter adapter;
	int completed_quest_count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.status_activity);
		setHeader(R.id.header);
		db = new SQLiteHelper(getBaseContext());
		FindViewById();
		GetStatus();
		SetAdapter();

	}

	private void FindViewById() {
		current_level = (TextView) findViewById(R.id.current_level);
		current_hp = (TextView) findViewById(R.id.current_hp);
		current_exp = (TextView) findViewById(R.id.current_exp);
		completed_quests = (TextView) findViewById(R.id.completed_quests);
		current_money = (TextView) findViewById(R.id.current_money);
		total_battles = (TextView) findViewById(R.id.total_battles);
		completed_quest_list = (ListView) findViewById(R.id.completed_quest_list);
	}

	private void GetStatus() {
		todos = db.getToDos();
		if (todos == null)
			todos = new ArrayList<ToDo>();
		else {
			ArrayList<ToDo> list = new ArrayList<ToDo>();
			// filter finished todo list
			for (int i = 0; i < todos.size(); ++i) {
				if (todos.get(i).getStatus()) {
					list.add(todos.get(i));
					completed_quest_count++;
				}
			}
			todos = list;
			completed_quests.setText(Integer.toString(completed_quest_count));
		}

	}
	private void SetAdapter() {
		adapter = new ToDoAdapter(StatusActivity.this, todos);
		completed_quest_list.setAdapter(adapter);
	}
}