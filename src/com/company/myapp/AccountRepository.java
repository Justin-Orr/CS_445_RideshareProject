package com.company.myapp;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;

import org.json.JSONObject;
import org.json.JSONTokener;

public class AccountRepository implements AccountBoundaryInterface {
	
	String file_path;
	
	public AccountRepository() {
		///The File.separator is to accommodate forward and backslashes based on the operating system.
		file_path = System.getProperty("catalina.base") + File.separator + "accounts.json";
	}
	
	public int creatAccount(String first_name, String last_name, String phone, String picture) {
		Account a = new Account(first_name, last_name, phone, picture);
		writeAccountToFile(a);
		return a.getAid();
	}
	
	private int writeAccountToFile(Account a) {
		JSONObject ja = new JSONObject(a);
		
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file_path, true));
			bos.write(ja.toString().getBytes());
			bos.flush();
			bos.close();
			return 0;
		}
		catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public Account getAccount(int aid) {
		
		Account a = null;
		
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file_path));
			JSONTokener tokenizer = new JSONTokener(bis);
			JSONObject ja = new JSONObject(); 
			while(tokenizer.more()) {
				ja = (JSONObject) tokenizer.nextValue();
				if(ja.getInt("aid") == aid) {
					a = Account.jsonToAccount(ja);
					break;
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return a;
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
