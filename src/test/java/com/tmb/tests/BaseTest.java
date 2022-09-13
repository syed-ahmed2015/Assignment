package com.tmb.tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public static WebDriver driver;
	
	@BeforeMethod
	protected void setUp(Object[] data) {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	
	@AfterMethod
	protected void tearDown() {
		driver.quit();
	}



}
