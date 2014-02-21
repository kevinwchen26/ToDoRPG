package com.CS429.todorpg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	static SQLiteDatabase database;
	DatabaseHelper helper;
	static String tableName = "MyCharacter";
	Spinner character_selection;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FindViewById();
		SpinnerListener();
		createDatabase();
	}
	private void FindViewById() {
		character_selection = (Spinner)findViewById(R.id.character_selection);
	}
	private void SpinnerListener() {
		character_selection.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//				Toast.makeText(parent.getContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
//				String character_selection_string;
				String character_selection_string = parent.getItemAtPosition(pos).toString().trim();
				intent = new Intent(MainActivity.this, CharacterProfile.class);
				intent.putExtra("character", character_selection_string);
				if(!character_selection_string.equals("Choose Character")) {
					startActivity(intent);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			
		});
	}
	/* Insert data to the database */
	private void InsertData() {
		Character default_stat = null;   			// temp ---- Remove
		database.beginTransaction();
		try {
			String sql = null;
			sql = "insert into " + tableName + "(str, con, dex, _int, wis, cha) " +
						"values('"+ default_stat.getSTR() +"', '"+ default_stat.getCON() +"', '" 
						+ default_stat.getDEX() +"', '"+ default_stat.getINT() +"', '" 
						+ default_stat.getWIS() +"', '" + default_stat.getCHA()+"')";
			database.execSQL(sql);
			database.setTransactionSuccessful();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
		}
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	 /* create database by using helper */
	private void createDatabase() {
		String db_name = "my_character.db";
		int version = 1;
		helper = new DatabaseHelper(this, db_name, null, version);
		database = helper.getWritableDatabase();
		
	}

	/* Database Helper */
	class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			createTable(db);
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
		
		private void createTable(SQLiteDatabase db) {
			String sql = "create table " + tableName + "(_id integer primary key autoincrement, str int, con int, dex int, _int int, wis int, cha int)";
			try {
				db.execSQL(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
