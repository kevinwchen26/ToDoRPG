package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.model.LogItem;
/**
 * Log Adapter
 * @author kchen26, hlim10
 *
 */
public class LogAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	public ArrayList<LogItem> log;

	public LogAdapter(Context context, ArrayList<LogItem> log) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.log = log;
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
	/**
	 * Shows the details of Event Log
	 */
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
