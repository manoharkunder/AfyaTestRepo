package com.keyword.engine;

import java.awt.AWTException;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.keyword.base.Base;


public class KeywordEngine {
	WebDriver driver;
	public Properties prop;
	
	public Base base;
	public WebElement element;
	
	public static Workbook book;
	public static Sheet sheet;
	
	public final String SCENARIO_SHEET_PATH = "/home/niveus/eclipse-workspace/"
			+ "UCJ_PERSONAL_LOAN/src/main/java/com/keyword/scenarios/VL1.xlsx";
	
	
	public void StartExecution(String sheetName) {
		
		FileInputStream file = null;
		
		String locatorName = "A";
		String locatorValue = "B";
		
		try {
			file  = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		int k =0;
		
			
		
		for(int i=0; i<sheet.getLastRowNum();i++) {
			
			String locatorColValue = sheet.getRow(i+1).getCell(k+1).toString().trim();
			
			if (!locatorColValue.equalsIgnoreCase("NA")) { //Xapth = <xapth value>
				locatorName = locatorColValue.split("=")[0].trim();
				locatorValue = locatorColValue.split("=")[1].trim();
			} 
			
			String action = sheet.getRow(i+1).getCell(k+2).toString().trim();
			String value =  sheet.getRow(i+1).getCell(k+3).toString().trim();
			
			switch (action) {
			case "Openbrowser":
				base = new Base();
				prop = base.init_properties();
					if(value.isEmpty() || value.equals("NA")) {
						driver = base.init_driver(prop.getProperty("browser"));
						System.out.println("oepenbrowser getting called 1");
					}else {
						
						driver = base.init_driver(value);
						System.out.println("oepenbrowser getting called");
					}
					break;
				
				
			case "enter url":
				if(value.isEmpty() || value.equals("NA")) {
					driver.get(prop.getProperty("URL"));
					System.out.println("url getting called");
				}else { 
					System.out.println("value is----------------"+value);
					driver.get(value);
					System.out.println("url getting called");
				}
				break;
				
			case "Close":
				driver.quit();
				
				break;
				
			case "scroll":
			JavascriptExecutor js = (JavascriptExecutor)driver ;
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");			
			break;
			
			case "Sleep":
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				break;
			
				default:
					break;

			case "CTRL+TAB":
				try {
					Thread.sleep(2000);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyPress(KeyEvent.VK_SPACE);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_SPACE);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				
				
							
			}
			
		
			switch (locatorName) {
			
			case "Xpath":
				for(int l=0;l<=10;l++) {
					try {
						element = driver.findElement(By.xpath(locatorValue));
						if(action.equalsIgnoreCase("Input")) {
							element.clear();
							
							element.sendKeys(value);
							break;
						}else if(action.equalsIgnoreCase("Click")){
							
							element.click();
							break;
							
								
							}
					}catch(Exception e) {
						e.getMessage();
					}
				}
				
					
				locatorName = "A";
				
				
				System.out.println("retry");
				break;
			
			default:
				break;
			
		
		}
	
		
	}
		
	}
	
}
