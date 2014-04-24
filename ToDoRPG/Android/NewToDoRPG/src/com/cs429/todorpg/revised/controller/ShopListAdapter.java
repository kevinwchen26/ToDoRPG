package com.cs429.todorpg.revised.controller;

import java.util.ArrayList;

import com.cs429.todorpg.revised.GameApplication;
import com.cs429.todorpg.revised.R;
import com.cs429.todorpg.revised.itemsystem.RpgItem;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopListAdapter extends ArrayAdapter<RpgItem> {
	private final Context context;
	private final ArrayList<RpgItem> shopItems;
	private int layout;

	public ShopListAdapter(Context context, int layoutResourceId, ArrayList<RpgItem> shopItems) {
		super(context, layoutResourceId, shopItems);
		this.context = context;
		this.shopItems = shopItems;
		this.layout = layoutResourceId;
	}
	
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		RpgItem shopItem = shopItems.get(position);
		View rowView = inflater.inflate(layout, parent, false);
		
		// Set Name
		TextView name = (TextView) rowView.findViewById(R.id.shop_item_name);
		name.setText(shopItem.getName());
	
		// Set Image Icon
		ImageView icon = (ImageView)rowView.findViewById(R.id.shop_img_icon);
		icon.setImageBitmap(BitmapFactory.decodeResource(GameApplication.getAppContext().getResources(), shopItem.getResId()));
		return rowView;
	}
}
