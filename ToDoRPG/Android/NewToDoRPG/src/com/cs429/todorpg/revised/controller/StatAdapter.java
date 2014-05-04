package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.model.Stat;
import com.cs429.todorpg.revised.utils.SQLiteHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
/**
 * 
 * @author kchen26, hlim10
 *
 */
public class StatAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Stat> stats;
	private SQLiteHelper db;

	public StatAdapter(Context context, ArrayList<Stat> stats) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.stats = stats;
		db = new SQLiteHelper(context);
	}

	@Override
	public int getCount() {
		return stats.size();
	}

	@Override
	public Object getItem(int position) {
		return stats.get(position);
	}

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
		stat_count.setText(stat.getCount()+"");

		return returnView;
	}

}
