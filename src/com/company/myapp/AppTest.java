package com.company.myapp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class AppTest {

	public static void main(String[] args) {
		AccountRepository ar = new AccountRepository();
		ar.creatAccount("John", "Doe", "708-600-8360", "www.example.com/pic.jpeg");
	}
	
	public static void test1() {
		Account a = new Account("John", "Doe", "708-600-8360", "www.example.com/pic.jpeg");
		Account b = new Account("Dane", "Doe", "708-600-8360", "www.example.com/pic.jpeg");
		
		JSONObject ja = a.toJson();
		JSONObject jb = b.toJson();
		
		//Write to JSON file
		try (FileWriter file = new FileWriter("accounts.json")) {
			file.write(ja.toString());
			file.write(jb.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (FileReader reader = new FileReader("accounts.json")) {
			JSONTokener tokenizer = new JSONTokener(reader);
			ja = (JSONObject) tokenizer.nextValue();
			jb = (JSONObject) tokenizer.nextValue();
			System.out.println(ja.toString());
			System.out.println(jb.toString());
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
