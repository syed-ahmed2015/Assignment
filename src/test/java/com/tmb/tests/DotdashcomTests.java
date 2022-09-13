package com.tmb.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.tmb.pages.LoginPage;

public final class DotdashcomTests extends BaseTest {
	
	@Test
	public void loginLogoutTest() {
		driver.get("http://localhost:7080/login");
		String homelabel = new LoginPage(driver)
				.enterUserName("tomsmith").enterPassWord("SuperSecretPassword!").clickLogin()
				.getsubheaderText();
		
		assertEquals(homelabel, "Welcome to the Secure Area. When you are done click logout below.");
		
	}
	
	@Test
	public void newTest() {
		driver.get("http://localhost:7080/login");
		LoginPage login = new LoginPage(driver);
		login.enterUserName("tomsmith").enterPassWord("SuperSecretPassword!").clickLogin();
		
		assertEquals(new LoginPage(driver).getFlashMessage(), "Your username is invalid!"); 
	}
	
	@Test
	public void test3() {
		
		driver.get("http://localhost:7080/checkboxes");
		List<WebElement> chkboxs = driver.findElements(By.xpath("//input[@type='checkbox']"));
		assertFalse(chkboxs.get(0).isSelected());
		chkboxs.get(0).click();
		assertTrue(chkboxs.get(0).isSelected());
		assertTrue(chkboxs.get(1).isSelected());
		chkboxs.get(1).click();
		assertFalse(chkboxs.get(1).isSelected());
	}
	
	@Test
	public void test4() {
		
		driver.get("http://localhost:7080/context_menu");
		Actions actions = new Actions(driver);
		WebElement elementLocator = driver.findElement(By.id("hot-spot"));
		actions.contextClick(elementLocator).perform();
		assertEquals("You selected a context menu", driver.switchTo().alert().getText());
	}
	
	@Test(enabled = false)
	public void test5() {
		
		driver.get("http://localhost:7080/drag_and_drop");
		Actions act=new Actions(driver);
		WebElement From = driver.findElement(By.id("column-a"));
		WebElement To = driver.findElement(By.id("column-b"));
		act.dragAndDrop(From, To).perform();
		String atext = driver.findElement(By.xpath("//div[@id='column-a']/header")).getText();
		assertEquals(atext, "B");
		String btext = driver.findElement(By.xpath("//div[@id='column-b']/header")).getText();
		assertEquals(btext, "A");
	}
	
	@Test
	public void test6() {
		
		driver.get("http://localhost:7080/dropdown");
		WebElement dropdown = driver.findElement(By.id("dropdown"));
		Select sel = new Select(dropdown);
		sel.selectByVisibleText("Option 1");
		assertEquals(sel.getFirstSelectedOption().getText(), "Option 1");
		sel.selectByVisibleText("Option 2");
		assertEquals(sel.getFirstSelectedOption().getText(), "Option 2");
	}
	
	@Test
	public void test7() {
		
		driver.get("http://localhost:7080/dynamic_content?with_content=static");
		String text1 = driver.findElement(By.xpath("//*[@id='content']/div[3]/div[2]")).getText();
		driver.findElement(By.linkText("click here")).click();
		String text2 = driver.findElement(By.xpath("//*[@id='content']/div[3]/div[2]")).getText();
		assertNotEquals(text1, text2);
	}
	
