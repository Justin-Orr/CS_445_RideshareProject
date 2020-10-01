package com.company.myapp;

import java.util.Hashtable;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class AccountRepository implements AccountBoundaryInterface {
	
	private Hashtable<Integer, Account> repo;
	String file_name = "accounts.json";
	
	public AccountRepository() {
		repo = new Hashtable<Integer, Account>();
	}
	
	public int creatAccount(String first_name, String last_name, String phone, String picture) {
		Account a = new Account(first_name, last_name, phone, picture);
		int error_code = writeAccountToFile(a);
		System.out.print(error_code);
		return a.getID();
	}
	
	public Account getAccount(int aid) {
		if(repo.containsKey(aid))
			return repo.get(aid);
		else
			return null;
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
	
	public Hashtable<Integer, Account> getRepo() {
		return repo;
	}
	
	private int writeAccountToFile(Account a) {
		JSONObject ja = new JSONObject(a);
		try (FileWriter file = new FileWriter(file_name, true)) {
			file.write(ja.toString());
			file.flush();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/*public Account readAccountFromFile(int aid) {
		try (FileReader reader = new FileReader(file_name)) {
			JSONTokener tokenizer = new JSONTokener(reader);
			JSONObject ja;
			while(tokenizer.more()) {
				JSONObject ja = (JSONObject) tokenizer.nextValue();
			}
			Account a = Account.jsonToAccount(ja);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
}
