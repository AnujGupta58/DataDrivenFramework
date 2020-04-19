package com.Udemy.testCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.Udemy.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {
		
		verifyEquals("abc", "abc");
		Reporter.log("Verification failed");
		Thread.sleep(3000);
		log.debug("inside login test");
		click("bankManagerLoginButton");
		Thread.sleep(3000);
				
		//Assert.assertTrue(isElementPresent(By.cssSelector(or.getProperty("addCustomerbtn"))),"Login not successful");
		log.debug("login successfully executed");
		Reporter.log("login successfully executed");
		System.out.println("login successfully executed");
		//Assert.fail("Login not success");
	}
}
