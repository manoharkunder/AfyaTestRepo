package com.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


public class MultiBrowser {

	WebDriver driver=null;
	
	@Parameters("browserName")
	@BeforeTest
	public void setUP(String browserName)
	{
		//System.out.println("browser name is: "+browserName);
		if(browserName.equalsIgnoreCase("chrome"))
		{
			System.out.println("browser is : "+browserName);
			driver=new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			System.out.println("browser is : "+browserName);
			driver=new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			System.out.println("browser is : "+browserName);

			System.out.println("edge browser not found ");
		}
	}
	
	@Test
	public void demo1() throws Exception
	{
		driver.get("https://www.google.com");
		Thread.sleep(3000);
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.close();
		System.out.println("test cpompleted successfullly");
	}
}
