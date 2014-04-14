package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import com.cs429.todorpg.revised.CalendarView;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.ToDoActivity;
import com.cs429.todorpg.revised.model.ToDo;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

public class ToDoAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<ToDo> todos;
	private ToDoAdapter adapter = this;
	private LayoutInflater inflater;
	private SQLiteHelper db;
	
	public ToDoAdapter(Context context, ArrayList<ToDo> todos){
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.todos = todos;
		db = new SQLiteHelper(context);
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
		final EditText extra_notes = (EditText) convertView.findViewById(R.id.extra_notes);
		
		change_title.setText(todos.get(position).getToDo());
		
		final Button done_button = (Button) convertView.findViewById(R.id.todo_done_button);
		final ImageButton edit_button = (ImageButton) convertView.findViewById(R.id.todo_edit_button);
		final ImageButton save_button = (ImageButton) convertView.findViewById(R.id.todo_save_button);
		final ImageButton cancel_button = (ImageButton) convertView.findViewById(R.id.todo_cancel_button);
		final ImageButton delete_button = (ImageButton) convertView.findViewById(R.id.todo_delete_button);
		final View show_edit_field = (View) convertView.findViewById(R.id.show_edit_field);
		final Button save_close_button = (Button) convertView.findViewById(R.id.save_close);
		
		///edit_box_field
		final Button hard = (Button)convertView.findViewById(R.id.hard);
		final Button medium = (Button)convertView.findViewById(R.id.medium);
		final Button easy = (Button)convertView.findViewById(R.id.easy);
		
		final Button due_date = (Button)convertView.findViewById(R.id.due_date_button);
		due_date.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(context, CalendarView.class);
				intent.putExtra("pos", position);
				((ToDoActivity) context).startActivityForResult(intent, 0);
//				context.startActivity(intent);
			}
		});
		
		OnClickListener mListener = new OnClickListener(){
			@Override
			public void onClick(View v){
				switch(v.getId()){
				case R.id.hard:
					Log.d("[HABIT]", "difficult hard");
					todos.get(position).setDifficulty(2);
					hard.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					easy.setBackgroundResource(R.color.original);
					break;
					
				case R.id.medium:
					Log.d("[HABIT]", "difficult medium");
					todos.get(position).setDifficulty(1);
					medium.setBackgroundResource(R.color.selected);
					hard.setBackgroundResource(R.color.original);
					easy.setBackgroundResource(R.color.original);
					break;
					
				case R.id.easy:
					Log.d("[HABIT]", "difficult easy");
					todos.get(position).setDifficulty(0);
					easy.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					hard.setBackgroundResource(R.color.original);
					break;
				}
			}
		};
		hard.setOnClickListener(mListener);
		medium.setOnClickListener(mListener);
		easy.setOnClickListener(mListener);
		
		
		done_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Toast.makeText(context, "successfully done this job", Toast.LENGTH_SHORT).show();
				todos.get(position).setFinish();
				db.updateToDo(todos.get(position));
				todos.remove(position);
				adapter.notifyDataSetChanged();
			}
		});
		
		save_close_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(change_title.getText().toString().length() == 0) {
					Toast.makeText(context, "Fill in the blank", Toast.LENGTH_SHORT).show();
					return;
				}
				todos.get(position).setToDo(change_title.getText().toString());
				
				String tmp = extra_notes.getText().toString();
				if(tmp != null){
					todos.get(position).setExtra(tmp);
					Log.d("[TODO]", "extra note: " + tmp);
				}
				db.updateToDo(todos.get(position));
				
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
				extra_notes.setText(todos.get(position).getExtra());
				change_title.setText(todos.get(position).getToDo());
				int[] tmp = todos.get(position).getDueDate();
				if(tmp.length > 2 && tmp[0] > 0)
					due_date.setText(tmp[0] + "/" + tmp[1] + "/2014");
				
				edit_button.setVisibility(View.GONE);
				cancel_button.setVisibility(View.VISIBLE);
				save_button.setVisibility(View.VISIBLE);
				show_edit_field.setVisibility(View.VISIBLE);

				switch(todos.get(position).getDifficulty()){
				case 0:	//easy
					easy.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					hard.setBackgroundResource(R.color.original);
					break;
				case 1:	//medium
					medium.setBackgroundResource(R.color.selected);
					easy.setBackgroundResource(R.color.original);
					hard.setBackgroundResource(R.color.original);
					break;
				case 2:	//hard
					hard.setBackgroundResource(R.color.selected);
					medium.setBackgroundResource(R.color.original);
					easy.setBackgroundResource(R.color.original);
					break;
				}
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
				///
				String tmp = extra_notes.getText().toString();
				if(tmp != null){
					todos.get(position).setExtra(tmp);
					Log.d("[TODO]", "extra note: " + tmp);
				}
				///
				db.updateToDo(todos.get(position));
				
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
				db.deleteToDo(todos.get(position));
				todos.remove(position);
				adapter.notifyDataSetChanged();
			}
		});
		
//		edit_field_operation(position, convertView);
		
		return convertView;
	}

/*	
	private void edit_field_operation(final int position, View view){
		
		final Button due_date = (Button)view.findViewById(R.id.due_date_button);
		due_date.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(context, CalendarView.class);
				((ToDoActivity) context).startActivityForResult(intent, 0);
//				context.startActivity(intent);
			}
		});
		
		final EditText extra_note = (EditText)view.findViewById(R.id.extra_notes);
		final Button save_close = (Button)view.findViewById(R.id.save_close);
		save_close.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				String tmp = extra_note.getText().toString();
				if(tmp != null){
					todos.get(position).setExtra(tmp);
					Log.d("[TODO]", "extra note: " + tmp);
				}
			}
		});
		
		final Button hard = (Button)view.findViewById(R.id.hard);
		final Button medium = (Button)view.findViewById(R.id.medium);
		final Button easy = (Button)view.findViewById(R.id.easy);
		
		OnClickListener mListener = new OnClickListener(){
			@Override
			public void onClick(View v){
				switch(v.getId()){
				case R.id.hard:
					Log.d("[TODO]", "difficult hard");
					todos.get(position).setDifficulty(2);
					break;
					
				case R.id.medium:
					Log.d("[TODO]", "difficult medium");
					todos.get(position).setDifficulty(1);
					break;
					
				case R.id.easy:
					Log.d("[TODO]", "difficult easy");
					todos.get(position).setDifficulty(0);
					break;
				}
			}
		};
		hard.setOnClickListener(mListener);
		medium.setOnClickListener(mListener);
		easy.setOnClickListener(mListener);
	}
*/	
}
