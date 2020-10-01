package com.company.myapp;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.json.JSONObject;

public class Account {
	
	private int aid, number_of_ratings;
	private double average_rating;
	private boolean is_active;
	private String account_type, first_name, last_name, phone_number, picture, date_created;
	private Vehicle vehicle;
	
	public Account() {
		//Default constructor
	}
	
	public Account(String first_name, String last_name, String phone_number, String picture) {
		this.aid = UniqueIdGenerator.getUniqueID();
		this.number_of_ratings = 0;
		this.average_rating = 0;
		this.is_active = false;
		this.account_type = "unset";
		this.first_name = first_name; 
		this.last_name = last_name;
		this.phone_number = phone_number;
		this.picture = picture;
		this.vehicle = new Vehicle();
		//this.date_created = TimeStamp.stamp();
		this.date_created = "DD-MMM-YYYY, HH:MM";
	}
	
	public JSONObject toJson() {
		/*JsonObjectBuilder obj = Json.createObjectBuilder();
		obj.add("aid", aid);
		obj.add("name", first_name + " " + last_name);
		//obj.add("date_created", date_created);
		obj.add("is_active", is_active);
		JsonObject jsonObject = obj.build();
		return jsonObject;*/
		JSONObject obj = new JSONObject();
		obj.put("aid", aid);
		obj.put("name", first_name + " " + last_name);
		//obj.add("date_created", date_created);
		obj.put("is_active", is_active);
		
		return obj;
	}
	
	public static Account jsonToAccount(JSONObject json) {
		String first_name = json.getString("first_name");
		String last_name = json.getString("last_name");
		String phone_number = json.getString("phone_number");
		String picture = json.getString("picture");
		Account a = new Account(first_name, last_name, phone_number, picture);
		return a;
	}
	
	public int activateAccount(String type) {
		if (type.compareTo("driver") == 0)
			return activateDriver();
		else if (type.compareTo("rider") == 0)
			return activateRider();
		else
			return -1;
	}
	
	public int activateDriver() {
		//Place method call here to ping user to grab vehicle data
		is_active = true;
		vehicle = new Vehicle();
		return 0;
	}
	
	public int activateRider() {
		is_active = true;
		return 0;
	}
	
	public int getID() {
		return aid;
	}
	
	public void setID(int id) {
		this.aid = id;
	}
	
	public String getFirstName() {
		return first_name;
	}
	
	public void setFirstName(String name) {
		this.first_name = name;
	}
	
	public String getLastName() {
		return last_name;
	}
	
	public void setLastName(String name) {
		this.last_name = name;
	}
	
	public String getPhoneNumber() {
		return this.phone_number;
	}
	
	public void setPhoneNumber(String number) {
		this.phone_number = number;
	}
	
	public String getPicture() {
		return this.picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getDateCreated() {
		return this.date_created;
	}
	
	public void setDateCreated(String date) {
		this.date_created = date;
	}
	
	public boolean isActive() {
		return this.is_active;
	}
	
	public void setActive(boolean b) {
		this.is_active = b;
	}
	
	public int getNumberOfRatings() {
		return this.number_of_ratings;
	}
	
	public void setNumberOfRatings(int num) {
		this.number_of_ratings = num;
	}
	
	public double getAverageRating() {
		return this.average_rating;
	}
	
	public void setAverageRating(double num) {
		this.average_rating = num;
	}
	
	public String getAccountType() {
		return this.account_type;
	}
	
	public void setAccountType(String type) {
		this.account_type = type;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}
	
	public void setVehicle(Vehicle v) {
		this.vehicle = v;
	}

}
