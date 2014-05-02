package com.cs429.todorpg.revised.model;

import android.util.Log;
/**
 * 
 * @author hlim10, ssong25
 *
 */
public class ToDo extends Quest {
	private boolean finished;
	private String my_todo;
	private String extra;
	private int difficulty; // 0 - easy / 1 - medium / 2 - hard
	private int primary_key;

	/**
	 * Constructor
	 * 
	 * @param my_todo
	 */
	public ToDo(String my_todo) {
		this.setToDo(my_todo);
		finished = false;
		difficulty = 0;
	}

	/**
	 * Secondary Constructor
	 * 
	 * @param my_todo
	 * @param extra
	 * @param primary_key
	 */
	public ToDo(String my_todo, String extra, int primary_key) {
		this.setToDo(my_todo);
		this.setExtra(extra);
		this.setKey(primary_key);
		finished = false;
		difficulty = 0; // default set - easy
	}

	/**
	 * various setters and getters for variables in object
	 * 
	 * @return
	 */
	public String getToDo() {
		return my_todo;
	}

	public void setToDo(String my_todo) {
		this.my_todo = my_todo;
	}

	public String getExtra() {
		if (extra == null)
			return new String();
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public int getKey() {
		return primary_key;
	}

	public void setKey(int primary_key) {
		this.primary_key = primary_key;
	}

	public void setFinish() {
		finished = true;
	}

	public boolean getStatus() {
		return finished;
	}

	public void setDifficulty(int rate) {
		if (rate < 0 || rate > 2) {// error...
			Log.e("[ToDo]", "difficuty out of range...");
			return;
		}
		this.difficulty = rate;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public boolean equals(Object o) {
		ToDo todo = (ToDo) o;
		return (this.primary_key == todo.getKey()
				&& this.my_todo.equals(todo.getToDo())
				&& this.extra.equals(todo.getExtra())
				&& this.getStatus() == todo.getStatus()
				&& this.getDifficulty() == todo.getDifficulty()
				&& this.getDueDate()[0] == todo.getDueDate()[0]
				&& this.getDueDate()[1] == todo.getDueDate()[1]
				&& this.getDueDate()[2] == todo.getDueDate()[2] && this
					.getDueDate()[3] == todo.getDueDate()[3]);
	}
}