	@Test
	public void test8() {
		
		driver.get("http://localhost:7080/dynamic_controls");
		driver.findElement(By.xpath("//button[text()='Remove']")).click();
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("checkbox")));
		driver.findElement(By.xpath("//button[text()='Add']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
		driver.findElement(By.xpath("//button[text()='Enable']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		String messageEnabled = driver.findElement(By.id("message")).getText();
		assertEquals(messageEnabled, "It's enabled!");
		assertTrue(driver.findElement(By.xpath("//input[@type='text']")).isEnabled());
		driver.findElement(By.xpath("//button[text()='Disable']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		String messageDisabled = driver.findElement(By.id("message")).getText();
		assertEquals(messageDisabled, "It's disabled!");
		assertFalse(driver.findElement(By.xpath("//input[@type='text']")).isEnabled());
	}
	
	@Test
	public void test9() {
		
		driver.get("http://localhost:7080/dynamic_loading/2");
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
		String text = driver.findElement(By.xpath("//*[@id='finish']/h4")).getText();
		assertEquals(text, "Hello World!");
	}
	
	@Test
	public void test10() throws Exception {
		
		driver.get("http://localhost:7080/download");
		driver.findElement(By.linkText("some-file.txt")).click();
		Thread.sleep(5000);
		boolean fileExists = false;
		File dir = new File(System.getProperty("user.home")+"\\Downloads");
		File[] dir_contents = dir.listFiles();
		if (dir_contents != null) {
		       for (File dir_content : dir_contents) {
		            if (dir_content.getName().equals("some-file.txt")) {
		            	fileExists = true;
		            }      
		       }
		   }
		assertTrue(fileExists);
		new File(System.getProperty("user.home")+"\\Downloads\\some-file.txt").delete();
	}
	
	@Test
	public void test11() throws Exception {
		
		driver.get("http://localhost:7080/floating_menu");
		WebElement element = driver.findElement(By.linkText("Elemental Selenium"));
		((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView();", element);
		assertTrue(driver.findElement(By.linkText("Home")).isDisplayed());
		assertTrue(driver.findElement(By.linkText("News")).isDisplayed());
		assertTrue(driver.findElement(By.linkText("Contact")).isDisplayed());
		assertTrue(driver.findElement(By.linkText("About")).isDisplayed());
		
	}
	

	@Test
	public void test12() throws Exception {
		
		driver.get("http://localhost:7080/iframe");
		driver.switchTo().frame(0);
		driver.findElement(By.id("tinymce")).clear();
		driver.findElement(By.id("tinymce")).sendKeys("Test");
		String result = driver.findElement(By.tagName("p")).getText();
		assertEquals(result, "Test");
	}
	
	@Test
	public void test13() {
		
		driver.get("http://localhost:7080/hovers");
		List<WebElement> imgs = driver.findElements(By.xpath("//*[@id='content']//img"));
		List<WebElement> names = driver.findElements(By.xpath("//h5"));
		Actions actions = new Actions(driver);
		actions.moveToElement(imgs.get(0)).build().perform();
		assertEquals(names.get(0).getText(), "name: user1");
		actions.moveToElement(imgs.get(1)).build().perform();
		assertEquals(names.get(1).getText(), "name: user2");
		actions.moveToElement(imgs.get(2)).build().perform();
		assertEquals(names.get(2).getText(), "name: user3");
	}
	
	@Test
	public void test14() {
		
		driver.get("http://localhost:7080/javascript_alerts");
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		Alert alt = driver.switchTo().alert();
		assertEquals(alt.getText(), "I am a JS Alert");
		alt.accept();
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		Alert confirmalt = driver.switchTo().alert();
		assertEquals(confirmalt.getText(), "I am a JS Confirm");
		confirmalt.accept();
		driver.findElement(By.xpath("//*[text()='Click for JS Prompt']")).click();
		Alert msgalt = driver.switchTo().alert();
		msgalt.sendKeys("Testing");
		msgalt.accept();
		String result = driver.findElement(By.id("result")).getText();
		assertTrue(result.contains("Testing"));
	}
	
	@Test
	public void test15() {
		  
		  driver.get("http://localhost:7080/javascript_error");
		  driver.manage().window().maximize();
	      LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
	      List<LogEntry> lg = logEntries.filter(Level.ALL);
	      System.out.println(lg.get(0));
	      assertTrue(lg.get(0).toString().contains("Uncaught TypeError: Cannot read properties of undefined (reading 'xyz')"));
	      
	}
	
	@Test
	public void test16() {
		
		driver.get("http://localhost:7080/windows");
		driver.findElement(By.linkText("Click Here")).click();
		String parent=driver.getWindowHandle();
		Set<String>s=driver.getWindowHandles();
		Iterator<String> I1= s.iterator();

		while(I1.hasNext()){
			String child_window=I1.next();
			if(!parent.equals(child_window)){
				driver.switchTo().window(child_window);
				assertEquals(driver.getTitle(), "New Window");
			}
		}
	}
	
	@Test
	public void test17() {
		
		String[] messages = { "Action successful", "Action unsuccesful, please try again", "Action unsuccessful"};
		driver.get("http://localhost:7080/notification_message_rendered");
		driver.findElement(By.linkText("Click here")).click();
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, messages);
		String result = driver.findElement(By.id("flash")).getText().split("\n")[0];
		System.out.println(result);
		assertTrue(list.contains(result));
	}
	
	
	
	
	
	
	
	
	
	
	


	
}
