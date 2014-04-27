package com.cs429.todoprg.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.cs429.todorpg.revised.controller.BTControl;
import com.cs429.todorpg.revised.controller.BTMessageHandler;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


/**
 * Bluetooth Serivce in peer to peer for battle system.
 * AcceptThread, ConnectThread, ConnectedThread 
 * 
 * @author ssong25
 *
 */
public class BluetoothService {
		
		/*Bluetooth states*/
		private static final int STATE_NONE = 0;
		private static final int STATE_LISTEN = 1;
		private static final int STATE_CONNECTING = 2;
		private static final int STATE_CONNECTED = 3;
		
		// Debugging
		private static final String TAG = "Bluetooth_connectService";

		// Name for the SDP record when creating server socket
		private static final String NAME = "Bluetooth_connect";

		// Unique UUID for this application
		private static final UUID MY_UUID = UUID
				.fromString("00001101-0000-1000-8000-00805F9B34FB");

		// Member fields
		private final BluetoothAdapter mAdapter;
		private static boolean D;
		private String address;
		
		private AcceptThread mAcceptThread;
		private ConnectThread mConnectThread;
		private ConnectedThread mConnectedThread;
		private int mState;
		private Context appContext;
		
		private BTMessageHandler mHandler;
		
		/**
		 * Constructor. Prepares a new Bluetooth_connect session.
		 * 
		 * @param context
		 *            The UI Activity Context
		 */
		public BluetoothService(Context context) {
			mAdapter = BTControl.getInstance(context).getAdapter();
			D = BTControl.getInstance(context).IsBluetoothEnabled();
			address = BTControl.getInstance(context).get_address();
			appContext = context;
			mState = STATE_NONE;
			mHandler = BTMessageHandler.getInstance(context);
		}
		
		/**
		 * this sets the state for blue tooth threads
		 * 
		 * @param state
		 */
		private void setState(int state){
			mState = state;
		}
		
		/**
		 * Return the current connection state.
		 */
		public synchronized int getState() {
			return mState;
		}
		
		public synchronized void resetState(){
			mState = STATE_LISTEN;
		}
		
		/**
		 * Start the chat service. Specifically start AcceptThread to begin a
		 * session in listening (server) mode. Called by the Activity onResume()
		 */
		public synchronized void start() {
			Log.d(TAG, "Bluetooth Service start()");
			
			// Cancel any thread attempting to make a connection
			if (mConnectThread != null && mState != STATE_CONNECTING) {
				Log.d(TAG, "connect thread null");
				mConnectThread.cancel();
				mConnectThread = null;
			}

			// Cancel any thread currently running a connection
			if (mConnectedThread != null) {
				Log.d(TAG, "connected thread null");
				mConnectedThread.cancel();
				mConnectedThread = null;
			}

			// Start the thread to listen on a BluetoothServerSocket
			//if connected or connect is null (no trial yet), then wait for connection
			if (mAcceptThread == null) {
				Log.d(TAG, "accept thread reset");
				mAcceptThread = new AcceptThread();
				mAcceptThread.start();
			}
			setState(STATE_LISTEN);
		}

		/**
		 * Start the ConnectThread to initiate a connection to a remote device.
		 * 
		 * @param device
		 *            The BluetoothDevice to connect
		 */
		public synchronized void connect(BluetoothDevice device) {

			// Cancel any thread attempting to make a connection
			if (mState == STATE_CONNECTING) {
				if (mConnectThread != null) {
					mConnectThread.cancel();
					mConnectThread = null;
				}
			}

			// Cancel any thread currently running a connection
			if (mConnectedThread != null) {
				mConnectedThread.cancel();
				mConnectedThread = null;
			}

			// Start the thread to connect with the given device
			mConnectThread = new ConnectThread(device);
			mConnectThread.start();
			setState(STATE_CONNECTING);
		}

		/**
		 * Start the ConnectedThread to begin managing a Bluetooth connection
		 * 
		 * @param socket
		 *            The BluetoothSocket on which the connection was made
		 * @param device
		 *            The BluetoothDevice that has been connected
		 */
		public synchronized void connected(BluetoothSocket socket,
				BluetoothDevice device) {

			// Cancel the thread that completed the connection
			if (mConnectThread != null) {
				mConnectThread.cancel();
				mConnectThread = null;
			}

			// Cancel any thread currently running a connection
			if (mConnectedThread != null) {
				mConnectedThread.cancel();
				mConnectedThread = null;
			}

			// Cancel the accept thread because we only want to connect to one
			// device
			if (mAcceptThread != null) {
				mAcceptThread.cancel();
				mAcceptThread = null;
			}

			// Start the thread to manage the connection and perform transmissions
			mConnectedThread = new ConnectedThread(socket);
			mConnectedThread.start();

			setState(STATE_CONNECTED);
		}

