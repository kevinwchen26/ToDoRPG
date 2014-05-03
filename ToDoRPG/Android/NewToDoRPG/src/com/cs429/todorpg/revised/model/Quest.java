package com.cs429.todorpg.revised.model;

/**
 * Quest class
 * 
 * @author Leon Chen
 * 
 */
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
	private int quest_id; 

	/**
	 * Constructor for class
	 */
	public Quest() {
		type = due_month = due_date = due_hour = due_min = -1;
		title = null;
		quest_id = (int) System.currentTimeMillis();
	}

	/**
	 * Secondary Constructor with qtitle
	 * 
	 * @param qtitle
	 */
	public Quest(String qtitle) {
		type = due_month = due_date = due_hour = due_min = -1;
		title = qtitle;
		quest_id = (int) System.currentTimeMillis();
	}

	/**
	 * sets the type of the quest
	 * 
	 * @param qtype
	 */
	public void setType(int qtype) {
		type = qtype;
	}

	/**
	 * sets the due date of the quest
	 * 
	 * @param month
	 * @param date
	 * @param hour
	 * @param min
	 */
	public void setDueDate(int month, int date, int hour, int min) {
		due_month = month;
		due_date = date;
		due_hour = hour;
		due_min = min;
	}

	/**
	 * gets the title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * gets the type
	 * 
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * gets the Due date
	 * 
	 * @return due date
	 */
	public int[] getDueDate() {
		int[] arr = { due_month, due_date, due_hour, due_min };
		return arr;
	}

	/**
	 * sets the ID
	 * 
	 * @param id
	 */
	public void setId(int id) {
		quest_id = id;
	}

	/**
	 * gets the ID
	 * 
	 * @return
	 */
	public int getId() {
		return quest_id;
	}
}
