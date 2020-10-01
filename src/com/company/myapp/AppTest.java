package com.company.myapp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class AppTest {

	public static void main(String[] args) {
		System.out.println(TimeStamp.stamp());
	}
	
	public static void test1() {
		Account a = new Account("John", "Doe", "708-600-8360", "www.example.com/pic.jpeg");
		Account b = new Account("Dane", "Doe", "708-600-8360", "www.example.com/pic.jpeg");
		
		JSONObject ja = a.toPrettyJson();
		JSONObject jb = b.toPrettyJson();
		
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
	
	public static void test2() {
		Account a = new Account("John", "Doe", "708-600-8360", "www.example.com/pic.jpeg");
		Account b = new Account("Dane", "Doe", "708-600-8360", "www.example.com/pic.jpeg");
		
		JSONObject ja = a.toPrettyJson();
		JSONObject jb = b.toPrettyJson();
		System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
		String path = "" + System.getProperty("user.dir") + "\\src\\com\\company\\myapp\\";
		System.out.println(path + "accounts.json");
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + "accounts.json", true));
			bos.write(ja.toString().getBytes());
			bos.write(jb.toString().getBytes());
			bos.flush();
			bos.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("accounts.json"));
			JSONTokener tokenizer = new JSONTokener(bis);
			ja = (JSONObject) tokenizer.nextValue();
			jb = (JSONObject) tokenizer.nextValue();
			System.out.println(ja.toString());
			System.out.println(jb.toString());
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
