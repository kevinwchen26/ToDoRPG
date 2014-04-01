package com.example.avatarcreator;

import android.app.Application;
import android.content.Context;

public class UserInfo extends Application {

	private String username;
	private Character myCharacter;
	private String profileID;
	private boolean loggedIn;
	private boolean characterCreated;
	
	private static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}
	
	public static Context getContext(){
        return mContext;
    }
	
	// Inventory
	public static Avatar avatar;
	public static Inventory inventory;

	public void login() {
		this.loggedIn=true;
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
