package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class NewInvoicePage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public NewInvoicePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//span[text()='Estimate']/../input")
	private WebElement estimateChckBox;
	
	@FindBy(xpath = "//input[@placeholder='Search clients...']")
	private WebElement searchClientsTF;
	
	@FindBy(xpath = "//button/span[@class='font-medium']")
	private WebElement clientSelectorBtn;
	
	@FindBy(xpath = "//label[contains(text(),'Patient')]/../select")
	private WebElement patientSelectDD;
	
	@FindBy(xpath = "//button[contains(.,'Search services')]")
	private WebElement searchservicesBtn;
	
	@FindBy(xpath = "//input[contains(@placeholder,'Type a service name')]")
	private WebElement serviceNameTF;
	
	@FindBy(xpath = "//button[@role='option']")
	private WebElement serviceSelectorBtn;
	
	@FindBy(xpath = "//button[.='Add']")
	private WebElement addServiceBtn;
	
	@FindBy(xpath = "//input[@type='date']")
	private WebElement dueDateInvoice;
	
	@FindBy(xpath = "//button[text()='Create Invoice']")
	private WebElement createInvoiceBtn;
	
	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement cancelBtn;
	
	
	
	public void newInvoice(String clientName, String serviceName, String date)
	{
		HomePage h = new HomePage(driver);
		w = new WebdriverUtility();
		w.waitTillElementToBeClickable(driver, h.getBillingLnk(), 10);
		h.getBillingLnk().click();
		
		BillingPage b = new BillingPage(driver);
		w = new WebdriverUtility();
		w.waitTillElementToBeClickable(driver, b.getNewInvoiceLnk(), 10);
		
		b.getNewInvoiceLnk().click();
		
		w.waitTillVisibilityOfElement(driver, searchClientsTF, 10);
		searchClientsTF.clear();
		searchClientsTF.sendKeys(clientName);
		
		w.waitTillElementToBeClickable(driver, clientSelectorBtn, 10);
		clientSelectorBtn.click();
		
		searchservicesBtn.click();
		serviceNameTF.sendKeys(serviceName);
		w.waitTillElementToBeClickable(driver, serviceSelectorBtn, 10);
		serviceSelectorBtn.click();
		
		addServiceBtn.click();
		
		dueDateInvoice.clear();
		dueDateInvoice.sendKeys(date);
		
		createInvoiceBtn.click();

		w.waitTillVisibilityOfElement(driver, b.getNewInvoiceLnk(), 10);
	}
	
	

}
