package com.company.myapp.interfaces;

import org.json.JSONObject;

import com.company.myapp.entities.Account;

public interface AccountRepositoryInterface {
	public int createAccount(String first_name, String last_name, String phone, String picture);
	public Account getAccount(int aid);
	public String viewAllAccounts();
	public String searchAccounts(String key);
	public void updateAccount(Account a, JSONObject obj);
	public int activateAccount(int aid);
}
