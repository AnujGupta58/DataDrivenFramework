package com.Udemy.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {
		
//		System.out.println(System.getProperty("user.dir"));
//		Properties prop = new Properties();
//		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
//		prop.load(fis);
//		System.out.println(prop.getProperty("browser"));
		
		Object[][] data = new Object[1][3];
		data[0][0]="ABC";
		data[0][1]="def";
		data[0][2]="2223344";
		
		System.out.println(data.length);
		String msg = "Hello world";
		
		System.out.println();
				
	}
}
