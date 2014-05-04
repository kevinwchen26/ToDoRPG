package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cs429.todorpg.revised.controller.ToDoAdapter;
import com.cs429.todorpg.revised.model.ToDo;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

/**
 * ToDo Activity
 * 
 * @author hlim10, ssong25
 * 
 */
public class ToDoActivity extends BaseActivity {
	private EditText add_todo_field;
	private ListView todo_list;
	private ArrayList<ToDo> todos;
	private ToDoAdapter adapter;
	private SQLiteHelper db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.todo_activity);
		setHeader(R.id.header);
		findViewById();
		db = new SQLiteHelper(getBaseContext());
		setToDoList();
		SetAdapter();
	}

	private void findViewById() {
		add_todo_field = (EditText) findViewById(R.id.add_todo_field);
		todo_list = (ListView) findViewById(R.id.todo_listview);
		findViewById(R.id.add_todo_button).setOnClickListener(ButtonHandler);

	}

	/**
	 * Add todo list whenever user clicks add button.
	 */
	Button.OnClickListener ButtonHandler = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.add_todo_button:
				String my_todo = add_todo_field.getText().toString();
				if (my_todo.isEmpty()) {
					Toast.makeText(ToDoActivity.this, "Fill in the blank",
							Toast.LENGTH_SHORT).show();
					return;
				}
				AddMyToDos(todos, my_todo);
				add_todo_field.setText("");
				SetAdapter();
				break;
			}
		}

	};

	/**
	 * Add ToDo Activity if no duplicate activity found.
	 * 
	 * @param todos
	 * @param my_todo
	 */
	private void AddMyToDos(ArrayList<ToDo> todos, String my_todo) {
		for (int i = 0; i < todos.size(); i++) {
			if (todos.get(i).getToDo().equals(my_todo)) {
				Toast.makeText(ToDoActivity.this,
						"\"" + my_todo + "\" is alreay in your ToDo list",
						Toast.LENGTH_SHORT).show();
				return;
			}
		}
		ToDo todo = new ToDo(my_todo);
		todos.add(todo);
		int id = db.addToDo(todo);
		todo.setKey(id);
		Log.d("[TODO]", "db position: " + id);
		// SetAdapter();
		adapter = new ToDoAdapter(ToDoActivity.this, todos);
		adapter.notifyDataSetChanged();
		TextValidate();
	}

	private void SetAdapter() {
		adapter = new ToDoAdapter(ToDoActivity.this, todos);
		todo_list.setAdapter(adapter);
	}

	/**
	 * Get Current ToDoList from DB.
	 */
	private void setToDoList() {
		todos = db.getToDos(2);
		if (todos == null)
			todos = new ArrayList<ToDo>();
		else {
			ArrayList<ToDo> list = new ArrayList<ToDo>();
			// filter finished todo list
			for (int i = 0; i < todos.size(); ++i) {
				if (!todos.get(i).getStatus())
					list.add(todos.get(i));
			}
			todos = list;
		}
	}

	/**
	 * Get Result depends on the return value
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {// due date from calendar view
				String[] dates = data.getStringExtra("DATE").split("/"); // month/day/year
				int pos = data.getIntExtra("pos", -1);
				if (dates.length < 3 || pos == -1)
					Log.e("[TODO]", "invalid data");
				else {
					ToDo todo = todos.get(pos);
					todo.setDueDate(Integer.parseInt(dates[0]),
							Integer.parseInt(dates[1]), 0, 0);
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

}
