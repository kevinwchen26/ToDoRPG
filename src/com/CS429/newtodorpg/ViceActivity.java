package com.CS429.newtodorpg;

import java.util.ArrayList;
import java.util.List;

import com.CS429.newtodorpg.model.Vice;

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
//		setHeader(R.id.header);
		vice_list = setViceList();
		findViewById();
		setViceListView();
	}
	
	private void findViewById(){
		vice_list_view = (ListView)findViewById(R.id.vice_listview);
		add_button = (Button)findViewById(R.id.add_button);
		add_button.setOnClickListener(ButtonListener);
		edit = (EditText)findViewById(R.id.list_edit);
	}
	
	
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
	
	private void setViceListView(){
		adapter = new ViceListAdapter(
				ViceActivity.this, vice_list);
		
//		View header = (View)getLayoutInflater().inflate(R.layout.list_view_header, null);
//		vice_list_view.addHeaderView(header);
		vice_list_view.setAdapter(adapter);
	}	
	
	private boolean addListFromEdit(EditText newtitle){
		String text = newtitle.getText().toString();
		if(text == null || text.isEmpty())
			return false;
		
		Vice vice = new Vice(text);
		vice.setImage(R.drawable.temp_images);
		vice_list.add(0, vice);
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
					Toast.makeText(ViceActivity.this, "type in title", Toast.LENGTH_SHORT).show();
				break;
				
			default:
				break;
			}
		}
	};
	
}