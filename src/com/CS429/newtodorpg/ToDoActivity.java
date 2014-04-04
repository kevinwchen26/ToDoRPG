package com.CS429.newtodorpg;

import java.util.ArrayList;

import com.CS429.newtodorpg.model.ToDo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ToDoActivity extends BaseActivity {
	
	private ArrayList<ToDo> todo_list;
	private ListView todo_list_view;
	private ToDoListAdapter adapter;
	private Button add_button;
	private EditText edit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_activity);
//		setHeader(R.id.header);
		findViewById();
		todo_list = settodoList();
		settodoListView();
	}

	private void findViewById(){
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
		
//		View header = (View)getLayoutInflater().inflate(R.layout.list_view_header, null);
//		daily_list_view.addHeaderView(header);
		todo_list_view.setAdapter(adapter);
	}	
	
	private boolean addListFromEdit(EditText newtitle){
		String text = newtitle.getText().toString();
		if(text == null || text.isEmpty())
			return false;
		
		ToDo todo = new ToDo(text);
		todo.setImage(R.drawable.temp_images);
		todo_list.add(0, todo);
		newtitle.setText(null);
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
	
}
