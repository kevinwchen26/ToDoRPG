package com.cs429.todorpg.revised.controller;

import java.io.IOException;

import com.cs429.todoprg.service.BluetoothService;
import com.cs429.todorpg.revised.BattleActivity;
import com.cs429.todorpg.revised.BattleMainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class BTMessageHandler extends Handler{

	public final static int MESSAGE_PERMISSION = 0;
	public final static int MESSAGE_CONNECTION_FAIL = 1;
	public final static int MESSAGE_CONNECTION_REQUEST = 2;
	public final static int MESSAGE_CONNECTION_SETTLED = 3;
	
	private static BTMessageHandler mHandler;
	
	private String TAG = "BTHandler";
	
	private Context appContext;
	private ProgressDialog mDialog;
	private BluetoothService BTService;
	
	private BTMessageHandler(Context context){
		appContext = context;
		mDialog = new ProgressDialog(context);
	}
	
	public static BTMessageHandler getInstance(Context context){
		if(mHandler == null)
			mHandler = new BTMessageHandler(context);
		return mHandler;
	}
	
	public void flush(){
		mHandler = null;
		appContext = null;
		mDialog = null;
	}
	
	public void setBTService(BluetoothService service){
		BTService = service;
	}
	
	@Override
	public void handleMessage(Message msg){
		switch(msg.what){
		
		case MESSAGE_CONNECTION_REQUEST:
			this.showYesNoDialog("You have a request for battle, Will you accept it?", (BluetoothSocket)msg.obj);
			break;
		
		case MESSAGE_PERMISSION:
			new Handler().post(new Runnable(){
				@Override
				public void run(){
					long starttime = System.currentTimeMillis();
					
					mDialog.setTitle("Bluetooth Connection");
					mDialog.setMessage("connecting to your friend");
					mDialog.setCancelable(false);
					mDialog.show();
		
					while(mDialog.isShowing()){
						long currenttime = System.currentTimeMillis();
						//mDialog = ProgressDialog.show(appContext, "Bluetooth Connection", "connecting to your friend", true);
						if(currenttime - starttime > 5000){
							mHandler.obtainMessage(BTMessageHandler.MESSAGE_CONNECTION_FAIL, 0, 0).sendToTarget();
							mDialog.dismiss();
						}
					}
				}
			});
			break;
		
		case MESSAGE_CONNECTION_FAIL:
			Log.d(TAG, "connection failure: " + msg.arg1 + ", " + msg.arg2);
			mDialog.dismiss();
			connection_failure(msg.arg1, msg.arg2);
			break;
			
		case MESSAGE_CONNECTION_SETTLED:
			Intent intent = new Intent(appContext, BattleActivity.class);
			appContext.startActivity(intent);
			break;
			
		default:
			break;
		}
	}
	
	
	private void connection_failure(int arg1, int arg2){
		//connection time out
		if(arg1 == 0 && arg2 == 0){
			showAlertDialog("Connection time out");
			BTService.start();
		}
		//invalid address connection trial
		else if(arg1 == 0 && arg2 == 1){
			showAlertDialog("The friend is not valid");
			BTService.start();
		}
		//connection denied
		else if(arg1 == 1 && arg2 == 1){
			showAlertDialog("Connection Denied");
			BTService.start();
		}
	}
	
	private void showAlertDialog(String message){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(appContext);
		ab.setTitle("connection failure");
		ab.setMessage(message);
		ab.setPositiveButton("OK", null);
		ab.show();
	}
	
	private void showYesNoDialog(String message, final BluetoothSocket socket){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder((Activity)appContext);
		ab.setTitle("connection failure");
		ab.setMessage(message);
		ab.setCancelable(false);
		ab.setPositiveButton("YES", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				try {
					socket.getOutputStream().write(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				BTService.acceptThreadConsequence(socket);
			}
		});
		ab.setNegativeButton("NO", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int id){
				dialog.dismiss();
				try {
					socket.getOutputStream().write(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		ab.show();
	}
	
}
