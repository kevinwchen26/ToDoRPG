package com.cs429.todorpg.revised.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cs429.todoprg.service.BluetoothService;
import com.cs429.todorpg.revised.Avatar;
import com.cs429.todorpg.revised.BattleActivity;
import com.cs429.todorpg.revised.model.ToDoCharacter;

public class BTMessageHandler extends Handler{

	public final static int MESSAGE_PERMISSION = 0;
	public final static int MESSAGE_CONNECTION_FAIL = 1;
	public final static int MESSAGE_CONNECTION_REQUEST = 2;
	public final static int MESSAGE_CONNECTION_SETTLED = 3;
	public final static int MESSAGE_BATTLE_SEND = 4;
	public final static int MESSAGE_BATTLE_ACKNOWLEDGE = 5;
	
	public final static int MESSAGE_SHARE_CHAR_INFO = 9;
	
	
	private static BTMessageHandler mHandler;
	
	private String TAG = "BTHandler";
	
	private Context appContext;
	private ProgressDialog mDialog;
	private BluetoothService BTService;
	
	private boolean isMyTurn;
	private boolean readyToStart;
	
	private BTMessageHandler(Context context){
		appContext = context;
		mDialog = new ProgressDialog(context);
		isMyTurn = false;
		setReadyToStart(false);
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
	
	public boolean toggleMyTurn(){
		isMyTurn = !isMyTurn;
		return isMyTurn;
	}
	
	public boolean getMyTurn(){
		return isMyTurn;
	}
	
	public void changeContext(Context context){
		appContext = context;
	}
	
	public void callJesus() {
		((BattleActivity)appContext).jesus();
	}
	
	public void battleToast(String s) {
		((BattleActivity)appContext).battleToast(s);
	}
	
	private void setEnemyImage(Avatar enemyAv) {
		((BattleActivity)appContext).setEnemyImage(enemyAv);
	}
	
	public void setEnemyInfo(ToDoCharacter c) {
		((BattleActivity)appContext).setEnemyInfo(c);
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
			
		case MESSAGE_BATTLE_SEND:
			byte[] sendmsg = new byte[128];
			//protocol: 0x01 is invoking an action...
			sendmsg[0] = 0x01;
			
			Log.d(TAG, "send a message with invoking an action: " + sendmsg[0]);
			BTService.write(sendmsg);
			callJesus();
			break;
			
		case MESSAGE_BATTLE_ACKNOWLEDGE:
			byte[] ackmsg = new byte[128];
			//protocol: 0x11 is acknowledging or assuring message arrival
			ackmsg[0] = 0x11;

			Log.d(TAG, "send a message with acknowledging: " + ackmsg.toString());
			BTService.write(ackmsg);
			break;
		case MESSAGE_SHARE_CHAR_INFO:
			byte[] myBytes = (byte[]) msg.obj;
			ByteArrayInputStream bis = new ByteArrayInputStream(myBytes);
			ObjectInput in = null;
			try {
				in = new ObjectInputStream(bis);
				Avatar enemyAv = (Avatar)in.readObject();
				
				// Initialize BattleActivity
				setEnemyImage(enemyAv);
				setEnemyInfo(enemyAv.getToDoCharacter());
				
				bis.close();
				in.close();
				
				// Now the player is ready for battle
				setReadyToStart(true);
				
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		default:
			break;
		}
	}
	
	
	

	private void connection_failure(int arg1, int arg2){
		//connection time out
		if(arg1 == 0 && arg2 == 0){
			showAlertDialog("Connection time out");
			BTService.resetState();
			BTService.start();
		}
		//invalid address connection trial
		else if(arg1 == 0 && arg2 == 1){
			Log.d(TAG, "connection invalid");
			showAlertDialog("The friend is not valid");
			BTService.resetState();
			BTService.start();
		}
		//connection denied
		else if(arg1 == 1 && arg2 == 1){
			Log.d(TAG, "connection denied");
			showAlertDialog("Connection Denied");
			BTService.resetState();
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
		ab = new AlertDialog.Builder(appContext);
		ab.setTitle("connection request");
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
				BTService.resetState();
				BTService.start();
			}
		});
		ab.show();
	}

	public boolean isReadyToStart() {
		return readyToStart;
	}

	public void setReadyToStart(boolean readyToStart) {
		this.readyToStart = readyToStart;
	}
	
}
