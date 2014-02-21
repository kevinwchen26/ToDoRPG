package com.CS429.todorpg;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class CharacterCreation extends Activity implements OnClickListener{

	int curidx;
	Drawable characters[];
	ImageView img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_creation);
		// Initialize Spinner
		Spinner spinner = (Spinner) findViewById(R.id.select_class_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.character_classes, android.R.layout.simple_spinner_item);
		// Specify the layout to use for choices
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		// Initialize character array
		setCharacters();
		
		// Initialize image view
	
		img = (ImageView)findViewById(R.id.character_image);
		
		
		
		
		ImageButton left = (ImageButton)findViewById(R.id.leftarrow);
		ImageButton right = (ImageButton)findViewById(R.id.rightarrow);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.character_creation, menu);
		return true;
	}
	
	 private void setCharacters(){
         //temporarily only 4 characters
         curidx = 0;
         characters = new Drawable[4];
         characters[0] = getApplicationContext().getResources().getDrawable(R.drawable.frodude);
         characters[1] = getApplicationContext().getResources().getDrawable(R.drawable.simpson);
         characters[2] = getApplicationContext().getResources().getDrawable(R.drawable.spacegirl);
         characters[3] = getApplicationContext().getResources().getDrawable(R.drawable.zelda);
	 }

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.leftarrow:
			curidx--;
			if (curidx < 0) {
				curidx = 3;
			}
			img.setImageDrawable(characters[curidx]);
			
			break;
		case R.id.rightarrow:
			curidx = (curidx + 1) % 4;
			img.setImageDrawable(characters[curidx]);
			
			break;
		}
		
	}
	

}
