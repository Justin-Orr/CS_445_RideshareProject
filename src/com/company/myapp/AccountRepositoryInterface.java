package com.company.myapp;

import org.json.JSONObject;

public interface AccountRepositoryInterface {
	public int createAccount(String first_name, String last_name, String phone, String picture);
	public Account getAccount(int aid);
	public String viewAllAccounts();
	public String searchAccounts(String key);
	public void updateAccount(Account a, JSONObject obj);
	public int activateAccount(int aid);
}
