package com.CS429.todorpg;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ToDoListAdapter extends BaseAdapter {
	private final Context context;
	private ArrayList<MyToDoList> data;
	private LayoutInflater inflater;
	CheckBox check;

	public ToDoListAdapter(Context context, ArrayList<MyToDoList> data) {
		this.context = context;
		this.data = data;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.todo_list_row, parent,
					false);
		}

		check = (CheckBox) convertView.findViewById(R.id.checkBox);
		check.setVisibility(View.GONE);
		if(QuestDetail.leader.equals(StaticClass.MY_ID)) {
			check.setVisibility(View.VISIBLE);
		}
		TextView todolist = (TextView) convertView.findViewById(R.id.todo_list);
		todolist.setText(data.get(position).getList());
		check.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(context, ", Toast.LENGTH_SHORT).show();
					
			}
			
		});

		return convertView;
	}

}