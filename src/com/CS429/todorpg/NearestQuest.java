package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.CS429.todorpg.Utils.JSONParser;

public class NearestQuest {

	private static NearestQuest quests;
	private JSONParser jsonparser;
	private JSONObject json;
	
	/**
	 * Constructor
	 */
	private NearestQuest(){
		init();
	}
	
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
	
	/**
	 * 
	 * @return
	 */
	public static synchronized NearestQuest getInstance(){
		if(quests == null)
			quests = new NearestQuest();
		return quests;
	}
	
	
	public JSONArray getQuests(){
		try {
			JSONArray data = json.getJSONArray("all_quest_info");
			
			//test printing
			Log.d("QUEST", data.toString());
			return data;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
