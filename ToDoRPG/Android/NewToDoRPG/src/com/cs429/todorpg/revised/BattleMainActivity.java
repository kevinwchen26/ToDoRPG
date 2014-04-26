package com.cs429.todorpg.revised;


import com.cs429.todoprg.service.BluetoothService;
import com.cs429.todorpg.revised.controller.BTControl;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BattleMainActivity extends Activity {

	private BluetoothService BTService;
	private BTControl btctrl;
	private String device_address;
	
	private Button bluetooth_connect_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("[LifeCycle]", "++ onCreate ++");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle_main);
		findViewById();
		
		btctrl = BTControl.getInstance(BattleMainActivity.this);
		boolean availability = btctrl.checkBTEnable();
		if(!availability){
			Toast.makeText(getApplicationContext(), "bluetooth is not available", Toast.LENGTH_SHORT).show();
			bluetooth_connect_btn.setEnabled(false);
		}
		BTService = new BluetoothService(getBaseContext());
	}

	@Override
	public void onResume(){
		Log.e("[LifeCycle]", "++ onResume ++");
		super.onResume();
		//do only when BT is available
		if(btctrl.IsBluetoothEnabled()){
			bluetooth_connect_btn.setEnabled(true);
			BTService.start();
		}
	}	

	@Override
	public void onDestroy(){
		Log.e("[LifeCycle]", "++ onDestroy ++");
		super.onDestroy();
		BTService.stop();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch(requestCode){
			
			case BTControl.REQUEST_ENABLE_BT:
				Toast.makeText(getApplicationContext(), "bluetooth is on", Toast.LENGTH_SHORT).show();
				break;
			
			case BTControl.CHOOSE_DEVICE:
				device_address = data.getStringExtra("selected");
				btctrl.set_address(device_address);
				Toast.makeText(getApplicationContext(), "connecting to " + device_address, Toast.LENGTH_SHORT).show();
				//connecting to another device here...
				BTService.connect(btctrl.getDevice());
				break;
				
			default:
				break;
			}
		}
	}
	
	private void findViewById(){
		bluetooth_connect_btn = (Button) findViewById(R.id.bluetooth_connect_btn);
		bluetooth_connect_btn.setOnClickListener(mListener);
	}
	
	Button.OnClickListener mListener = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			switch(v.getId()){
				
				case R.id.bluetooth_connect_btn:
					if(!btctrl.IsBluetoothEnabled()){
						Toast.makeText(getApplicationContext(), "bluetooth is still not enabled", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(getApplicationContext(), "connect pressed", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(BattleMainActivity.this, DeviceListActivity.class);
						startActivityForResult(intent, BTControl.CHOOSE_DEVICE);
					}
					break;
					
				default:
					break;
			}
		}
	};
	
}
