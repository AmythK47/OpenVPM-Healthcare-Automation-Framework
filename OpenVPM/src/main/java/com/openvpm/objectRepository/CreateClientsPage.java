package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class CreateClientsPage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public CreateClientsPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "firstName")
	private WebElement firstNameTF;

	@FindBy(id = "lastName")
	private WebElement lastNameTF;

	@FindBy(id = "email")
	private WebElement emailTF;
	
	@FindBy(id = "phone")
	private WebElement phoneTF;
	
	@FindBy(xpath = "//label/input")
	private WebElement cnfrmChckbox;
	
	@FindBy(xpath = "//button[text()='Create Client']")
	private WebElement createClientBtn;
	
	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement cancelBtn;
	
	public void createClient(String firstName, String lastName, String email, String phone) 
	{
		HomePage h = new HomePage(driver);
		h.getClientsLnk().click();
		
		ClientsPage cp = new ClientsPage(driver);
		cp.getNewClientBtn().click();
		
		firstNameTF.clear();
		firstNameTF.sendKeys(firstName);
		
		lastNameTF.clear();
		lastNameTF.sendKeys(lastName);
		
		emailTF.clear();
		emailTF.sendKeys(email);
		
		phoneTF.clear();
		phoneTF.sendKeys(phone);
		
		cnfrmChckbox.click();
		
		createClientBtn.click();
		
		w = new WebdriverUtility();
		w.waitTillElementToBeClickable(driver, cp.getEditClientBtn(), 10);
				
		
	}
	
}
