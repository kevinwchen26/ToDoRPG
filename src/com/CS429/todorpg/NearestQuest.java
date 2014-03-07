package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.CS429.todorpg.Utils.JSONParser;

public class NearestQuest extends AsyncTask<String, String, String>{

	private JSONParser jsonparser = new JSONParser();
	private JSONObject json;
	private static JSONArray quests;
	
	@Override
	protected String doInBackground(String... arg0) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		json = jsonparser.makeHttpRequest(
				StaticClass.url_get_quests, "GET", params);
		//test printing
		Log.d("QUEST", json.toString());
		
		try {
			if(json == null)
				Log.d("TESTING", "json is null, what is going on??");
			
			quests = json.getJSONArray("all_quest_info");
	
			//test printing
			Log.d("QUEST", quests.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return quests.toString();
	}
	
	public JSONArray getQuests(){
		if(quests == null)
			Log.d("TEST", "quest is null");
		
		return quests;
	}
	
}
