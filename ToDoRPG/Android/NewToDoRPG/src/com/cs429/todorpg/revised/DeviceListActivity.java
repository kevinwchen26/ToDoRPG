package com.cs429.todorpg.revised;

import java.util.Set;

import com.cs429.todorpg.revised.controller.BTControl;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeviceListActivity extends BaseActivity {

	private Set<BluetoothDevice> pairedDevices;
	private ArrayAdapter<String> adapter;
	private ListView list_view;
	private BluetoothDevice selected_device;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_list);
		
		list_view = (ListView) findViewById(R.id.device_list_view);
	
		adapter = new ArrayAdapter<String>(DeviceListActivity.this, R.layout.device_list_row);
		list_view.setAdapter(adapter);
		list_view.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				BluetoothDevice[] devices = pairedDevices.toArray(new BluetoothDevice[pairedDevices.size()]);
				selected_device = devices[position];
				
				if(selected_device == null)
					Log.d("[tmp]", "something is wrong..");
				
				BTControl.getInstance(getApplicationContext()).setDevice(selected_device);
				
				Intent intent = new Intent();
				intent.putExtra("selected", selected_device.getAddress());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		list();
	}

	@Override
	public void onBackPressed()
	{
		setResult(Activity.RESULT_CANCELED);
	    finish();
	};
	
	private void list(){
		pairedDevices = BTControl.getInstance(getApplicationContext()).getAdapter().getBondedDevices();
		for(BluetoothDevice device : pairedDevices){
			adapter.add(device.getName() + "\n" + device.getAddress());
		}
	}
}
