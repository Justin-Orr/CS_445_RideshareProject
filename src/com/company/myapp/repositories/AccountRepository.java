package com.company.myapp.repositories;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.company.myapp.entities.Account;
import com.company.myapp.interfaces.AccountRepositoryInterface;

public class AccountRepository implements AccountRepositoryInterface {
	
	private static Hashtable<Integer, Account> repo = new Hashtable<Integer, Account>();
	
	public AccountRepository() {
		//Default constructor
	}
	
	public int createAccount(String first_name, String last_name, String phone, String picture) {
		Account a = new Account(first_name, last_name, phone, picture);
		repo.put(a.getAid(), a);
		return a.getAid();
	}
	
	public Account getAccount(int aid) {
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
	}
	
	public int activateAccount(int aid) {
		Account a = getAccount(aid);
		if(a == null) {
			return -1;
		}
		else {
			a.setActive(true);
			return 0;
		}
	}
	
}
