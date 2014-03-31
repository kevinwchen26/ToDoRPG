package com.example.avatarcreator;


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;

public class ShowMergedResult extends Activity {

	@Override
	public void onCreate(Bundle bundle){
		
		super.onCreate(bundle);
		setContentView(R.layout.main);
		
		Bitmap bitmap = getIntent().getParcelableExtra("merged_image");
		ImageView image = (ImageView) findViewById(R.id.merged_image);
		image.setImageBitmap(bitmap);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_merged_result, menu);
		return true;
	}

}
