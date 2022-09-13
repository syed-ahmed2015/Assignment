package com.tmb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class LoginPage {

	private WebDriver driver;
	private final By textboxUsername = By.id("username");
	private final By textboxPassword = By.id("password");
	private final By buttonLogin = By.xpath("//button[@type='submit']");
	private final By flash = By.id("flash");


	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public LoginPage enterUserName(String username) {
		driver.findElement(textboxUsername).sendKeys(username);
		return this;
	}

	public LoginPage enterPassWord(String password) {
		driver.findElement(textboxPassword).sendKeys(password);
		return this;
	}
	public HomePage clickLogin() {
		driver.findElement(buttonLogin).click();
		return new HomePage(driver);
	}

	public String getTitle() {
		return driver.getTitle();
	}
	
	public String getFlashMessage() {
		return driver.findElement(flash).getText();
	}
	
	
}
