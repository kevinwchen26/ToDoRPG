package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs429.todorpg.revised.GameApplication;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.itemsystem.RpgItem;

public class ItemListAdapter extends ArrayAdapter<RpgItem> {
	private final Context context;
	private final ArrayList<RpgItem> inventoryItems;
	private int layout;

	public ItemListAdapter(Context context, int layoutResourceId, ArrayList<RpgItem> inventoryItems) {
		super(context, layoutResourceId, inventoryItems);
		this.context = context;
		this.inventoryItems = inventoryItems;
		this.layout = layoutResourceId;
	}
	
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		RpgItem equippedItem = inventoryItems.get(position);
		View rowView = inflater.inflate(layout, parent, false);
		
		// Set Name
		TextView name = (TextView) rowView.findViewById(R.id.equipped_item_name);
		name.setText(equippedItem.getName());
	
		// Set Image Icon
		ImageView icon = (ImageView)rowView.findViewById(R.id.imgIcon);
		icon.setImageBitmap(BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), equippedItem.getResId()));
		return rowView;
	}
}