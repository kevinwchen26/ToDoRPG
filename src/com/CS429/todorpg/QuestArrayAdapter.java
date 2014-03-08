package com.CS429.todorpg;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author paulkim6 / jcheng26
 * 	edited by hlim10 / partner 1
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
		
		// Each quest JSONObject comes in a form like this:
		// {"quest_location_lat" : "", "quest_desciption":"Finish the 473 Homework before Unoffical",
		// "quest_title":"473 Homework Rush", "creater_name":"Patrick", "quest_difficulty":"1",
		// "quest_duration":"5", "quest_milestone":"Milestone1_ Milestone2_", "quest_id":"14", "quest_location_long":"",
		// "quest_status":"INACTIVE"}
		// WARNING: FOR NOW ALL VALUES ARE STRING. CONVERT TO INT / DOUBLE BEFORE USE.
		
		// Get quest_title and quest_description for now.
		View rowView = inflater.inflate(layout, parent, false);
		TextView questTitle = (TextView) rowView.findViewById(R.id.questTitle);
		TextView creator_id = (TextView) rowView.findViewById(R.id.creator_id);
		TextView isQuestActive = (TextView) rowView.findViewById(R.id.isQuestActive);
		try {
			questTitle.setText(questJson.getString("quest_title"));
			creator_id.setText(questJson.getString("creator_name"));
			String questStatus = questJson.getString("quest_status");
			if ("ACTIVE".equals(questStatus))
				isQuestActive.setTextColor(Color.RED);
			else
				isQuestActive.setTextColor(Color.parseColor("#939393"));
			
			isQuestActive.setText(questStatus);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rowView;
	}
}
