package com.CS429.newtodorpg.model;

import java.util.Calendar;

public abstract class Quest {

	public static final int VICE = 0;
	public static final int DAILY = 1;
	public static final int TODO = 2;
	
	private int type;
	private String title;
	private int due_month;
	private int due_date;
	private int due_hour;
	private int due_min;
	private int quest_id;	//this is for alarm ID..will be used later
	
	public Quest(){
		type = due_month = due_date = due_hour = due_min = -1;
		title = null;
		quest_id = (int)System.currentTimeMillis();
	}
	
	public Quest(String qtitle){
		type = due_month = due_date = due_hour = due_min = -1;
		title = qtitle;
		quest_id = (int)System.currentTimeMillis();
	}
	
	public void setType(int qtype){
		type = qtype;
	}
	
	public void setDueDate(int month, int date, int hour, int min){
		due_month = month;
		due_date = date;
		due_hour = hour;
		due_min = min;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int getType(){
		return type;
	}
	
	public int[] getDueDate(){
		int[] arr = {due_month, due_date, due_hour, due_min};
		return arr;
	}
}
