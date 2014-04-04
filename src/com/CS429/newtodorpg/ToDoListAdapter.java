package com.CS429.newtodorpg;

import java.util.ArrayList;

import com.CS429.newtodorpg.model.Daily;
import com.CS429.newtodorpg.model.Quest;
import com.CS429.newtodorpg.model.ToDo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ToDoListAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<ToDo> list;
	private ToDoListAdapter adapter = this;
	
	public ToDoListAdapter(Context context, ArrayList<ToDo> todos){
		this.context = context;
		this.list = todos;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;
		if(row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(R.layout.todo_list_view_row, parent, false);
		}
		
		ImageView image = (ImageView)row.findViewById(R.id.imgIcon);
		TextView title = (TextView)row.findViewById(R.id.txtTitle);
		
		Button detail_button = (Button)row.findViewById(R.id.add_todo_detail_button);
		detail_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ToDoDetailActivity.class);
				((Activity) context).startActivityForResult(intent, Quest.VICE);
			}	
		});
		
		Button delete_button = (Button)row.findViewById(R.id.delete_todo_button);
		delete_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				list.remove(position);
				adapter.notifyDataSetChanged();
			}	
		});
		
		ToDo todo = list.get(position);
		image.setImageResource(todo.getImageResource());
		title.setText(todo.getTitle());
		
		return row;
	}

}
