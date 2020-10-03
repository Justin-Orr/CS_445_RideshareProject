package com.company.myapp;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

public class RideRepository implements RideRepositoryInterface {
	
	private static Hashtable<Integer, Ride> repo = new Hashtable<Integer, Ride>();
		
	public RideRepository() {
		//Default constructor
	}
	
	public int createRide(int driverID, int maxNumberOfPassengers, double ammountPerPerson, Location location, DateTime dateTime, Vehicle vehicle, String conditions) {
		Ride r = new Ride(driverID, maxNumberOfPassengers, ammountPerPerson, location, dateTime, vehicle, conditions);
		repo.put(r.getRid(), r);
		return r.getRid();
	}
	
	/*public Account getAccount(int aid) {
		return repo.get(aid);
	}
	
	public String viewAllAccounts() {
		JSONArray obj = new JSONArray();
		ArrayList<Account> accounts = new ArrayList<Account>(repo.values());
		for(Account a : accounts) {
			String out = a.toPrettyJson().toString();
			obj.put(new JSONObject(out)); //Make a Json array of json objects with a summary of the accounts, not all data
		}
		return obj.toString();
	}
	
	public String searchAccounts(String key) {
		JSONArray obj = new JSONArray();
		String out = "";
		ArrayList<Account> accounts = new ArrayList<Account>(repo.values());
		for(Account a : accounts) {
			if(key.compareToIgnoreCase(a.getFirstName()) == 0 || key.compareToIgnoreCase(a.getLastName()) == 0 || key.compareToIgnoreCase(a.getPhoneNumber()) == 0) {
				out = a.toPrettyJson().toString();
				obj.put(new JSONObject(out)); //Make a Json array of json objects with a summary of the accounts, not all data
			}	
		}
		return obj.toString();
	}
	
	public void updateAccount(Account a, JSONObject obj) {
		a.setFirstName(obj.getString("first_name"));
		a.setLastName(obj.getString("last_name"));
		a.setPhoneNumber(obj.getString("phone"));
		a.setPicture(obj.getString("picture"));
		repo.replace(a.getAid(), a);
	}*/
	
}
