package com.Udemy.testCases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.Udemy.base.TestBase;
import com.Udemy.utilities.TestUtil;

public class OpenCustomerAccount extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openCustomerAccount(Hashtable<String, String> data) throws InterruptedException {
		
		click("openAccount");
		select("customer", data.get("customer"));
		select("currency",data.get("currency"));
		Thread.sleep(3000);
		click("process");
		Thread.sleep(3000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
	}
}
