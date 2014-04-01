package com.example.avatarcreator;

import android.app.Application;

public class UserInfo extends Application {

	private String username;
	private Character myCharacter;
	private String profileID;
	private boolean loggedIn;
	private boolean characterCreated;



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
