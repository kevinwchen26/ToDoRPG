package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.CS429.todorpg.Utils.Constants;
import com.CS429.todorpg.Utils.JSONParser;

public class NearestQuest extends AsyncTask<String, String, String>{

	private JSONParser jsonparser;
	private JSONObject json;
	private static JSONArray quests;
	private Object lock = new Object();
	
	/**
	 * Initialization
	 */
	private void init(){
		synchronized(lock){
			jsonparser = new JSONParser();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			json = jsonparser.makeHttpRequest(
					Constants.url_get_quests, "GET", params);
			//test printing
			Log.d("QUEST", json.toString());
			
			try {
				quests = json.getJSONArray("all_quest_info");
				
				//test printing
				Log.d("QUEST", quests.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public JSONArray getQuests(){
		synchronized(lock){
			if(quests == null)
				Log.d("QUEST", "quest is null");
			return quests;
		}
	}

	@Override
	protected String doInBackground(String... arg0) {
		init();
		return null;
	}
	
	
	@Override
	protected void onPostExecute(String arg){
	}
	
}
