package com.company.myapp;

public class AppTest {

	public static void main(String[] args) {
		AccountRepository ar = new AccountRepository();
		ar.creatAccount("Jane", "Doe", "708-600-8360", "www.example.com");
		ar.creatAccount("Larry", "Doe", "123-456-7890", "www.java.com");
		System.out.println(ar.searchAccounts("708-600-8360"));
	}

}
