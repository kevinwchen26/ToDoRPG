package com.example.avatarcreator;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Inventory inventory;
    
    public ImageAdapter(Context c, Inventory inventory) {
        mContext = c;
        this.inventory = inventory;
    }

    public int getCount() {
        return inventory.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
    		imageView.setBackgroundColor(Color.LTGRAY);
    		
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(inventory.getItemList().get(position).getResourceId());
        return imageView;
    }

    // references to our images
    /*
    private Integer[] mThumbIds = {
            R.drawable.light_helmet, R.drawable.wiz_hat
            , R.drawable.wiz_hat, R.drawable.wiz_hat, R.drawable.wiz_hat
            , R.drawable.wiz_hat, R.drawable.wiz_hat, R.drawable.wiz_hat
            , R.drawable.light_helmet, R.drawable.light_helmet
            , R.drawable.light_helmet, R.drawable.light_helmet
    };
    */
}