		/**
		 * Stop all threads
		 */
		public synchronized void stop() {
			Log.d("BTHandler", "destroying the connection at all...");
			
			if (mConnectThread != null) {
				mConnectThread.cancel();
				mConnectThread = null;
			}
			if (mConnectedThread != null) {
				mConnectedThread.cancel();
				mConnectedThread = null;
			}
			if (mAcceptThread != null) {
				mAcceptThread.cancel();
				mAcceptThread = null;
			}
			setState(STATE_NONE);
		}

		/**
		 * Write to the ConnectedThread in an unsynchronized manner
		 * 
		 * @param out
		 *            The bytes to write
		 * @see ConnectedThread#write(byte[])
		 */
		public void write(byte[] out) {
			// Create temporary object
			ConnectedThread r;
			// Synchronize a copy of the ConnectedThread
			synchronized (this) {
				if (mState != STATE_CONNECTED)
					return;
				r = mConnectedThread;
			}
			// Perform the write unsynchronized
			r.write(out);
		}

		/*
		public byte[] read(){
			// Create temporary object
			ConnectedThread r;
			// Synchronize a copy of the ConnectedThread
			synchronized (this) {
				if(mState != STATE_CONNECTED)
					return null;
				r = mConnectedThread;
			}
			// Perform the read unsynchronized
			return r.read();
		}
		*/
		
		/**
		 * Indicate that the connection attempt failed and notify the UI Activity.
		 */
		private void connectionFailed(int arg1, int arg2) {
			setState(STATE_LISTEN);
			mHandler.obtainMessage(BTMessageHandler.MESSAGE_CONNECTION_FAIL, arg1, arg2).sendToTarget();
		}

		/**
		 * Indicate that the connection was lost and notify the UI Activity.
		 */
		private void connectionLost() {
			setState(STATE_LISTEN);
		}

		/**
		 * This thread runs while listening for incoming connections. It behaves
		 * like a server-side client. It runs until a connection is accepted (or
		 * until cancelled).
		 */
		private class AcceptThread extends Thread {
			// The local server socket
			private final BluetoothServerSocket mmServerSocket;

			public AcceptThread() {
				Log.d(TAG, "acceptThread start");
				BluetoothServerSocket tmp = null;

				// Create a new listening server socket
				try {
					tmp = mAdapter
							.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
				} catch (IOException e) {
					Log.e(TAG, "listen() failed", e);
				}
				mmServerSocket = tmp;
			}

			@Override
			public void run() {
				if (D)
					Log.d(TAG, "BEGIN mAcceptThread" + this);
				setName("AcceptThread");
				BluetoothSocket socket = null;

				// Listen to the server socket if we're not connected
				while (mState != STATE_CONNECTED) {
					try {
						// This is a blocking call and will only return on a
						// successful connection or an exception
						socket = mmServerSocket.accept();
					} catch (IOException e) {
						Log.e(TAG, "accept() failed", e);
						break;
					}
					
					Log.d(TAG, "receiving a request from a friend..");
					mHandler.obtainMessage(BTMessageHandler.MESSAGE_CONNECTION_REQUEST, socket).sendToTarget();
				}
				if (D)
					Log.i(TAG, "END mAcceptThread");
			}

			public void cancel() {
				if (D)
					Log.d(TAG, "cancel " + this);
				try {
					mmServerSocket.close();
				} catch (IOException e) {
					Log.e(TAG, "close() of server failed", e);
				}
			}
		}

		public void acceptThreadConsequence(BluetoothSocket socket){
			// If a connection was accepted
			if (socket != null) {
				synchronized (BluetoothService.this) {
					switch (mState) {
					case STATE_LISTEN:
					case STATE_CONNECTING:
						// Situation normal. Start the connected thread.
						connected(socket, socket.getRemoteDevice());
						break;
					case STATE_NONE:
					case STATE_CONNECTED:
						// Either not ready or already connected. Terminate
						// new socket.
						try {
							socket.close();
						} catch (IOException e) {
							Log.e(TAG, "Could not close unwanted socket", e);
						}
						break;
					}
				}
			}
		}
		
		/**
		 * This thread runs while attempting to make an outgoing connection with a
		 * device. It runs straight through; the connection either succeeds or
		 * fails.
		 */
		private class ConnectThread extends Thread {
			private final BluetoothSocket mmSocket;
			private final BluetoothDevice mmDevice;

			public ConnectThread(BluetoothDevice device) {
				Log.d(TAG, "connectThread start");
				mmDevice = device;
				BluetoothSocket tmp = null;
				// Get a BluetoothSocket for a connection with the
				// given BluetoothDevice
				try {
					tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
				} catch (IOException e) {
				}
				mmSocket = tmp;
			}

