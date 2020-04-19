package com.Udemy.rough;

import java.util.Date;

public class TimeStamp {
	public static void main(String[] args) {
		
		Date date = new Date();
		String screenshotName = date.toString().replace(":", "_").replace(" ", "_") +".jpg"; 
		System.out.println(screenshotName);
	}
}
