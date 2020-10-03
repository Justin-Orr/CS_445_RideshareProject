package com.company.myapp.repositories;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.company.myapp.entities.Rating;
import com.company.myapp.interfaces.RatingRepositoryInterface;

public class RatingRepository implements RatingRepositoryInterface {
	
private static Hashtable<Integer, Rating> repo = new Hashtable<Integer, Rating>();
	
	public RatingRepository() {
		//Default constructor
	}
	
	public int rateAccount(int rid, int senderID, int rating, String comment) {
		Rating r = new Rating(rid, senderID, rating, comment);
		repo.put(r.getSid(), r);
		return r.getSid();
	}
	
	public Rating getRating(int sid) {
		return repo.get(sid);
	}
	
	/*public String searchAccounts(String key) {
		JSONArray obj = new JSONArray();
		String out = "";
		ArrayList<Rating> accounts = new ArrayList<Rating>(repo.values());
		for(Account a : accounts) {
			if(key.compareToIgnoreCase(a.getFirstName()) == 0 || key.compareToIgnoreCase(a.getLastName()) == 0 || key.compareToIgnoreCase(a.getPhoneNumber()) == 0) {
				out = a.toPrettyJson().toString();
				obj.put(new JSONObject(out)); //Make a Json array of json objects with a summary of the accounts, not all data
			}	
		}
		return obj.toString();
	}*/
	
}
