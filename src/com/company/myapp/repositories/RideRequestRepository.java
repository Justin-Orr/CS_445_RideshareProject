package com.company.myapp.repositories;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONObject;

import com.company.myapp.entities.RideRequest;
import com.company.myapp.interfaces.RideRequestRepositoryInterface;

public class RideRequestRepository implements RideRequestRepositoryInterface {
	
private static Hashtable<Integer, RideRequest> repo = new Hashtable<Integer, RideRequest>();
	
	public RideRequestRepository() {
		//Default constructor
	}
	
	public int createRideRequest(int rid, int aid, int passengers, boolean ride_confirmed, boolean pickup_confirmed) {
		RideRequest r = new RideRequest(rid, aid, passengers, ride_confirmed, pickup_confirmed);
		repo.put(r.getJid(), r);
		return r.getRid();
	}
	
	public RideRequest getRequest(int rid) {
		return repo.get(rid);
	}
	
	/*public String viewAllAccounts() {
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
	}*/
	
	public void confirmDenyRequest(RideRequest rr, JSONObject obj) {
		rr.setRide_confirmed(obj.getBoolean("ride_confirmed"));
		repo.replace(rr.getJid(), rr);
	}
	
}
