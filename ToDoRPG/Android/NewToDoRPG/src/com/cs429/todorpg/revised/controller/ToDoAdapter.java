package com.cs429.todorpg.revised.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

import com.cs429.todorpg.revised.BaseActivity;
import com.cs429.todorpg.revised.CalendarView;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.ToDoActivity;
import com.cs429.todorpg.revised.model.LogItem;
import com.cs429.todorpg.revised.model.ToDo;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.revised.utils.Constants;
import com.cs429.todorpg.revised.utils.SQLiteHelper;
/**
 * ToDo Adapter
 * @author hlim10, ssong25
 *
 */
public class ToDoAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<ToDo> todos;
	private ToDoAdapter adapter = this;
	private LayoutInflater inflater;
	private SQLiteHelper db;
	private String title;
	int difficulty;
	/**
	 * Constructor
	 * @param context
	 * @param todos
	 */
	public ToDoAdapter(Context context, ArrayList<ToDo> todos){
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.todos = todos;
		db = new SQLiteHelper(context);
	}
	/**
	 * Get Count of ToDo List
	 */
	@Override
	public int getCount() {
		return todos.size();
	}
	/**
	 * Get selected item of ToDo List
	 */
	@Override
	public Object getItem(int position) {
		return todos.get(position);
	}
	/**
	 * Get selected item id of ToDo List
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	/**
	 * Shows the details of ToDo Activity
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		String blank = "    ";
		title = "Completed ToDo : " + todos.get(position).getToDo();
		difficulty = todos.get(position).getDifficulty();
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
		/**
		 * Button Listener Handler
		 */
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
		difficulty = todos.get(position).getDifficulty();
		/**
		 * done button listener
		 */
		done_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Toast.makeText(context, "successfully done this job", Toast.LENGTH_SHORT).show();
				Constants.UpdateCharacterStatus(db, difficulty, context, 1);
				BaseActivity.TextValidate();
				todos.get(position).setFinish();
				db.updateToDo(todos.get(position));
				todos.remove(position);
				adapter.notifyDataSetChanged();
				Calendar c = Calendar.getInstance();
				System.out.println("Current time => " + c.getTime());

				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				String formattedDate = df.format(c.getTime());
				db.addLogItem(new LogItem(title, formattedDate));
			}
			
		});
		
		/**
		 * save button listener
		 */
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
		/**
		 * edit button listener
		 */
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
		/**
		 * save button listener
		 */
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
		/**
		 * cancel button listener
		 */
		cancel_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit_button.setVisibility(View.VISIBLE);
				cancel_button.setVisibility(View.GONE);
				save_button.setVisibility(View.GONE);
				show_edit_field.setVisibility(View.GONE);
			}
		});
		/**
		 * delete button listener
		 */
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
}
