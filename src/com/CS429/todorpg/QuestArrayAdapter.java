package com.CS429.todorpg;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

/**
 * 
 * @author paulkim6 / jcheng26 edited by hlim10
 * 
 * 
 *         Custom ArrayAdapter for the main list view used in QuestInfo
 * 
 */
public class QuestArrayAdapter extends BaseAdapter implements Filterable {
	private final Context context;
	private ArrayList<Quest> originalData;
	private ArrayList<Quest> filteredData;
	static ArrayList<Quest> filterResultsData;
	private LayoutInflater inflater;
	static boolean isFiltered = false; // To check
	int count = 15; // Starting data amount
	int filtered_size = 0;

	public QuestArrayAdapter(Context context, ArrayList<Quest> arrData) {
		this.context = context;
		this.originalData = arrData;
		this.filteredData = arrData;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (isFiltered) {
			filtered_size = filteredData.size();
			return filtered_size;
		} else {
			if (count >= originalData.size())
				count = originalData.size();
			return count;
		}
	}

	@Override
	public Object getItem(int position) {
		if (isFiltered)
			return filteredData.get(position);
		else
			return originalData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.quest_row, parent, false);
		}

		ArrayList<Quest> data = (isFiltered ? filteredData : originalData);

		TextView title = (TextView) convertView.findViewById(R.id.questTitle);
		TextView creator_id = (TextView) convertView
				.findViewById(R.id.creator_id);
		TextView isQuestActive = (TextView) convertView
				.findViewById(R.id.isQuestActive);

		String getTitle = data.get(position).getTitle();
		if (getTitle.length() > 25) {
			String subTitle = getTitle.substring(0, 25);
			subTitle = subTitle.concat("...");
			title.setText(subTitle);
		} else {
			title.setText(getTitle);
		}

		creator_id.setText(data.get(position).getLeader());
		isQuestActive.setText(data.get(position).getStatus());
	
		if ("ACTIVE".equals(data.get(position).getStatus()))
			isQuestActive.setTextColor(Color.RED);
		else
			isQuestActive.setTextColor(Color.parseColor("#939393"));

		// TODO MAKE DATE LATER...
		/*
		 * TextView date =
		 * (TextView)convertView.findViewById(R.id.list_layout_date);
		 * date.setText(data.get(position).getDate());
		 */
		return convertView;
	}

	/* Filtering Handler */
	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				FilterResults results = new FilterResults();

				if (charSequence == null || charSequence.length() == 0) {
					isFiltered = false;
					results.values = originalData;
					results.count = originalData.size();
				} else {
					isFiltered = true;
					filterResultsData = new ArrayList<Quest>();
					for (Quest data : originalData) {
						if (data.getTitle().contains(charSequence)
								|| data.getLeader().contains(charSequence) || data.getStatus().contains(charSequence)) {
							filterResultsData.add(data);
						}
					}
					results.values = filterResultsData;
					results.count = filterResultsData.size();
				}
				return results;
			}

			@Override
			protected void publishResults(CharSequence charSequence,
					FilterResults filterResults) {
				filteredData = (ArrayList<Quest>) filterResults.values;
				notifyDataSetChanged();
			}
		};
	}
}
