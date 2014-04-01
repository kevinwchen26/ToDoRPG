package com.CS429.todorpg;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.CS429.todorpg.Class.Character;

public class StaticClass {
	/* URL */
	public final static String url_create_account = "http://todorpg.net46.net/ToDoRPG/create_account.php";
	public final static String url_get_all_account_info = "http://todorpg.net46.net/ToDoRPG/get_all_account_info.php";
	public final static String url_create_character = "http://todorpg.net46.net/ToDoRPG/create_character.php";
	public final static String url_get_character_info = "http://todorpg.net46.net/ToDoRPG/get_character_info.php";
	public final static String url_create_quest = "http://todorpg.net46.net/ToDoRPG/create_quest.php";
	public final static String url_get_quests = "http://todorpg.net46.net/ToDoRPG/get_quests.php";
	public final static String url_update_party = "http://todorpg.net46.net/ToDoRPG/add_party_member.php";
	public final static String url_get_users_quest = "http://todorpg.net46.net/ToDoRPG/get_users_quest.php";
	public final static String url_update_quest = "http://todorpg.net46.net/ToDoRPG/update_quest.php";
	public final static String url_delete_quest = "http://todorpg.net46.net/ToDoRPG/delete_quest.php";
	public final static String url_update_quest_member = "http://todorpg.net46.net/ToDoRPG/update_quest_member.php";
	public final static String url_update_work_status = "http://todorpg.net46.net/ToDoRPG/update_work_status.php";
	
	
	/* Shared Preferences Keys */
	public final static String MY_PREFERENCES = ",yPrefs";
	public final static String PREF_USERNAME = "prefUserName";
	public final static String PREF_IS_LOGGED_IN = "pref_is_logged_in";
	
	public final static String PREF_CHARACTER_NAME = "character_name";
	public final static String PREF_CHARACTER_STR = "character_str";
	public final static String PREF_CHARACTER_CON = "character_con";
	public final static String PREF_CHARACTER_DEX = "character_dex";
	public final static String PREF_CHARACTER_INT = "character_int";
	public final static String PREF_CHARACTER_WIS = "character_wis";
	public final static String PREF_CHARACTER_CHA = "character_cha";
	public final static String PREF_CHARACTER_LEVEL = "character_level";
	public final static String PREF_CHARACTER_CLASS = "character_class";
	public final static String PREF_CHARACTER_EXISTS = "character_exists"; // Does the user have a character
	
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_DATA = "log_db";
	public static final String TAG_INFO = "character";
	public static final String TAG_USER_NAME = "user_name";
	public static final float TAG_INIT_EXP = 0.0f;
	public static final String TAG_PASSWORD = "password";
	public static final String ACCOUNT_CREATION_MESSAGE = "Your account has been created successfully";
	public static final String LOGIN_SUCCESS_MESSAGE = "Logged in Successfully";
	public static final String WELCOME_MESSAGE = "Welcome";
	public static final String LOGOUT_MESSAGE = "Logged out Successfully";
	public static final String NEED_LOGIN_MESSAGE = "You have to login first";
	public static final String CHARACTER_CREATE_SUCCESS_MESSAGE = "Character created Successfully";
	public static final String HAVE_CHARACTER_MESSAGE = "You alreay have one character";
	public static final String DONT_HAVE_CHARACTER_MESSAGE = "You don't have a character";
	public static final String TAG_CHECK_INTERNET = "Internet is not connected";
	public static final String QUEST_SUCCESS ="Quest Made!";
	public static final String QUEST_FAIL ="Incomplete Quest!";
	public static final String TAG_ERROR = "ERROR";
	public static final String TAG_CONFIRM_CANCEL = "Do you really want to cancel?";
	public static final String TAG_DELETE = "Do you really want to remove this quest?";
	public static final String TAG_CHECK_REMOVE = "REMOVE";
	public static final String TAG_CANCEL = "CANCEL";
	public static final String TAG_NO_QUEST_WARNING = "You don't have any quest now";
	public static final String TAG_NO_PERMISSION = "You can only see the status of this list";
	public final static String delimiter = "@#$";
	public static final int SINGLE_USER_INFO = 100;
	public static final int ALL_USER_INFO = 200;
	public static final int STATUS_CHANGED = 1000;
	public static int TAG_WORK_STATUS = 1432;
	
	public static final String WARRIOR = "Warrior";
	public static final String ASSASSIN = "Assassin";
	public static final String MAGE = "Mage";
	public static final String ARCHER = "Archer";
	public static final String SUMMONER = "Summoner";
	
	public static final int INIT_LEVEL = 1;
	
	public static final String Warrior_skill_1 = "Warrior Skill 1";
	public static final String Warrior_skill_2 = "Warrior Skill 2";
	public static final String Warrior_skill_3 = "Warrior Skill 3";
	public static final String Warrior_skill_4 = "Warrior Skill 4";
	
	public static final String Assassin_skill_1 = "Assassin Skill 1";
	public static final String Assassin_skill_2 = "Assassin Skill 2";
	public static final String Assassin_skill_3 = "Assassin Skill 3";
	public static final String Assassin_skill_4 = "Assassin Skill 4";
	
	public static final String Mage_skill_1 = "Mage Skill 1";
	public static final String Mage_skill_2 = "Mage Skill 2";
	public static final String Mage_skill_3 = "Mage Skill 3";
	public static final String Mage_skill_4 = "Mage Skill 4";
	
	public static final String Archer_skill_1 = "Archer Skill 1";
	public static final String Archer_skill_2 = "Archer Skill 2";
	public static final String Archer_skill_3 = "Archer Skill 3";
	public static final String Archer_skill_4 = "Archer Skill 4";
	
	public static final String Summoner_skill_1 = "Summon Skill 1";
	public static final String Summoner_skill_2=  "Summon Skill 2";
	public static final String Summoner_skill_3 = "Summon Skill 3";
	public static final String Summoner_skill_4 = "Summon Skill 4";

	public static final int LOGIN_SUCCESS = 100;
	public static String MY_ID;
	public static boolean LOGGED_ID;
	public static boolean CHARACTER_CREATED;
	
	public static Character CLASS_INFO;
	
	public static ArrayList<Quest> myQuest = new ArrayList<Quest>();
	
	public static boolean isNetworkConnected(Activity activity) {
		ConnectivityManager cManager; 
		NetworkInfo mobile; 
		NetworkInfo wifi; 
		 
		// Need workaround 
		cManager=(ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE); 
		//mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		//wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		 if (cManager != null) {
			 NetworkInfo netInfo = cManager.getActiveNetworkInfo();
			 if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				 return true;
			 }
			 else {
				 return false;
			 }
			 
		 }
		 else
			 return false;
		 
		 /*
		if(mobile.isConnected() || wifi.isConnected()) {
			return true;
		}
		else 
			return false;
			*/
	}
	public static AlertDialog GetNetworkDialog(Activity activity) {
	    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(activity) 
	        //set message, title, and icon
	        .setTitle("Warning") 
	        .setMessage(StaticClass.TAG_CHECK_INTERNET) 
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) { 
	                dialog.dismiss();
	            }   
	        })
	        .create();
	        return myQuittingDialogBox;
	}
	
	public static AlertDialog sendAlertMessage(Activity activity, String title, String msg) {
	    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(activity) 
	        //set message, title, and icon
	        .setTitle("Warning") 
	        .setMessage(msg) 
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) { 
	                dialog.dismiss();
	            }   
	        })
	        .create();
	        return myQuittingDialogBox;
	}
}
