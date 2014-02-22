package com.CS429.todorpg;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

public class CharacterCreation extends Activity implements OnClickListener, OnItemSelectedListener{

	int curidx;
	Drawable characters[];
	ImageView img;
	EditText characterName;
	Spinner spinner;
	Intent intent;
	AlertDialog dialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.character_creation);
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
		if (name == null || name.isEmpty()) {
			dialog = WarningMessage();
			dialog.show();
		}
		
//		name = "Empty String";
		
//		Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
//		Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();
		
	}
	private AlertDialog WarningMessage() {
	    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this) 
	        //set message, title, and icon
	        .setTitle("Warning") 
	        .setMessage("Please write name of Character") 
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) { 
	                dialog.dismiss();
	            }   
	        })
	        .create();
	        return myQuittingDialogBox;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//		Toast.makeText(parent.getContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
//		String character_selection_string;
		String character_selection_string = parent.getItemAtPosition(pos).toString().trim();
		intent = new Intent(CharacterCreation.this, CharacterProfile.class);
		intent.putExtra("character", character_selection_string);
		if(!character_selection_string.equals("Choose Character")) {
			startActivity(intent);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		selected = "Nothing Selected";
	}

}
