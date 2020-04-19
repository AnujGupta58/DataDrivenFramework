	package com.Udemy.utilities;

import java.io.File;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;

public class ExtentManager {
	
	private static ExtentReports extent;
	
	public static ExtentReports getInstance() {
		if(extent == null) {
			extent = new ExtentReports(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\extentReport.html",true,DisplayOrder.OLDEST_FIRST,NetworkMode.ONLINE);
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\extentconfig\\ReportsConfig.xml"));
		}
		return extent;
	}
}
