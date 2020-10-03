package com.company.myapp.entities;

import org.json.JSONObject;

public class DateTime {
	
	private String date, time;
	
	public DateTime() {
		//Default Constructor
	}
	
	public DateTime(String date, String time) {
		this.date = date;
		this.time = time;
	}
	
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("date", date);
		obj.put("time", time);
		return obj;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
