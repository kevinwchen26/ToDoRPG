package com.CS429.todorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.CS429.todorpg.Utils.Constants;
import com.CS429.todorpg.Utils.JSONParser;

import android.os.AsyncTask;
import android.util.Log;

/* Get Login info */
public class GetAllAccountInfo extends AsyncTask<String, String, String> {
	JSONParser jsonParser = new JSONParser();
	static boolean data_check;
	static JSONArray account = null;

	@Override
	protected String doInBackground(String... args) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject json = jsonParser.makeHttpRequest(
				Constants.url_get_all_account_info, "GET", params);
		Log.d("All Account Info", json.toString());

		int success;
		try {
			success = json.getInt(Constants.TAG_SUCCESS);
			if (success == 1) {
				data_check = true;
				account = json.getJSONArray(Constants.TAG_DATA);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(String file_url) {
	}
}