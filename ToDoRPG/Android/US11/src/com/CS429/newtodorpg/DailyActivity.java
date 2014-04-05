package com.CS429.newtodorpg;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.CS429.newtodorpg.controller.DailyAdapter;
import com.CS429.newtodorpg.model.Daily;

public class DailyActivity extends BaseActivity {
	private EditText add_daily_field;
	private ListView daily_list;
	private ArrayList<Daily> daily;
	private DailyAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_activity);
		setHeader(R.id.header);
		daily = new ArrayList<Daily>();
		// new today_vice().execute();
		findViewById();
	}

	private void findViewById() {
		add_daily_field = (EditText) findViewById(R.id.add_daily_field);
		daily_list = (ListView) findViewById(R.id.daily_listview);
		findViewById(R.id.add_daily_button).setOnClickListener(ButtonHandler);
		
	}
	
	Button.OnClickListener ButtonHandler = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch(view.getId()) {
				case R.id.add_daily_button:
					String my_daily = add_daily_field.getText().toString();
					if(my_daily.isEmpty()) {
						Toast.makeText(DailyActivity.this, "Fill in the blank", Toast.LENGTH_SHORT).show();
						return;
					}
					AddMyDaily(daily, my_daily);
					add_daily_field.setText("");
					SetAdapter();
					break;
			}
		}
		
	};
	private void AddMyDaily(ArrayList<Daily> daily, String my_daily) {
		for(int i = 0; i < daily.size(); i++) {
			if(daily.get(i).getDaily().equals(my_daily)) {
				Toast.makeText(DailyActivity.this, "\"" + my_daily +"\" is alreay in your daily list", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		daily.add(new Daily(my_daily));
		Toast.makeText(DailyActivity.this, my_daily, Toast.LENGTH_SHORT).show();
//		SetAdapter();
		adapter = new DailyAdapter(DailyActivity.this, daily);
		adapter.notifyDataSetChanged();
	}
	private void SetAdapter() {
		adapter = new DailyAdapter(DailyActivity.this, daily);
		daily_list.setAdapter(adapter);
		

	}

}

/*	private void findViewById(){
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
		
		View header = (View)getLayoutInflater().inflate(R.layout.quest_header, null);
		TextView txt = (TextView)header.findViewById(R.id.txtHeader);
		txt.setText("DAILY LIST");
		daily_list_view.addHeaderView(header);
		daily_list_view.setAdapter(adapter);
	}	
	
	private boolean addListFromEdit(EditText newtitle){
		String text = newtitle.getText().toString();
		if(text == null || text.isEmpty())
			return false;
		
		Daily Daily = new Daily(text);
		Daily.setImage(R.drawable.temp_images);
		//temporary due date set
		Daily.setDueDate(Calendar.getInstance().get(Calendar.MONTH),
			Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 0, 0);
		daily_list.add(0, Daily);
		newtitle.setText(null);
		DataBaseManager.getInstance(getApplicationContext()).insertDAILY(Daily);
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
	
class today_daily extends AsyncTask<String, String, String> {
		
		Calendar calendar;
		int month;
		int day;
		protected void onPreExecute() {
			super.onPreExecute();
			DataBaseManager.getInstance(DailyActivity.this);
			calendar = Calendar.getInstance();
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}

		@Override
		protected String doInBackground(String... args) {
			daily_list = DataBaseManager.getInstance(DailyActivity.this).getDaily(month, day);
			if(daily_list == null)
				daily_list = new ArrayList<Daily>();
			return null;
		}
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			setDailyListView();	
		}
	}
	
} */
