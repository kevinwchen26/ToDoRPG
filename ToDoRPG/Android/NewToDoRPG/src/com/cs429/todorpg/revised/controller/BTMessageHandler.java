package com.cs429.todorpg.revised.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
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

import com.cs429.todorpg.battlelogic.AttackResult;
import com.cs429.todorpg.revised.Avatar;
import com.cs429.todorpg.revised.BattleActivity;
import com.cs429.todorpg.revised.model.ToDoCharacter;
import com.cs429.todorpg.service.BluetoothService;

/**
 * This is a handler that handles message sent back and forth between two connected devices
 * 
 * @author ssong25
 *
 */
public class BTMessageHandler extends Handler{

	/*Constants*/
	public final static int MESSAGE_PERMISSION = 0;
	public final static int MESSAGE_CONNECTION_FAIL = 1;
	public final static int MESSAGE_CONNECTION_REQUEST = 2;
	public final static int MESSAGE_CONNECTION_SETTLED = 3;
	public final static int MESSAGE_BATTLE_SEND = 4;
	public final static int MESSAGE_BATTLE_ACKNOWLEDGE = 5;
	public final static int MESSAGE_BATTLE_END = 6;
	
	public final static int MESSAGE_SHARE_CHAR_INFO = 9;
	
	public final static int RECEIVE_ENEMY_CHAR_INFO = 100;
	public final static int RECEIVE_ATTACK = 101;
	
	
	/*Variables*/
	private static BTMessageHandler mHandler;
	
	private String TAG = "BTHandler";
	
	private Context myContext;
	private ProgressDialog mDialog;
	private BluetoothService BTService;
	
	private boolean isMyTurn;
	private boolean readyToStart;
	
	/**
	 * private constructor
	 * 
	 * @param context: context of an activity which a handler binds to 
	 */
	private BTMessageHandler(Context context){
		myContext = context;
		mDialog = new ProgressDialog(context);
		isMyTurn = false;
		setReadyToStart(false);
	}
	
	/**
	 * static method that gets BTMessageHandler.
	 * if none of the handler has been initialized before, outputs new one
	 * 
	 * @param context: context of an activity which a handler binds to
	 * @return
	 */
	public static BTMessageHandler getInstance(Context context){
		if(mHandler == null)
			mHandler = new BTMessageHandler(context);
		return mHandler;
	}
	
	/**
	 * destroys the current handler
	 */
	public void flush(){
		mHandler = null;
		myContext = null;
		mDialog = null;
	}
	
	/**
	 * This sets BluetoothService to a handler
	 * 
	 * @param service: BluetoothService which this handler handles with
	 */
	public void setBTService(BluetoothService service){
		BTService = service;
	}
	
	/**
	 * This is only for game battle turn.
	 * This sets my turn synchronized with the enemy's turn
	 * 
	 * @return true if it is my turn, false otherwise
	 */
	public boolean toggleMyTurn(){
		isMyTurn = !isMyTurn;
		return isMyTurn;
	}
	
	/**
	 * getter method of my turn
	 * 
	 * @return: boolean myTurn
	 */
	public boolean getMyTurn(){
		return isMyTurn;
	}
	
	/**
	 * As this handler is static, it could be used cross activities.
	 * This method is to change a context which a handler should bind to
	 * 
	 * @param context: context of an activity that currently is running
	 */
	public void changeContext(Context context){
		myContext = context;
	}
	
	/**
	 * call back method to battletoast mehod in BattleActivity
	 * 
	 * @param s: toast message
	 */
	public void battleToast(String s) {
		((BattleActivity)myContext).battleToast(s);
	}
	
	/**
	 * call back method to setEnemyImage at BattleActivity
	 * 
	 * @param enemyAv: enemy avatar
	 */
	private void setEnemyImage(Avatar enemyAv) {
		((BattleActivity)myContext).setEnemyImage(enemyAv);
	}
	
