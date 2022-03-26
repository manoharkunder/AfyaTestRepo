package com.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Demo1 {
	public static void main(String[] args) throws Exception {
		ChromeOptions opt = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.managed_default_content_settings.geolocation", 2);
		opt.setExperimentalOption("prefs", prefs);

		// opt.addArguments("--headless");

		WebDriver driver = new ChromeDriver(opt);

		driver.manage().window().maximize();

		driver.get("https://buystaging.icicibank.com/ucj/index");

		Thread.sleep(2000);
		WebElement okBtn = driver.findElement(By.xpath("//button[text()='OK']"));
		okBtn.click();

		/*
		 * ((JavascriptExecutor) driver).executeScript("window.open()");
		 * ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		 * driver.switchTo().window(tabs.get(1)); driver.get(
		 * "chrome://settings/content/siteDetails?site=https%3A%2F%2Fbuystaging.icicibank.com"
		 * );
		 */

		// Thread.sleep(3000);
		// System.out.println(driver.getCurrentUrl());

		Actions act = new Actions(driver);
		/*
		 * act.sendKeys(Keys.TAB);
		 */
		// act.sendKeys(Keys.TAB);
		act.sendKeys(Keys.TAB).perform();
		act.release().perform();
		act.sendKeys(Keys.ENTER).perform();

		/*
		 * act.sendKeys(Keys.TAB); act.sendKeys(Keys.TAB);
		 * act.sendKeys(Keys.TAB).build().perform();
		 * 
		 * act.sendKeys(Keys.ENTER);
		 */

		/*
		 * act.sendKeys(Keys.UP); act.sendKeys(Keys.UP);
		 * act.sendKeys(Keys.ENTER).build().perform();
		 */

//		Robot r=new Robot();
//		for(int i=0;i<3;i++)
//		{
//		r.keyPress(KeyEvent.VK_TAB);
//		r.keyRelease(KeyEvent.VK_TAB);
//		}
//		r.keyPress(KeyEvent.VK_ENTER);
//		r.keyRelease(KeyEvent.VK_ENTER);
//		
//		r.keyPress(KeyEvent.VK_DOWN);
//		r.keyRelease(KeyEvent.VK_DOWN);
//		
//		r.keyPress(KeyEvent.VK_DOWN);
//		r.keyRelease(KeyEvent.VK_DOWN);
//	
//		r.keyPress(KeyEvent.VK_ENTER);
//		r.keyRelease(KeyEvent.VK_ENTER);
		// driver.close();
		// driver.switchTo().window(tabs.get(0));
		System.out.println(driver.getCurrentUrl());

		TakesScreenshot scrShot = ((TakesScreenshot) driver);

		// Call getScreenshotAs method to create image file

		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// Move image file to new destination

		File DestFile = new File("/home/niveus/eclipse-workspace/UCJ_PERSONAL_LOAN/icici/test.png");

		// Copy file at destination

		FileUtils.copyFile(SrcFile, DestFile);
		/*
		 * driver.navigate().refresh(); Thread.sleep(5000); try { WebElement okBtn =
		 * driver.findElement(By.xpath("//button[text()='OK']"));
		 * 
		 * if(okBtn.isEnabled())
		 * 
		 * System.out.println("Found Ok button--------"+okBtn.getText());
		 * 
		 * 
		 * } catch (Exception e) { System.out.println("Ok button NOT FOUND"); } }
		 */
	}
}
