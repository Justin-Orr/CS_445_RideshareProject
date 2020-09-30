package com.company.myapp;

public class Account {
	
	private int aid, number_of_ratings, average_rating;
	private boolean is_active;
	private String account_type, first_name, last_name, phone_number, picture;
	private Vehicle vehicle;
	
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
	
	public String getFirstName() {
		return first_name;
	}

}
