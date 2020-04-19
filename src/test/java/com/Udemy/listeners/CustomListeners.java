package com.Udemy.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.Udemy.base.TestBase;
import com.Udemy.utilities.MonitoringMail;
import com.Udemy.utilities.TestConfig;
import com.Udemy.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {
	
	public String messageBody;
	
	public void onTestStart(ITestResult result) {
		t = report.startTest(result.getName().toUpperCase());  
		//System.out.println("name --" + result.getName());
		if(!TestUtil.isTestRunnable(result.getName(), excel)) {
//			System.out.println("name --" + result.getName());
//			System.out.println(TestUtil.isTestRunnable(result.getName(), excel));
			throw new SkipException("Skipping the testCase" +(result.getName().toUpperCase()+" as Runmode is NO"));
		}
	}
	
	public void onTestSuccess(ITestResult result) {
		t.log(LogStatus.PASS, result.getName().toUpperCase()+"PASS");
		report.endTest(t);
		report.flush();
	}

	public void onTestSkipped(ITestResult result) {
		
		t.log(LogStatus.SKIP, result.getName().toUpperCase() + " Skipped the test");
		report.endTest(t);
		report.flush();
	}
	
	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.log(LogStatus.FAIL, result.getName().toUpperCase()+ "Failed with exception : " + result.getThrowable());
		t.log(LogStatus.FAIL, t.addScreenCapture(TestUtil.screenshotName));
		
		Reporter.log("Capturing screenshot");
		Reporter.log("<a target=\"_blank\" href= "+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<a target =\"_blank\" href= "+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
		report.endTest(t);
		report.flush();
	}
	
	public void onStart(ISuite suite) {

	}
	
	public void onFinish(ISuite suite) {
		MonitoringMail mail = new MonitoringMail();
		//System.out.println(InetAddress.getLocalHost().getHostAddress());
		
		try {
			messageBody = "http://"+ InetAddress.getLocalHost().getHostAddress() +":8080/job/DataDrivenFramework/Extent_20Reports/";
			System.out.println(messageBody);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
