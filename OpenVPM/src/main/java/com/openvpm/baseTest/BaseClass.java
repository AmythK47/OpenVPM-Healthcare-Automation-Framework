package com.openvpm.baseTest;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.Status;
import com.openvpm.genericUtility.PropertiesUtility;
import com.openvpm.genericUtility.ThreadlocalUtility;
import com.openvpm.genericUtility.WebdriverUtility;
import com.openvpm.objectRepository.HomePage;
import com.openvpm.objectRepository.LoginPage;

/**
 * This class contains the Pre-conditions and Post-conditions for the Test Scripts.
 * testNg configuration annotations have been used here.
 * 
 * @author Amit
 */

public class BaseClass {
	
	public WebDriver driver;
	public WebdriverUtility w;
	public PropertiesUtility p;
	
	@BeforeSuite
	public void configBS()
	{
		//Database connection
		
	}

	@BeforeClass(groups= {"smoke","integration", "system"})
	public void configBC() throws Exception
	{
		//launch Browser
		p = new PropertiesUtility();
		String browser = p.readDataFromPropertiesFile("browser");
		
		w = new WebdriverUtility();
		driver = w.launchBrowser(browser);
		w.maximizeBrowser(driver);
		w.implicitWait(driver, 10);
		
		ThreadlocalUtility.setDriver(driver);
		Reporter.log("Browser Launched Successfully",true);
	}
	
	@BeforeMethod(groups= {"smoke","integration", "system"})
	public void configBM() throws Exception
	{
		//login
		String url = p.readDataFromPropertiesFile("url");
		String email = p.readDataFromPropertiesFile("email");
		String password = p.readDataFromPropertiesFile("password");
		
		LoginPage l = new LoginPage(ThreadlocalUtility.getDriver());
		l.login(url, email, password);
		
		Reporter.log("Successfull Login",true);
		
	}
	
	@AfterMethod(groups= {"smoke","integration", "system"})
	public void configAM()
	{
		//logout
		HomePage h = new HomePage(ThreadlocalUtility.getDriver());
		h.signOut();
		Reporter.log("Successfull Logout",true);
	}
	
	@AfterClass(groups= {"smoke","integration", "system"})
	public void configAC()
	{
		//close browser
		w.closeBrowser(driver);
		Reporter.log("Successfull Closed Browser",true);
	}
	
	@AfterSuite
	public void configAS()
	{
		//close database connection
	}
	
}
