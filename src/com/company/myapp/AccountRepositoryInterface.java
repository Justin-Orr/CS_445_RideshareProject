package com.company.myapp;

public interface AccountRepositoryInterface {
	public int creatAccount(String first_name, String last_name, String phone, String picture);
	public Account getAccount(int aid);
	public int activateAccount(int aid);
}