	/**
	 * call back method to stEnemyinfo at BattleActivity
	 * 
	 * @param c: ToDoCharacter
	 */
	public void setEnemyInfo(ToDoCharacter c) {
		((BattleActivity)myContext).setEnemyInfo(c);
	}
	
	/**
	 * call back method to defendAttack at BattleActivity
	 * 
	 * @param attackResult
	 */
	public void defendAttack(AttackResult attackResult) {
		((BattleActivity)myContext).defendAttack(attackResult);
	}
	
	/**
	 * This handles all the messages between two connected devices
	 */
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
			Intent intent = new Intent(myContext, BattleActivity.class);
			myContext.startActivity(intent);
			((Activity)myContext).finish();
			
			break;
			
		case MESSAGE_BATTLE_SEND: // Recieve an attack result
			
			Log.d("BTMESSAGE_HANDLER", "Send Attackto BT service");
			BTService.writeObject(msg.obj);
			toggleMyTurn();
			
			break;
			
		case MESSAGE_BATTLE_ACKNOWLEDGE:
			byte[] ackmsg = new byte[128];
			//protocol: 0x11 is acknowledging or assuring message arrival
			ackmsg[0] = 0x11;

			Log.d(TAG, "send a message with acknowledging: " + ackmsg.toString());
			battleToast("Got Attacked");
			BTService.write(ackmsg);
			break;
			
		case MESSAGE_BATTLE_END:
			BTService.stop();
			break;
			
		case MESSAGE_SHARE_CHAR_INFO:
			Log.d("BTMESSAGE_HANDLER", "Send Attackto BT service");
			BTService.writeObject(msg.obj);
			break;
		case RECEIVE_ENEMY_CHAR_INFO:
			Avatar enemyAv = (Avatar)msg.obj;
			battleToast(enemyAv.getToDoCharacter().getName() + " is the enemy");
			setReadyToStart(true);
			// Initialize BattleActivity
			setEnemyImage(enemyAv);
			setEnemyInfo(enemyAv.getToDoCharacter());
			
			break;
		case RECEIVE_ATTACK:
			AttackResult attackResult = (AttackResult)msg.obj;
			defendAttack(attackResult);
			toggleMyTurn();
			
			break;
			
		default:
			break;
		}
	}
	
	
	
	/**
	 * This handles the output and the sequence of connection failure
	 * 
	 * @param arg1
	 * @param arg2
	 */
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
	
	/**
	 * This shows a dialog
	 * 
	 * @param message: string to be displayed on the dialog
	 */
	private void showAlertDialog(String message){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(myContext);
		ab.setTitle("connection failure");
		ab.setMessage(message);
		ab.setPositiveButton("OK", null);
		ab.show();
	}
	
	/**
	 * This shows a dialog with yes / no choices
	 * 
	 * @param message: string to be displayed on the dialog
	 * @param socket: bluetooth socket to be connected to a remote device
	 */
	private void showYesNoDialog(String message, final BluetoothSocket socket){
		AlertDialog.Builder ab = null;
		ab = new AlertDialog.Builder(myContext);
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

	/**
	 * method to get if the device is ready to start
	 * 
	 * @return true if my device is ready, false otherwise
	 */
	public boolean isReadyToStart() {
		return readyToStart;
	}

	/**
	 * setter method of notifying my device is ready to start
	 * 
	 * @param readyToStart
	 */
	public void setReadyToStart(boolean readyToStart) {
		this.readyToStart = readyToStart;
	}
	
	/**
	 * parser to convert from byte array to an object
	 * 
	 * @param myBytes
	 * @return converted object
	 */
	public Object getObjectFromBytes(byte [] myBytes) {
		ByteArrayInputStream bis = new ByteArrayInputStream(myBytes);
		Object retObject = null;
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			retObject = in.readObject();
			
			bis.close();
			in.close();
			
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
		return retObject;
	}
}
