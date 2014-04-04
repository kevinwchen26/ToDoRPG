package com.CS429.newtodorpg;

import java.util.ArrayList;

import com.CS429.newtodorpg.model.Vice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViceListAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Vice> list;
	private ViceListAdapter adapter = this;
	
	
	public ViceListAdapter(Context context, ArrayList<Vice> vices){
		this.context = context;
		this.list = vices;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		
		if(row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(R.layout.vice_list_view_row, parent, false);
		}
		
		ImageView image = (ImageView)row.findViewById(R.id.imgIcon);
		TextView title = (TextView)row.findViewById(R.id.txtTitle);
		
		Button delete_button = (Button)row.findViewById(R.id.delete_vice_button);
		delete_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				list.remove(position);
				adapter.notifyDataSetChanged();
			}	
		});
		
		Vice vice = list.get(position);
		image.setImageResource(vice.getImageResource());
		title.setText(vice.getTitle());
		
		return row;
	}

}
