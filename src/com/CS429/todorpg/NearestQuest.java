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

	private JSONParser jsonparser;
	private JSONObject json;
	
	/**
	 * Initialization
	 */
	private void init(){
		jsonparser = new JSONParser();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		json = jsonparser.makeHttpRequest(
				StaticClass.url_get_quests, "GET", params);
		//test printing
		Log.d("QUEST", json.toString());
	}
	
	public JSONArray getQuests(){
//		try {
//			JSONArray data = json.getJSONArray("all_quest_info");
//			
//			//test printing
//			Log.d("QUEST", data.toString());
//			return data;
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		return null;
	}

	@Override
	protected String doInBackground(String... arg0) {
		init();
		return null;
	}
	
}
