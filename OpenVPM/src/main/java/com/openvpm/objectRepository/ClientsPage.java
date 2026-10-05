package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ClientsPage {
	
	
	public WebDriver driver;
	
	public ClientsPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[.='New Client']")
	private WebElement newClientBtn;
	
	@FindBy(xpath = "//button[text()='Edit']")
	private WebElement editClientBtn;

	public WebElement getNewClientBtn() {
		return newClientBtn;
	}

	public WebElement getEditClientBtn() {
		return editClientBtn;
	}
	

}
