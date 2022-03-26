package com.keyword.base;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.keyword.engine.SendEmail;


public class Base {
	
	public WebDriver driver;
	public Properties prop;
	static int passedCount = 0;
	static int failureCount = 0;
	static int skippedCount = 0;
	public static ExtentTest test;
	public static ExtentReports extent;
	public static ExtentHtmlReporter htmlReporter;
	public static SimpleDateFormat simpleDateFormat;
	public static Properties properties;
	public static FileInputStream inputStream;
	public static String fileName=null;
	public static File file;
	public static Workbook workbook = null;
	public static Sheet sheet1;
	public static Sheet sheet2;
	public static FileOutputStream fos;



	public WebDriver init_driver(String browser) {
		
	if(browser.equals("chrome")) {
		
			if(prop.getProperty("headless").equals("yes")) {
				//ChromeOptions options = new ChromeOptions();
				//options.addArguments("--headless");
				driver = new ChromeDriver(); 
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
			}
			else {
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				
			}
				return driver;
			
		}
		
		return driver;
		
	}
	
	public Properties init_properties() {
		prop = new Properties();
		try {
			FileInputStream f = new FileInputStream("/home/niveus/eclipse-workspace/"
					+ "UCJ_PERSONAL_LOAN/src/main/java/com/keyword/configPackage/config.properties");
			try {
				prop.load(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prop;
	}
	
	@BeforeSuite
	public void setUp() throws IOException {
		
		properties = new Properties();
		inputStream = new FileInputStream(new File(System.getProperty("user.dir") + "/Qa.properties"));
		properties.load(inputStream);

		/********************Extent Report*******************/
		htmlReporter = new ExtentHtmlReporter(
				new File(System.getProperty("user.dir") + "/Report/AutomationReport.html"));
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/Report/ExtentConfigFile.xml"));
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("Operating System", properties.getProperty("OperatingSystem"));
		extent.setSystemInfo("System Name", properties.getProperty("SystemName"));
		extent.setSystemInfo("Environment", properties.getProperty("Environment"));
		extent.setSystemInfo("Platform", "Web Application");

		htmlReporter.config().setDocumentTitle(properties.getProperty("ReportDocumentTitle"));
		htmlReporter.config().setReportName(properties.getProperty("ReportName"));
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
		
		/********************Excel Sheet*******************/
		/*
		 * fileName=properties.getProperty("filename"); file = new
		 * File(System.getProperty("user.dir") + "/" + fileName); // Create an object of
		 * FileInputStream class to read excel file inputStream = new
		 * FileInputStream(file); //get the extension of the file String
		 * fileExtensionName = fileName.substring(fileName.indexOf(".")); // Check
		 * condition if the file is xlsx file or if the file is xls file if
		 * (fileExtensionName.equals(".xlsx") ||fileExtensionName.equals(".xls")) { //
		 * If it is xlsx or xls file then create object of XSSFWorkbook class workbook =
		 * new XSSFWorkbook(inputStream); } // Read sheet inside the workbook by its
		 * name sheet1 = workbook.getSheet(properties.getProperty("sheetname1")); sheet2
		 * = workbook.getSheet(properties.getProperty("sheetname2"));
		 */
		
		//fos = new FileOutputStream(file);
		//init_driver();
		//driver.get("https://buystaging.icicibank.com/vl/index?ius=KMOU29843KM&iup=VLRD2454");
	}
	@AfterMethod
	public void getResult(ITestResult result) throws IOException
	
	{
		int passed =0;
		int failed=0;
		int skipped=0;
		if(result.getStatus()==ITestResult.FAILURE)
		{
			 failed++;
			 File path=new File(System.getProperty("user.dir")+"/Report/ScreenShot/"+result.getName()+".png");	 
			 try{
				 // To create reference of TakesScreenshot
				 TakesScreenshot screenshot=(TakesScreenshot)driver;
				 // Call method to capture screenshot
				 File src=screenshot.getScreenshotAs(OutputType.FILE);
				 // Copy files to specific location 
				 // result.getName() will return name of test case so that screenshot name will be same as test case name
				 FileUtils.copyFile(src, path);
				 System.out.println("Successfully captured a screenshot");
				 }catch (Exception e){
				 System.out.println("Exception while taking screenshot "+e.getMessage());
				 } 	
			 test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+"Test Failed Due to below issues", ExtentColor.RED));
			 test.fail(result.getThrowable().getMessage(),MediaEntityBuilder.createScreenCaptureFromPath(path.getName()).build());
			
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			passed++;
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+"Test is Passed", ExtentColor.GREEN));
			
		}
		else
		{
			skipped++;
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+"Test is Skipped ", ExtentColor.YELLOW));
			test.skip(result.getThrowable());
			
		}
		passedCount=passedCount+passed;
		failureCount=failureCount+failed;
		skippedCount=skippedCount+skipped;
	}
	
	@AfterSuite
	public void flushReport() throws IOException, InterruptedException
	{	

		//flush all the information to the extent report
		extent.flush();
		//Send Email
		SendEmail email=new SendEmail();
		email.sendEmail(passedCount,failureCount,skippedCount);
		
		//excel 
//		fos = new FileOutputStream(file);
//		workbook.write(fos);
		//inputStream.close();
		//fos.close();
		
		//close driver 
		//driver.close();
		//driver.quit();
	}
	
	
	
}
