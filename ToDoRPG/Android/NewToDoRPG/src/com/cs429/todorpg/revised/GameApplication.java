package com.cs429.todorpg.revised;

import java.util.ArrayList;

import com.cs429.todorpg.revised.itemsystem.Armor;
import com.cs429.todorpg.revised.itemsystem.Helmet;
import com.cs429.todorpg.revised.itemsystem.NegativeEffects;
import com.cs429.todorpg.revised.itemsystem.PositiveEffects;
import com.cs429.todorpg.revised.itemsystem.Shield;
import com.cs429.todorpg.revised.itemsystem.Weapon;

import android.app.Application;
import android.content.Context;

public class GameApplication extends Application {
    private static GameApplication mInstance;
    private static Context mAppContext;
    public Avatar avatar;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.setAppContext(getApplicationContext());
        this.avatar=new Avatar();

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
