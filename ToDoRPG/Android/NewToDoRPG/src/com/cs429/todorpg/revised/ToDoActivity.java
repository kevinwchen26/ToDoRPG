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

public class ToDoActivity extends BaseActivity {
	private EditText add_todo_field;
	private ListView todo_list;
	private ArrayList<ToDo> todos;
	private ToDoAdapter adapter;
	private SQLiteHelper db;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.todo_activity);
		setHeader(R.id.header);
		// new today_vice().execute();
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
	
	Button.OnClickListener ButtonHandler = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch(view.getId()) {
				case R.id.add_todo_button:
					String my_todo = add_todo_field.getText().toString();
					if(my_todo.isEmpty()) {
						Toast.makeText(ToDoActivity.this, "Fill in the blank", Toast.LENGTH_SHORT).show();
						return;
					}
					AddMyToDos(todos, my_todo);
					add_todo_field.setText("");
					SetAdapter();
					break;
			}
		}
		
	};
	private void AddMyToDos(ArrayList<ToDo> todos, String my_todo) {
		for(int i = 0; i < todos.size(); i++) {
			if(todos.get(i).getToDo().equals(my_todo)) {
				Toast.makeText(ToDoActivity.this, "\"" + my_todo +"\" is alreay in your ToDo list", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		ToDo todo = new ToDo(my_todo); 
		todos.add(todo);
		int id = db.addToDo(todo);
		todo.setKey(id);
		Log.d("[TODO]", "db position: " + id);
		Toast.makeText(ToDoActivity.this, my_todo, Toast.LENGTH_SHORT).show();
//		SetAdapter();
		adapter = new ToDoAdapter(ToDoActivity.this, todos);
		adapter.notifyDataSetChanged();
	}
	private void SetAdapter() {
		adapter = new ToDoAdapter(ToDoActivity.this, todos);
		todo_list.setAdapter(adapter);
	}
	private void setToDoList(){
		todos = db.getToDos();
		if(todos == null)
			todos = new ArrayList<ToDo>();
		else{
			ArrayList<ToDo> list = new ArrayList<ToDo>();
			//filter finished todo list
			for(int i = 0; i < todos.size(); ++i){
				if(!todos.get(i).getStatus())
					list.add(todos.get(i));
			}
			todos = list;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(requestCode == 0){//due date from calendar view
				String[] dates = data.getStringExtra("DATE").split("/"); //month/day/year
				int pos = data.getIntExtra("pos", -1);
				if(dates.length < 3 || pos == -1)
					Log.e("[TODO]", "invalid data");
				else{
					ToDo todo = todos.get(pos);
					todo.setDueDate(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), 0, 0);
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

}

	/*private void findViewById(){
		todo_list_view = (ListView)findViewById(R.id.todo_listview);
		add_button = (Button)findViewById(R.id.add_button);
		add_button.setOnClickListener(ButtonListener);
		edit = (EditText)findViewById(R.id.list_edit);
	}
	
	
	private ArrayList<ToDo> settodoList(){
		//temporary lists
		ArrayList<ToDo> tmp = new ArrayList<ToDo>();
		ToDo tmp1 = new ToDo("todo1");
		tmp1.setImage(R.drawable.temp_images);
		tmp.add(tmp1);
		
		ToDo tmp2 = new ToDo("todo2");
		tmp2.setImage(R.drawable.temp_images);
		tmp.add(tmp2);
		
		ToDo tmp3 = new ToDo("todo3");
		tmp3.setImage(R.drawable.temp_images);
		tmp.add(tmp3);
		////////
		
		return tmp;
	}
	
	private void settodoListView(){
		adapter = new ToDoListAdapter(
				ToDoActivity.this, todo_list);
		
		View header = (View)getLayoutInflater().inflate(R.layout.quest_header, null);
		TextView txt = (TextView)header.findViewById(R.id.txtHeader);
		txt.setText("TODO LIST");
		todo_list_view.addHeaderView(header);
		todo_list_view.setAdapter(adapter);
	}	
	
	private boolean addListFromEdit(EditText newtitle){
		String text = newtitle.getText().toString();
		if(text == null || text.isEmpty())
			return false;
		
		ToDo todo = new ToDo(text);
		todo.setImage(R.drawable.temp_images);
		//temporary due date set
		todo.setDueDate(Calendar.getInstance().get(Calendar.MONTH),
			Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 0, 0);
		//temporary milestone writing
		todo.WriteMileStone("AAAAAAAAAA");
		todo_list.add(0, todo);
		newtitle.setText(null);
		DataBaseManager.getInstance(getApplicationContext()).insertTODO(todo);
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
					Toast.makeText(ToDoActivity.this, "type in title", Toast.LENGTH_SHORT).show();
				break;				
			default:
				break;
			}
		}
	};
	
class today_todo extends AsyncTask<String, String, String> {
		
		Calendar calendar;
		int month;
		int day;
		protected void onPreExecute() {
			super.onPreExecute();
			DataBaseManager.getInstance(ToDoActivity.this);
			calendar = Calendar.getInstance();
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}

		@Override
		protected String doInBackground(String... args) {
			todo_list = DataBaseManager.getInstance(ToDoActivity.this).getAllToDo();
			if(todo_list == null)
				todo_list = new ArrayList<ToDo>();
			return null;
		}
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			settodoListView();	
		}
	}
	*/
