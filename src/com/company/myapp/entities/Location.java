package com.company.myapp.entities;

import org.json.JSONObject;

public class Location {
	
	private String fromCity, fromZip, toCity, toZip;
	
	public Location() {
		//Default Constructor
	}
	
	public Location(String fromCity, String fromZip, String toCity, String toZip) {
		this.fromCity = fromCity;
		this.fromZip = fromZip;
		this.toCity = toCity;
		this.toZip = toZip;
	}
	
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("from_city", fromCity);
		obj.put("from_zip", fromZip);
		obj.put("to_city", toCity);
		obj.put("to_zip", toZip);
		return obj;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getFromZip() {
		return fromZip;
	}

	public void setFromZip(String fromZip) {
		this.fromZip = fromZip;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getToZip() {
		return toZip;
	}

	public void setToZip(String toZip) {
		this.toZip = toZip;
	}

}
