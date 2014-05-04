package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.model.Stat;

/**
 * Stat Adapter
 * 
 * @author kchen26, hlim10
 * 
 */
public class StatAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<Stat> stats;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param stats
	 */
	public StatAdapter(Context context, ArrayList<Stat> stats) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.stats = stats;
	}

	/**
	 * Get Count of Stat List
	 */
	@Override
	public int getCount() {
		return stats.size();
	}

	/**
	 * Get selected item of Stat List
	 */
	@Override
	public Object getItem(int position) {
		return stats.get(position);
	}

	/**
	 * Get selected item id of Stat List
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Shows the details of stat
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Stat stat = stats.get(position);
		if (view == null) {
			view = inflater.inflate(R.layout.stat_list_row, parent, false);
		}
		final View returnView = view;

		// returnView.setBackgroundColor(onehabit.getStatus());
		final TextView stat_name = (TextView) returnView
				.findViewById(R.id.stat_name);
		stat_name.setText(stat.getName());

		final TextView stat_count = (TextView) returnView
				.findViewById(R.id.stat_count);
		stat_count.setText(stat.getCount() + "");

		return returnView;
	}

}
