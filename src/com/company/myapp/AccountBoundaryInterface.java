package com.company.myapp;

import java.util.Hashtable;

public interface AccountBoundaryInterface {
	public int creatAccount(String first_name, String last_name, String phone, String picture);
	public Account getAccount(int aid);
	public int activateAccount(int aid);
	public Hashtable<Integer, Account> getRepo();
}
