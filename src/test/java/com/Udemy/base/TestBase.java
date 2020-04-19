package com.Udemy.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.Udemy.utilities.ExcelUtility;
import com.Udemy.utilities.ExtentManager;
import com.Udemy.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	/*
	 * WebDriver - done	
	 * Properties - done
	 * Logs 
	 * Extent reports
	 * DB
	 * Excel
	 * Mail
	 * ReportNG
	 * Jenkins
	 * 
	 */
	
	public static WebDriver driver = null;
	public static Properties config = new Properties();
	public static Properties or = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");   // Logs for selenium and application
	public static ExcelUtility excel = new ExcelUtility(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testData.xlsx"); 
	public static WebDriverWait wait;
	public static WebElement dropdown;
	public static String browser;
	
	public ExtentReports report = ExtentManager.getInstance();  // Extent Reports
	public static ExtentTest t;
	
	@BeforeSuite
	public void setUp() {
		
		if(driver == null) {
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("config properties loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				or.load(fis);
				log.debug("or properties loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()) {
//			
//			browser = System.getenv("browser");
//		}
//		else {
//			browser = config.getProperty("browser");
//		}
//		
//		config.setProperty("browser", browser);
		
		if(config.getProperty("browser").equals("firefox")) {
			//System.getProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			log.debug("firefox browser is launched");
		}
		else if(config.getProperty("browser").equals("chrome")) {
			//System.getProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			log.debug("chrome browser is launched");
		}
		else if(config.getProperty("browser").equals("ie")) {
			System.getProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			log.debug("ie browser is launched");
		}
		
		driver.get(config.getProperty("testSiteURL"));
		log.debug("Navigate to " + config.getProperty("testSiteURL"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		wait = new WebDriverWait(driver,5);
	}
	
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void click(String locator) {
		driver.findElement(By.cssSelector(or.getProperty(locator))).click();
		t.log(LogStatus.INFO, "clicking on : " + locator);
	}
	
	public void type(String locator, String value) {
		driver.findElement(By.cssSelector(or.getProperty(locator))).sendKeys(value);
		t.log(LogStatus.INFO, "Typing in : "+locator+ " and values are : " +value);
	}
	
	public void select(String locator, String value) {
		dropdown = driver.findElement(By.cssSelector(or.getProperty(locator)));
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		t.log(LogStatus.INFO, "Selecting from dropdown : "+ locator + "avd value is "+ value);
	}
	
	public void verifyEquals(String expected, String actual) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
		}
		catch (Throwable th) {
			TestUtil.captureScreenshot();
			
			// For reportng 
			Reporter.log("<br>" + "Verification fails : " + th.getMessage()+ "<br>");
			Reporter.log("<a target =\"_blank\" href= "+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			// For extent reports
			t.log(LogStatus.FAIL, "Verification fails : " + th.getMessage());
			t.log(LogStatus.FAIL, t.addScreenCapture(TestUtil.screenshotName));
		}
	}
	
	@AfterSuite
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
		log.debug("test execution completed");
	}
}
