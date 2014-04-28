package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.model.LogItem;
import com.cs429.todorpg.revised.model.Stat;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LogAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	public ArrayList<LogItem> log;
	private SQLiteHelper db;

	public LogAdapter(Context context, ArrayList<LogItem> log) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.log = log;
		db = new SQLiteHelper(context);
	}

	@Override
	public int getCount() {
		return log.size();
	}

	@Override
	public Object getItem(int position) {
		return log.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LogItem item = log.get(position);
		if (view == null) {
			view = inflater.inflate(R.layout.log_list_row, parent, false);
		}
		final View returnView = view;

		// returnView.setBackgroundColor(onehabit.getStatus());
		final TextView stat_name = (TextView) returnView
				.findViewById(R.id.log_content);
		stat_name.setText(item.getContent());

		final TextView stat_count = (TextView) returnView
				.findViewById(R.id.log_date);
		stat_count.setText(item.getDate_time());

		return returnView;
	}

}
