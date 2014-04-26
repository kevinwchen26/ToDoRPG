package com.cs429.todorpg.revised;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.cs429.todorpg.revised.controller.ToDoAdapter;
import com.cs429.todorpg.revised.model.ToDo;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class StatusActivity extends BaseActivity {
	TextView current_level, current_hp, current_exp, completed_quests, current_money, total_battles;
	private ListView completed_quest_list;
	private SQLiteHelper db;
	private ArrayList<ToDo> todos;
	private ToDoCharacter character;
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
		GetCharacterInfo();
		GetToDos();
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
	
	private void GetCharacterInfo() {
		character = db.getCharacter();
		if(character == null) {
			return;
		}
		current_level.setText(Integer.toString(character.getLevel()));
		current_hp.setText(Integer.toString(character.getHP()));
		current_money.setText(Integer.toString(character.getGold()));
		DecimalFormat df = new DecimalFormat("#.00"); 
		double curr_exp = character.getCurrExp() / (double)(character.getLevel() * 100) * 100;
		String result = df.format(curr_exp).concat("%");
		current_exp.setText(result);
		
	}

	private void GetToDos() {
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
