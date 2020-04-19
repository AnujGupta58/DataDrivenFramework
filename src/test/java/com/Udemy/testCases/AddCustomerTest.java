package com.Udemy.testCases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Udemy.base.TestBase;
import com.Udemy.utilities.TestUtil;

public class AddCustomerTest extends TestBase{
	
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {
		
		if(!data.get("runMode").equalsIgnoreCase("y")) {
			throw new SkipException("RunMode is no for this data set");
		}
		click("addCustomerbtn");
		type("firstName", data.get("firstName"));
		type("lastName", data.get("lastName"));
		type("postCode", data.get("postCode"));
		Thread.sleep(3000);
		click("addCustomer");
		Thread.sleep(3000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alertText")));
		alert.accept();
		//Assert.fail("Cannot add customer");
	}
	
//	@DataProvider
//	public Object[][] getData() {
//		String sheetName = "AddCustomerTest";
//		int rows = excel.getRowCount(sheetName);
//		int cols = excel.getColumnCount(sheetName);
//		//System.out.println(rows + " " +cols );
//		Object data[][] = new Object[rows-1][cols];
//		
//		for(int rowNum=1;rowNum<rows;rowNum++) {
//			for(int colNum=0;colNum<cols;colNum++) {
//				data[rowNum-1][colNum] = excel.getCellData(sheetName, colNum, rowNum);
//				//System.out.println("data----"+data[rowNum-1][colNum]);
//			}
//		}
//		return data;
////		Object[][] data = new Object[1][4];
////		data[0][0]="ABC";
////		data[0][1]="def";
////		data[0][2]="2223344";
////		data[0][3]="Customer added successfully";
////		return data;
//	}
}
