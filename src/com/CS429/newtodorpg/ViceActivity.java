package com.CS429.newtodorpg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.CS429.newtodorpg.controller.ViceListAdapter;
import com.CS429.newtodorpg.database.DataBaseManager;
import com.CS429.newtodorpg.model.Vice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViceActivity  extends BaseActivity {
	
	private ArrayList<Vice> vice_list;
	private ListView vice_list_view;
	private ViceListAdapter adapter;
	private Button add_button;
	private EditText edit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vice_activity);
		setHeader(R.id.header);
//		vice_list = setViceList();
		new today_vice().execute();
		findViewById();
//		setViceListView();
	}
	
	private void findViewById(){
		vice_list_view = (ListView)findViewById(R.id.vice_listview);
		add_button = (Button)findViewById(R.id.add_button);
		add_button.setOnClickListener(ButtonListener);
		edit = (EditText)findViewById(R.id.list_edit);
	}
	
/*	
	private ArrayList<Vice> setViceList(){
		
		//temporary lists
		ArrayList<Vice> tmp = new ArrayList<Vice>();
		Vice tmp1 = new Vice("tmp1");
		tmp1.setImage(R.drawable.temp_images);
		tmp.add(tmp1);
		
		Vice tmp2 = new Vice("tmp2");
		tmp2.setImage(R.drawable.temp_images);
		tmp.add(tmp2);
		
		Vice tmp3 = new Vice("tmp3");
		tmp3.setImage(R.drawable.temp_images);
		tmp.add(tmp3);
		////////

		return tmp;
	}
*/	
	private void setViceListView(){
		adapter = new ViceListAdapter(
				ViceActivity.this, vice_list);
		
		View header = (View)getLayoutInflater().inflate(R.layout.quest_header, null);
		TextView txt = (TextView)header.findViewById(R.id.txtHeader);
		txt.setText("VICE LIST");
		vice_list_view.addHeaderView(header);
		vice_list_view.setAdapter(adapter);
	}	
	
	private boolean addListFromEdit(EditText newtitle){
		String text = newtitle.getText().toString();
		if(text == null || text.isEmpty())
			return false;
		
		Vice vice = new Vice(text);
		vice.setImage(R.drawable.temp_images);
		//temporary due date set
		vice.setDueDate(Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 0, 0);
		vice_list.add(0, vice);
		newtitle.setText(null);
		DataBaseManager.getInstance(getApplicationContext()).insertVICE(vice);
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
					Toast.makeText(ViceActivity.this, "type in title", Toast.LENGTH_SHORT).show();
				break;
				
			default:
				break;
			}
		}
	};
	
class today_vice extends AsyncTask<String, String, String> {
		
		Calendar calendar;
		int month;
		int day;
		protected void onPreExecute() {
			super.onPreExecute();
			DataBaseManager.getInstance(ViceActivity.this);
			calendar = Calendar.getInstance();
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}

		@Override
		protected String doInBackground(String... args) {
			vice_list = DataBaseManager.getInstance(ViceActivity.this).getVICE(month, day);
			if(vice_list == null)
				vice_list = new ArrayList<Vice>();
			return null;
		}
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			setViceListView();	
		}
	}
	
}