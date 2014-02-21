package com.CS429.todorpg;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class CharacterCreation extends Activity implements OnClickListener, OnItemSelectedListener{

	int curidx;
	Drawable characters[];
	ImageView img;
	EditText characterName;
	Spinner spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_creation);
		// Initialize Spinner
		spinner = (Spinner) findViewById(R.id.select_class_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.character_classes, android.R.layout.simple_spinner_item);
		// Specify the layout to use for choices
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		// Initialize character array
		setCharacters();
		
		// Initialize image view
	
		img = (ImageView)findViewById(R.id.character_image);
		
		// Arrows
		ImageButton left = (ImageButton)findViewById(R.id.leftarrow);
		ImageButton right = (ImageButton)findViewById(R.id.rightarrow);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		
		// Name
		characterName = (EditText)findViewById(R.id.character_name);
		 
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
	
	String selected;	//temp variable
	@SuppressLint("NewApi")
	public void tempMessage(View view) {
		String name = characterName.getText().toString();
		if (name == null || name.isEmpty())
			name = "Empty String";
		Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
		Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long id) {
		
		selected = parent.getItemAtPosition(pos).toString();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		selected = "Nothing Selected";
	}

}
