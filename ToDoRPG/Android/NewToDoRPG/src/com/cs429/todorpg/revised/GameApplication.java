package com.cs429.todorpg.revised;

import android.app.Application;
import android.content.Context;

public class GameApplication extends Application {
    private static GameApplication mInstance;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        this.setAppContext(getApplicationContext());
    }

    public static GameApplication getInstance(){
        return mInstance;
    }
    public static Context getAppContext() {
        return mAppContext;
    }
    @SuppressWarnings("static-access")
	public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }
}
