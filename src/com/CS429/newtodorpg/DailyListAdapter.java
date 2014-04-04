package com.CS429.newtodorpg;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.CS429.newtodorpg.model.Daily;
import com.CS429.newtodorpg.model.Quest;
import com.CS429.newtodorpg.model.Vice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DailyListAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Daily> list;
	private DailyListAdapter adapter = this;
	
	public DailyListAdapter(Context context, ArrayList<Daily> dailys){
		this.context = context;
		this.list = dailys;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;
		if(row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(R.layout.daily_list_view_row, parent, false);
		}
		
		ImageView image = (ImageView)row.findViewById(R.id.imgIcon);
		TextView title = (TextView)row.findViewById(R.id.txtTitle);
/*		
		Button detail_button = (Button)row.findViewById(R.id.add_daily_detail_button);
		detail_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.d("[Daily]", "detail temporary function");
			}	
		});
*/		
	
		Button detail_button = (Button)row.findViewById(R.id.add_daily_detail_button);
		detail_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, DailyDetailActivity.class);
				((Activity) context).startActivityForResult(intent, Quest.VICE);
			}	
		});
		
		Button delete_button = (Button)row.findViewById(R.id.delete_daily_button);
		delete_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				list.remove(position);
				adapter.notifyDataSetChanged();
			}	
		});
		
		Daily daily = list.get(position);
		image.setImageResource(daily.getImageResource());
		title.setText(daily.getTitle());
		
		
		return row;
	}

}
