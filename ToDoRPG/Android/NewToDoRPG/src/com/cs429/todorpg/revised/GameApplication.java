package com.cs429.todorpg.revised;

import android.app.Application;
import android.content.Context;

/**
 * Keeps control of the Avatar
 * 
 * @author Leon Chen
 * 
 */
public class GameApplication extends Application {
	private static GameApplication mInstance;
	private static Context mAppContext;
	public Avatar avatar;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		this.setAppContext(getApplicationContext());
		this.avatar = new Avatar();

	}

	/**
	 * returns the instance of the gameApplication
	 * 
	 * @return mInstance
	 */
	public static GameApplication getInstance() {
		return mInstance;
	}

	/**
	 * returns the Context of this application
	 * 
	 * @return mAppContext
	 */
	public static Context getAppContext() {
		return mAppContext;
	}

	@SuppressWarnings("static-access")
	public void setAppContext(Context mAppContext) {
		this.mAppContext = mAppContext;
	}

}
