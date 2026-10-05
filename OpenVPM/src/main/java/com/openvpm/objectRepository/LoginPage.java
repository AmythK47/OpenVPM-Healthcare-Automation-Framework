package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class LoginPage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "email")
	private WebElement emailTF;
	
	@FindBy(id = "password")
	private WebElement passwordTF;
	
	@FindBy(xpath = "//button[contains(.,'Sign in')]")
	private WebElement signInBtn;
	
	
	public void login(String url, String email, String password)
	{
		driver.get(url);
		
		emailTF.clear();
		emailTF.sendKeys(email);
		
		passwordTF.clear();
		passwordTF.sendKeys(password);
		
		signInBtn.click();
		
		w = new WebdriverUtility();
		w.waitTillPageTitleContains(driver, "OpenVPM: Open-Source Veterinary Practice Management", 15);
	}
	

}
