package com.CS429.todorpg;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.CS429.todorpg.Class.Character;
import com.CS429.todorpg.Utils.Constants;

public class UserInfo extends Application {

	private String username;
	private Character myCharacter;
	private String profileID;
	private boolean loggedIn;
	private boolean characterCreated;

	public static ArrayList<Quest> myQuest = new ArrayList<Quest>();

	public static boolean isNetworkConnected(Activity activity) {
		ConnectivityManager cManager;
		NetworkInfo mobile;
		NetworkInfo wifi;

		// Need workaround
		cManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		// mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		// wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (cManager != null) {
			NetworkInfo netInfo = cManager.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				return true;
			} else {
				return false;
			}

		} else
			return false;

		/*
		 * if(mobile.isConnected() || wifi.isConnected()) { return true; } else
		 * return false;
		 */
	}

	public static AlertDialog GetNetworkDialog(Activity activity) {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(activity)
		// set message, title, and icon
				.setTitle("Warning").setMessage(Constants.TAG_CHECK_INTERNET).setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).create();
		return myQuittingDialogBox;
	}

	public static AlertDialog sendAlertMessage(Activity activity, String title, String msg) {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(activity)
		// set message, title, and icon
				.setTitle("Warning").setMessage(msg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).create();
		return myQuittingDialogBox;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public void setProfileID(String id) {
		this.profileID = id;
	}

	public void login() {
		this.loggedIn=true;
	}

	public void createCharacter(String name, int str, int con, int dex, int intel, int wis, int charisma, int level, String job) {
		this.myCharacter=new Character(name,str,con,dex,intel,wis,charisma,level,job);
		this.characterCreated=true;
		
	}

	public String getUserName() {
		return this.username;
	}

	public void cancelCharacterCreate() {
		this.characterCreated=false;
	}

	public String getProfileID() {
		return this.profileID;
	}

	public boolean isLoggedIn() {
		return this.loggedIn;
	}
	
	public boolean hasCharacter(){
		return this.characterCreated;
	}

	public void logout() {
		this.loggedIn=false;
	}

	public Character getCharacter() {
		return this.myCharacter;
	}
	
	public void createdCharacter(){
		this.characterCreated=true;
	}
}
