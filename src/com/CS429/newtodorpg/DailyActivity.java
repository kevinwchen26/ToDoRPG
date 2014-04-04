package com.CS429.newtodorpg;

import java.util.ArrayList;

import com.CS429.newtodorpg.model.Daily;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class DailyActivity extends BaseActivity {

	private ArrayList<Daily> daily_list;
	private ListView daily_list_view;
	private DailyListAdapter adapter;
	private Button add_button;
	private EditText edit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_activity);
//		setHeader(R.id.header);
		daily_list = setDailyList();
		findViewById();
		setDailyListView();
	}

	private void findViewById(){
		daily_list_view = (ListView)findViewById(R.id.daily_listview);
		add_button = (Button)findViewById(R.id.add_button);
		add_button.setOnClickListener(ButtonListener);
		edit = (EditText)findViewById(R.id.list_edit);
	}
	
	
	private ArrayList<Daily> setDailyList(){
		//temporary lists
		ArrayList<Daily> tmp = new ArrayList<Daily>();
		Daily tmp1 = new Daily("daily1");
		tmp1.setImage(R.drawable.temp_images);
		tmp.add(tmp1);
		
		Daily tmp2 = new Daily("daily2");
		tmp2.setImage(R.drawable.temp_images);
		tmp.add(tmp2);
		
		Daily tmp3 = new Daily("daily3");
		tmp3.setImage(R.drawable.temp_images);
		tmp.add(tmp3);
		////////
		
		return tmp;
	}
	
	private void setDailyListView(){
		adapter = new DailyListAdapter(
				DailyActivity.this, daily_list);
		
//		View header = (View)getLayoutInflater().inflate(R.layout.list_view_header, null);
//		daily_list_view.addHeaderView(header);
		daily_list_view.setAdapter(adapter);
	}	
	
	private boolean addListFromEdit(EditText newtitle){
		String text = newtitle.getText().toString();
		if(text == null || text.isEmpty())
			return false;
		
		Daily Daily = new Daily(text);
		Daily.setImage(R.drawable.temp_images);
		daily_list.add(0, Daily);
		newtitle.setText(null);
		return true;
	}
	
	OnClickListener ButtonListener = new OnClickListener(){
		@Override
		public void onClick(View view) {
			switch(view.getId()){
			
			case R.id.add_button:
				if(addListFromEdit(edit))
					adapter.notifyDataSetChanged();
				else
					Toast.makeText(DailyActivity.this, "type in title", Toast.LENGTH_SHORT).show();
				break;				
			default:
				break;
			}
		}
	};
	
	
}
