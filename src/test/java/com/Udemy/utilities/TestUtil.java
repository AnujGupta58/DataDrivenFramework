package com.Udemy.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.Udemy.base.TestBase;

public class TestUtil extends TestBase {

	public static String screenshotPath;
	public static String screenshotName;

	public static void captureScreenshot() throws IOException {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date date = new Date();
		screenshotName = date.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		FileUtils.copyFile(src,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));
	}

	// Data provider with parameters as String and return data of array of string
//	@DataProvider(name = "dp")
//	public Object[][] getData(Method m) {
//		String sheetName = m.getName();
//		int rows = excel.getRowCount(sheetName);
//		int cols = excel.getColumnCount(sheetName);
//		Object data[][] = new Object[rows - 1][cols];
//
//		for (int rowNum = 1; rowNum < rows; rowNum++) {
//			for (int colNum = 0; colNum < cols; colNum++) {
//				data[rowNum - 1][colNum] = excel.getCellData(sheetName, colNum, rowNum);
//				// System.out.println("data----"+data[rowNum-1][colNum]);
//			}
//		}
//		return data;
//	}
	
	//Data provider which return data as a hastable.
	@DataProvider(name="dp")
	public Object[][] getData(Method m){
		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		Object[][] data = new Object[rows-1][1];
		Hashtable<String, String> table = null;
		for(int rowNum = 1; rowNum<rows; rowNum++) {
			
			table = new Hashtable<String, String>();
			
			for(int colNum = 0;colNum<cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 0), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum-1][0] = table;
			}
		}
		return data;
	}

	
	public static boolean isTestRunnable(String testName, ExcelUtility excel) {
		String sheetName = "testSuite";
		int rows = excel.getRowCount(sheetName);
		//System.out.println("rowss "+rows);
		for (int rowNum = 1; rowNum < rows; rowNum++) {

			String testCase = excel.getCellData(sheetName, 0, rowNum);
			//System.out.println("testCases "+testCase instanceof String);

			if (testCase.equalsIgnoreCase(testName)) {

				String runmode = excel.getCellData(sheetName, 1, rowNum);
				//System.out.println("runmode "+runmode);
				
				if (runmode.equalsIgnoreCase("Y")) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
}
