package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.model.ToDo;

public class ToDoAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<ToDo> todos;
	private ToDoAdapter adapter = this;
	private LayoutInflater inflater;
	
	public ToDoAdapter(Context context, ArrayList<ToDo> todos){
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.todos = todos;
	}
	
	@Override
	public int getCount() {
		return todos.size();
	}

	@Override
	public Object getItem(int position) {
		return todos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		String blank = "    ";
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.todo_list_view_row, parent, false);
		}
		TextView my_todo = (TextView) convertView.findViewById(R.id.todo_text);
		my_todo.setText(blank + todos.get(position).getToDo());
		
		final EditText change_title = (EditText) convertView.findViewById(R.id.change_title);
		EditText extra_notes = (EditText) convertView.findViewById(R.id.extra_notes);
		
		change_title.setText(todos.get(position).getToDo());
		
		final ImageButton edit_button = (ImageButton) convertView.findViewById(R.id.todo_edit_button);
		final ImageButton save_button = (ImageButton) convertView.findViewById(R.id.todo_save_button);
		final ImageButton cancel_button = (ImageButton) convertView.findViewById(R.id.todo_cancel_button);
		final ImageButton delete_button = (ImageButton) convertView.findViewById(R.id.todo_delete_button);
		final View show_edit_field = (View) convertView.findViewById(R.id.show_edit_field);
		final Button save_close_button = (Button) convertView.findViewById(R.id.save_close);
		
		save_close_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(change_title.getText().toString().length() == 0) {
					Toast.makeText(context, "Fill in the blank", Toast.LENGTH_SHORT).show();
					return;
				}
				todos.get(position).setToDo(change_title.getText().toString());
				adapter.notifyDataSetChanged();
				edit_button.setVisibility(View.VISIBLE);
				cancel_button.setVisibility(View.GONE);
				save_button.setVisibility(View.GONE);
				show_edit_field.setVisibility(View.GONE);
			}
		});
		
		edit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				change_title.setText(todos.get(position).getToDo());
				edit_button.setVisibility(View.GONE);
				cancel_button.setVisibility(View.VISIBLE);
				save_button.setVisibility(View.VISIBLE);
				show_edit_field.setVisibility(View.VISIBLE);

			}
		});
		save_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(change_title.getText().toString().length() == 0) {
					Toast.makeText(context, "Fill in the blank", Toast.LENGTH_SHORT).show();
					return;
				}
				todos.get(position).setToDo(change_title.getText().toString());
				adapter.notifyDataSetChanged();
				edit_button.setVisibility(View.VISIBLE);
				cancel_button.setVisibility(View.GONE);
				save_button.setVisibility(View.GONE);
				show_edit_field.setVisibility(View.GONE);
			}
		});

		cancel_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit_button.setVisibility(View.VISIBLE);
				cancel_button.setVisibility(View.GONE);
				save_button.setVisibility(View.GONE);
				show_edit_field.setVisibility(View.GONE);
			}
		});

		delete_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				todos.remove(position);
				adapter.notifyDataSetChanged();
			}
		});
		
		
		return convertView;
	}

}
