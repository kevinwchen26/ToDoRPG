package com.cs429.todorpg.revised.controller;

import com.cs429.todorpg.revised.BattleMainActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * This is a handler that checks the availability of blue tooth connection at a device
 * 
 * @author ssong25
 *
 */
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
	
	/**
	 * static method get the object of BTControl handler
	 * if none of BTControl handler has been initialized, new one will be returned
	 * 
	 * @param context: context of an activity to bind to this handler
	 * @return BTControl handler object
	 */
	public static BTControl getInstance(Context context){
		if(instance == null){
			instance = new BTControl(context);
		}
		return instance;
	}
	
	/**
	 * private constructor
	 * 
	 * @param context: context of an activity to bind to a handler
	 */
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
	
	/**
	 * This checks if bluetooth is on at a device
	 * 
	 * @return true if enabled, false otherwise
	 */
	public boolean IsBluetoothEnabled(){
		return myBluetoothAdapter.isEnabled();
	}
	
	/**
	 * This gets blue tooth adapter encapsulated in a device
	 * 
	 * @return BluetoothAdapter
	 */
	public BluetoothAdapter getAdapter(){
		return myBluetoothAdapter;
	}
	
	/**
	 * This sets mac address to a handler
	 * 
	 * @param address: mac address of a remote device an app is connecting to
	 */
	public void set_address(String address){
		this.mac_address = address;
	}
	
	/**
	 * This gets mac address from a handler
	 * 
	 * @return mac address of a remote device and app is connecting to
	 */
	public String get_address(){
		return mac_address;
	}
	
	/**
	 * This sets BluetoothDevice to a handler
	 * 
	 * @param selected: a remote device an app is connecting to 
	 */
	public void setDevice(BluetoothDevice selected){
		if(selected == null)
			Log.d("[BluetoothControl]", "setDevice: device is null");
		else
			Log.d("[BluetoothControl]", "setDevice: " + selected.getAddress());
		device = selected;
	}
	
	/**
	 * This gets BluetoothDevice from a handler
	 * 
	 * @return BlueToothDevice, a remote device an app is connecting to
	 */
	public BluetoothDevice getDevice(){
		if(device == null)
			Log.d("[BluetoothControl]", "getDevice: device is null");
		else
			Log.d("[BluetoothControl]", "getDevice: " + device.getAddress());
		return device;
	}
}
