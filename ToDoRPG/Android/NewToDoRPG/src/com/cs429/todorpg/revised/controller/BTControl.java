package com.cs429.todorpg.revised.controller;

import com.cs429.todorpg.revised.BattleMainActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BTControl {

	/* constants for BTControl */
	public static final int REQUEST_ENABLE_BT = 1;
	public static final int CHOOSE_DEVICE = 2;
	
	
	/*variables*/
	private static BTControl instance;
	private static BluetoothAdapter myBluetoothAdapter;
	private static BluetoothDevice device;
	
	private Context appContext;
	private String mac_address;
	
	public static BTControl getInstance(Context context){
		if(instance == null){
			instance = new BTControl(context);
		}
		return instance;
	}
	
	private BTControl(Context context){
		myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		appContext = context;
	}
	
	/**
	 * this checks if the device are able to use bluetooth feature.
	 * This should be called on the onCreate() of the activity.
	 * 
	 * @return true if possible, false otherwise
	 */
	public boolean checkBTEnable(){
		if(myBluetoothAdapter == null)
			return false;
		else if(!myBluetoothAdapter.isEnabled()){
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			((BattleMainActivity) appContext).startActivityForResult(
					enableIntent, REQUEST_ENABLE_BT);
		}
		return myBluetoothAdapter.isEnabled();
	}
	
	public boolean IsBluetoothEnabled(){
		return myBluetoothAdapter.isEnabled();
	}
	
	
	public BluetoothAdapter getAdapter(){
		return myBluetoothAdapter;
	}
	
	public void set_address(String address){
		this.mac_address = address;
	}
	
	public String get_address(){
		return mac_address;
	}
	
	public void setDevice(BluetoothDevice selected){
		if(selected == null)
			Log.d("[BluetoothControl]", "setDevice: device is null");
		else
			Log.d("[BluetoothControl]", "setDevice: " + selected.getAddress());
		device = selected;
	}
	
	public BluetoothDevice getDevice(){
		if(device == null)
			Log.d("[BluetoothControl]", "getDevice: device is null");
		else
			Log.d("[BluetoothControl]", "getDevice: " + device.getAddress());
		return device;
	}
}
