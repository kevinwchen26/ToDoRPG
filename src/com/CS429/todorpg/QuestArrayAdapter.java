package com.CS429.todorpg;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author Paul Kim
 * 
 * Custom ArrayAdapter for the main list view used in QuestInfo
 *
 */
public class QuestArrayAdapter extends ArrayAdapter<JSONObject> {
	private final Context context;
	private int layout;
	private JSONObject [] quests;
 
	public QuestArrayAdapter(Context context, int layoutResourceId, JSONObject[] quests) {
		super(context, layoutResourceId, quests);
		this.context = context;
		this.quests = quests;
		this.layout = layoutResourceId;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		JSONObject questJson = quests[position];
		
		// Get quest_title and quest_description
		View rowView = inflater.inflate(layout, parent, false);
		TextView questTitle = (TextView) rowView.findViewById(R.id.questTitle);
		TextView questDescription = (TextView) rowView.findViewById(R.id.questDescription);
		try {
			questTitle.setText(questJson.getString("quest_title"));
			questDescription.setText(questJson.getString("quest_description"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rowView;
	}
}
