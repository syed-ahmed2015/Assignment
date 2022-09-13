package com.tmb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public final class HomePage {
	private WebDriver driver;
	private final By subheader = By.className("subheader");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	
	public String getsubheaderText() {
		return driver.findElement(subheader).getText();
	}
}
