package com.cs429.todorpg.revised;

import com.cs429.todorpg.revised.controller.BTControl;
import com.cs429.todorpg.revised.controller.BTMessageHandler;
import com.cs429.todorpg.service.BluetoothService;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * start activity of bluetooth connection
 * 
 * @author ssong25
 *
 */
public class BattleMainActivity extends BaseActivity {

	/*variables*/
	private BluetoothService BTService;
	private BTControl btctrl;
	private String device_address;

	private Button bluetooth_connect_btn;

	/**
	 * checks blue tooth availability when the activity starts
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e("[LifeCycle]", "BattleMainActivity: ++ onCreate ++");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		findViewById();

		btctrl = BTControl.getInstance(BattleMainActivity.this);
		boolean availability = btctrl.checkBTEnable();
		if (!availability) {
			Toast.makeText(getApplicationContext(),
					"bluetooth is not available", Toast.LENGTH_SHORT).show();
			bluetooth_connect_btn.setEnabled(false);
		}
		BTService = new BluetoothService(BattleMainActivity.this);
		
		BTMessageHandler.getInstance(BattleMainActivity.this).setBTService(
				BTService);
	}

	/**
	 * bluetooth service starts once bluetooth availability is confirmed
	 */
	@Override
	public void onResume() {
		Log.e("[LifeCycle]", "BattleMainActivity: ++ onResume ++");
		super.onResume();
		// do only when BT is available
		if (btctrl.IsBluetoothEnabled()) {
			bluetooth_connect_btn.setEnabled(true);
			BTService.start();
		}
	}

	@Override
	public void onDestroy() {
		Log.e("[LifeCycle]", "BattleMainActivity: ++ onDestroy ++");
		super.onDestroy();
	}

	/**
	 * handling of bluetooth availability and remote device choosing among lists
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case BTControl.REQUEST_ENABLE_BT:
				Toast.makeText(getApplicationContext(), "bluetooth is on",
						Toast.LENGTH_SHORT).show();
				break;

			case BTControl.CHOOSE_DEVICE:
				device_address = data.getStringExtra("selected");
				btctrl.set_address(device_address);
				Toast.makeText(getApplicationContext(),
						"connecting to " + device_address, Toast.LENGTH_SHORT)
						.show();
				// connecting to another device here...
				BTService.connect(btctrl.getDevice());
				break;

			default:
				break;
			}
		}
	}

	/**
	 * wrap up of findViewById
	 */
	private void findViewById() {
		bluetooth_connect_btn = (Button) findViewById(R.id.bluetooth_connect_btn);
		bluetooth_connect_btn.setOnClickListener(mListener);
	}

	/**
	 * Button listener
	 */
	Button.OnClickListener mListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.bluetooth_connect_btn:
				if (!btctrl.IsBluetoothEnabled()) {
					Toast.makeText(getApplicationContext(),
							"bluetooth is still not enabled",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "connect pressed",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(BattleMainActivity.this,
							DeviceListActivity.class);
					startActivityForResult(intent, BTControl.CHOOSE_DEVICE);
				}
				break;

			default:
				break;
			}
		}
	};

}
