package com.cs429.todorpg.revised.model;

public class LogItem {
	private String content;

	private int id;
	private String date_time;

	public LogItem(int id, String content, String date) {
		this.id=id;
		this.content=content;
		this.date_time=date;
	}
	
	public LogItem(String content, String date){
		this.content=content;
		this.date_time=date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public boolean equals(Object o) {
		LogItem other = (LogItem) o;
		return this.content == other.content
				&& this.date_time.equals(other.date_time);
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