			@Override
			public void run() {
				setName("ConnectThread");
				Looper.prepare();

				// Always cancel discovery because it will slow down a connection
				mAdapter.cancelDiscovery();

				// Make a connection to the BluetoothSocket
				try {
					// This is a blocking call and will only return on a
					// successful connection or an exception
					
//					mHandler.obtainMessage(BTMessageHandler.MESSAGE_PERMISSION).sendToTarget();
					mmSocket.connect();
				} catch (IOException e) {
					//connection failure
					connectionFailed(0, 1);
					// Close the socket
					try {
						mmSocket.close();
					} catch (IOException e2) {
						Log.e(TAG,
								"unable to close() socket during connection failure",
								e2);
					}
					// Start the service over to restart listening mode
					Log.d(TAG, "originally resetting start()?");
					setState(STATE_LISTEN);
					BluetoothService.this.start();
					return;
				}

				int answer = 0;		//0: no , 1: yes for permission from a friend
				// Reset the ConnectThread because we're done
				//wait for the message from a friend
				synchronized (BluetoothService.this) {
					mConnectThread = null;
					try {
						InputStream mmInStream = mmSocket.getInputStream();
						answer = mmInStream.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
					Log.d(TAG, "reading answer: " + answer);
					// Start the connected thread
					if(answer == 1){
						boolean myturn = mHandler.toggleMyTurn();
						if(!myturn){Log.e(TAG, "error handling game turn...");}
						connected(mmSocket, mmDevice);
					}
					else{
						try {
							mmSocket.close();
						} catch (IOException e) {
							Log.e(TAG, "close() of connect socket failed", e);
						}
						connectionFailed(1, 1);
					}
				}				
				Looper.loop();			
			}

			public void cancel() {
				Log.d(TAG, "cancel " + this);
				try {
					mmSocket.close();
				} catch (IOException e) {
					Log.e(TAG, "close() of connect socket failed", e);
				}
			}
		}
		
		/**
		 * This thread runs during a connection with a remote device. It handles all
		 * incoming and outgoing transmissions.
		 */
		private class ConnectedThread extends Thread {
			private final BluetoothSocket mmSocket;
			private final InputStream mmInStream;
			private final OutputStream mmOutStream;

			public ConnectedThread(BluetoothSocket socket) {
				Log.d(TAG, "create ConnectedThread");
				mmSocket = socket;
				InputStream tmpIn = null;
				OutputStream tmpOut = null;

				// Get the BluetoothSocket input and output streams
				try {
					tmpIn = socket.getInputStream();
					tmpOut = socket.getOutputStream();
				} catch (IOException e) {
					Log.e(TAG, "temp sockets not created", e);
				}

				mmInStream = tmpIn;
				mmOutStream = tmpOut;
			}

			@Override
			public void run() {
				Log.i(TAG, "BEGIN mConnectedThread");
				mHandler.obtainMessage(BTMessageHandler.MESSAGE_CONNECTION_SETTLED).sendToTarget();
				
				byte[] buffer;
				int bytes;
				
				while(true){
					try {
						buffer = new byte[128];
						
						bytes = mmInStream.read(buffer);
//						Log.d("BTHandler", "buffer read is: " + buffer +" and its size: " + bytes);
						
						//receiving from an enemy for invoking an action
						if(buffer[0] == 0x01){
							//enenmy invoked and action and I need to send acknowledge message...
							//apply a msg from an enemy too
							Log.d("BTHandler", "received invocation");
							mHandler.obtainMessage(BTMessageHandler.MESSAGE_BATTLE_ACKNOWLEDGE, -1, bytes, buffer).sendToTarget();
							//next turn??
							mHandler.toggleMyTurn();
						}
						//receiving from an enemy for acknowledge
						else if (buffer[0] == 0x11){
							//enemy send an acknowledge message and now move forward to next turn
							Log.d("BTHandler", "received acknowledge..");
							//next turn??
							mHandler.toggleMyTurn();
						}
						
					} catch (IOException e) {
						Log.e("BTHandler", "unable to read(): ", e);
						break;
					}
					buffer = null;
				}
								
			}

			/**
			 * Write to the connected OutStream.
			 * 
			 * @param buffer
			 *            The bytes to write
			 */
			public void write(byte[] buffer) {
				try {
					Log.d("BTHandler", "write()");
					mmOutStream.write(buffer);
//					mmOutStream.flush();
//					mmOutStream.close();

				} catch (IOException e) {
					Log.e(TAG, "Exception during write", e);
				}
			}
			
			/**
			 * read from the connected InputStream
			 * 
			 * @return buffer read from InputStream
			 */
			/*
			public byte[] read(){
				byte[] buffer = new byte[1024];
				try{
					mmInStream.read(buffer);
					mmInStream.close();
					
				} catch(IOException e) {
					Log.e(TAG, "Exception during read", e);
				}
				return buffer;
			}
			*/

			public void cancel() {
				Log.d(TAG, "cancel " + this);
				try {
					mmSocket.close();
				} catch (IOException e) {
					Log.e(TAG, "close() of connect socket failed", e);
				}
			}
		}
}
