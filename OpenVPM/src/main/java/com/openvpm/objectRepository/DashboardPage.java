package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class DashboardPage {
	
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public DashboardPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//p[text()='Revenue (MTD)']/following-sibling::p")
	private WebElement revenueRecorded;

	public WebElement getRevenueRecorded() {
		return revenueRecorded;
	}
	
	public String recordRevenue()
	{
		HomePage h = new HomePage(driver);
		h.getDashboardLnk().click();
		
		driver.navigate().refresh();
		
		w = new WebdriverUtility();
		w.waitTillVisibilityOfElement(driver, revenueRecorded, 10);
		String rev = revenueRecorded.getText();
		return rev;
	}
}
