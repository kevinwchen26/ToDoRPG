package com.CS429.newtodorpg.database;

import java.util.ArrayList;

import com.CS429.newtodorpg.R;
import com.CS429.newtodorpg.model.Daily;
import com.CS429.newtodorpg.model.ToDo;
import com.CS429.newtodorpg.model.Vice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseManager extends SQLiteOpenHelper{

	private volatile static DataBaseManager dbInstance = null;

	final static String DATABASE_NAME = "PrototypingDB";
	final static int DATABASE_VERSION = 4;
	final static String DB_LOG_ID = "Database";

	/*
	 *  TABLES
	 */
	public static final String VICE_TABLE = "vice_table";
	public static final String DAILY_TABLE = "daily_table";
	public static final String TODO_TABLE = "todo_table";
	public static final String MILESTONES = "milestone_table";
	
	/*
	 *  FIELD NAMES
	 */
	public static final String QUEST_ID = "quest_id";
	public static final String TITLE = "title";
	public static final String MONTH = "month";
	public static final String DAY = "day";
	public static final String HOUR = "hour";
	public static final String MIN = "min";
	public static final String DESCRIP = "decription";
	public static final String MILESTONE = "milestone";
	
	private DataBaseManager(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DataBaseManager getInstance(Context context){
		if(dbInstance == null){
			synchronized (DataBaseManager.class) {
				if(dbInstance == null)
				{
					dbInstance = new DataBaseManager(context);
					Log.i(DB_LOG_ID,"START - Called getInstance()");
				}					
			}
		}
		return dbInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE "+ VICE_TABLE +
				" ("+ QUEST_ID + " INTEGER, " + TITLE + " TEXT, " + MONTH + " INTEGER, " 
				+ DAY + " INTEGER, " + HOUR + " INTEGER, " + MIN + " INTEGER)");
		
		db.execSQL("CREATE TABLE "+ DAILY_TABLE +
				" ("+ QUEST_ID + " INTEGER, " + TITLE + " TEXT, " + DESCRIP + " TEXT, " + MONTH + " INTEGER, " 
				+ DAY + " INTEGER, " + HOUR + " INTEGER, " + MIN + " INTEGER)");
		
		db.execSQL("CREATE TABLE "+ TODO_TABLE + " ("+ QUEST_ID + " INTEGER, " 
				+ TITLE + " TEXT, " + DESCRIP + " TEXT, " +  MONTH + " INTEGER, " + DAY + " INTEGER, " + HOUR + " INTEGER, " + MIN + " INTEGER)");
		
		db.execSQL("CREATE TABLE " + MILESTONES + " (" + QUEST_ID + " INTEGER, " + MILESTONE + " TEXT)");
		Log.i(DB_LOG_ID,"Created Tables!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + VICE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DAILY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + MILESTONES);
		Log.i(DB_LOG_ID,"Drop the table and create new tables");
		onCreate(db);
	}
	
	
	public void insertVICE(Vice vice){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		int[] dates = vice.getDueDate();
		
		cv.put(QUEST_ID, vice.getId());
		cv.put(TITLE, vice.getTitle());
		if(dates.length == 4){
			cv.put(MONTH, dates[0]);
			cv.put(DAY, dates[1]);
			cv.put(HOUR, dates[2]);
			cv.put(MIN, dates[3]);
		}
		db.insert(VICE_TABLE, null, cv);
		Log.i(DB_LOG_ID, "insertVICE()");
		db.close();
	}
	
	public void insertDAILY(Daily daily){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		int[] dates = daily.getDueDate();
		
		cv.put(QUEST_ID, daily.getId());
		cv.put(TITLE, daily.getTitle());
//		cv.put(DESCRIP, daily.getDescription());
		if(dates.length == 4){
			cv.put(MONTH, dates[0]);
			cv.put(DAY, dates[1]);
			cv.put(HOUR, dates[2]);
			cv.put(MIN, dates[3]);
		}
		db.insert(DAILY_TABLE, null, cv);
		Log.i(DB_LOG_ID, "insertDAILY()");
		db.close();
	}
	
	public void insertTODO(ToDo todo){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		int[] dates = todo.getDueDate();
		
		cv.put(QUEST_ID, todo.getId());
		cv.put(TITLE, todo.getTitle());
//		cv.put(DESCRIP, todo.getDescription());
		if(dates.length == 4){
			cv.put(MONTH, dates[0]);
			cv.put(DAY, dates[1]);
			cv.put(HOUR, dates[2]);
			cv.put(MIN, dates[3]);
		}
		db.insert(TODO_TABLE, null, cv);
		Log.i(DB_LOG_ID, "insertTODO()");
		db.close();
		
//		ArrayList<String> milestones = todo.getMileStone();
//		insertMileStone(todo.getId(), milestones);
	}
	
	private void insertMileStone(int id, ArrayList<String> milestones){
		if(milestones == null)
			return;
		else{
			ArrayList<String> tmp = new ArrayList<String>(milestones);
			while(!tmp.isEmpty()){
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues cv = new ContentValues();
				cv.put(QUEST_ID, id);
				cv.put(MILESTONE, tmp.get(0));
				db.insert(MILESTONES, null, cv);
				Log.i(DB_LOG_ID, "insertVICE()");
				db.close();
				tmp.remove(0);
			}
		}
		Log.i(DB_LOG_ID, "insertMilestones()");
	}
	
	
	public ArrayList<Vice> getVICE(int month, int day){
		SQLiteDatabase db = this.getReadableDatabase();
    	//List<TouchableArea> touchables = Collections.synchronizedList(new ArrayList<TouchableArea>());
    	String[] columns = new String[]{QUEST_ID, TITLE, MONTH, DAY, HOUR, MONTH};
    	String selection = MONTH +"=? AND " + DAY +"=?";
      	Cursor cursor = db.query(VICE_TABLE, columns, selection,
      			new String[]{String.valueOf(month), String.valueOf(day)}, null,null, null);
      	
      	ArrayList<Vice> vices = null;
      	if(cursor.moveToFirst()){
      		vices = new ArrayList<Vice>();
      		do{
      			int quest_id = cursor.getInt(0);
      			String title = cursor.getString(1);
      			int vmonth = cursor.getInt(2);
      			int vday = cursor.getInt(3);
      			int hour = cursor.getInt(4);
      			int min = cursor.getInt(5);
      			
      			Vice vice = new Vice(title);
      			vice.setDueDate(vmonth, vday, hour, min);
      			vice.setId(quest_id);
      			vice.setImage(R.drawable.add_button);//temporary image set
      			vices.add(vice);
      		}while(cursor.moveToNext());
      	}		
      	return vices;
	}
	
	public ArrayList<Daily> getDaily(int month, int day){
		SQLiteDatabase db = this.getReadableDatabase();
    	//List<TouchableArea> touchables = Collections.synchronizedList(new ArrayList<TouchableArea>());
    	String[] columns = new String[]{QUEST_ID, TITLE, MONTH, DAY, HOUR, MONTH, DESCRIP};
    	String selection = MONTH +"=? AND " + DAY +"=?";
      	Cursor cursor = db.query(DAILY_TABLE, columns, selection,
      			new String[]{String.valueOf(month), String.valueOf(day)}, null,null, null);
      	
      	ArrayList<Daily> dailys = null;
      	if(cursor.moveToFirst()){
      		dailys = new ArrayList<Daily>();
      		do{
      			int quest_id = cursor.getInt(0);
      			String title = cursor.getString(1);
      			int vmonth = cursor.getInt(2);
      			int vday = cursor.getInt(3);
      			int hour = cursor.getInt(4);
      			int min = cursor.getInt(5);
      			String descrip = cursor.getString(6);
      			
      			Daily daily = new Daily(title);
      			daily.setDueDate(vmonth, vday, hour, min);
      			daily.setId(quest_id);
//      			daily.WriteDescription(descrip);
//      			daily.setImage(R.drawable.add_button);//temporary image set
      			dailys.add(daily);
      		}while(cursor.moveToNext());
      	}		
      	return dailys;
	}
	
	public ArrayList<ToDo> getAllToDo(){
		SQLiteDatabase db = this.getReadableDatabase();
    	//List<TouchableArea> touchables = Collections.synchronizedList(new ArrayList<TouchableArea>());
//    	String[] columns = new String[]{QUEST_ID, TITLE, MONTH, DAY, HOUR, MONTH, DESCRIP};
//      	Cursor cursor = db.query(DAILY_TABLE, columns, null,
 //     			null, null,null, null);
    	String sql = "SELECT * FROM " + TODO_TABLE;
    	Cursor cursor = db.rawQuery(sql, null);
      	
      	ArrayList<ToDo> todos = null;
      	if(cursor.moveToFirst()){
      		todos = new ArrayList<ToDo>();
      		do{
      			int quest_id = cursor.getInt(0);
      			String title = cursor.getString(1);
      			String descrip = cursor.getString(2);
      			int vmonth = cursor.getInt(3);
      			int vday = cursor.getInt(4);
      			int hour = cursor.getInt(5);
      			int min = cursor.getInt(6);
      			ArrayList<String> miles = getMilestones(quest_id); 
      			
      			ToDo todo = new ToDo(title);
      			todo.setDueDate(vmonth, vday, hour, min);
      			todo.setId(quest_id);
//      			todo.WriteDescription(descrip);
//      			todo.WriteMileStone(miles);
//      			todo.setImage(R.drawable.add_button);//temporary image set
      			todos.add(todo);
      		}while(cursor.moveToNext());
      	}		
      	return todos;
	}
	
	private ArrayList<String> getMilestones(int id){
		SQLiteDatabase db = this.getReadableDatabase();
    	//List<TouchableArea> touchables = Collections.synchronizedList(new ArrayList<TouchableArea>());
		String sql = "SELECT * FROM " + MILESTONES;
    	Cursor cursor = db.rawQuery(sql, null);
      	
      	ArrayList<String> miles = null;
      	if(cursor.moveToFirst()){
      		miles = new ArrayList<String>();
      		do{
      			miles.add(cursor.getString(1));
      		}while(cursor.moveToNext());
      	}
		return miles;
	}
}
