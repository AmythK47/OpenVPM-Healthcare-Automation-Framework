package com.openvpm.objectRepository;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.openvpm.genericUtility.WebdriverUtility;

public class BillingPage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public BillingPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[.='New Invoice']")
	private WebElement newInvoiceLnk;
	
	@FindBy(xpath = "//button[@title='Mark as Sent']")
	private WebElement markAsSentInvoiceBtn;
	
	@FindBy(xpath = "//table/tbody/tr/td[contains(@class,'font-medium')]")
	private List<WebElement> clientList;
	
	@FindBy(xpath = "//tbody/tr/td[contains(@class,'px-2 py-3 text-muted-foreground')]")
	private WebElement openClientPayment;
	
	@FindBy(xpath = "//table/tbody/tr/td[@class='px-4 py-3']/span")
	private WebElement paymentStatus;
	
	@FindBy(xpath = "//button[.='Record Payment']")
	private WebElement recordPaymentBtn;

	@FindBy(xpath = "//label[text()='Method']/../select")
	private WebElement methodSlctrDD;
	
	@FindBy(xpath = "//button[text()='Cancel']/../button[text()='Record Payment']")
	private WebElement cnfrmPaymentBtn;
	
	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement cancelBtn;
	
	@FindBy(xpath = "//span[text()='Balance:']/span")
	private WebElement balanceCheck;
	
	
	@FindBy(xpath = "//table[@class='w-full text-sm']/tbody/tr[@class='border-b border-border/50 last:border-0']/td")
	private WebElement serviceCheckfield;
	
	

	public WebElement getNewInvoiceLnk() {
		return newInvoiceLnk;
	}

	public WebElement getMarkAsSentInvoiceBtn() {
		return markAsSentInvoiceBtn;
	}

	public List<WebElement> getClientList() {
		return clientList;
	}

	public WebElement getPaymentStatus() {
		return paymentStatus;
	}

	public WebElement getRecordPaymentBtn() {
		return recordPaymentBtn;
	}

	public WebElement getMethodSlctrDD() {
		return methodSlctrDD;
	}

	public WebElement getCnfrmPaymentBtn() {
		return cnfrmPaymentBtn;
	}

	public WebElement getCancelBtn() {
		return cancelBtn;
	}
	
	public WebElement getBalanceCheck() {
		return balanceCheck;
	}
	
	public WebElement getOpenClientPayment() {
		return openClientPayment;
	}

	public WebElement getServiceCheckfield() {
		return serviceCheckfield;
	}
	
	public boolean verifyPayment() throws InterruptedException
	{
	
		Thread.sleep(2000);
		return balanceCheck.getText().contains("$0.00");
		
	}

	public void registerPayment(String clientName, String paymentMethod) throws InterruptedException
	{
	    w = new WebdriverUtility();

	    w.waitTillElementToBeClickable(driver, newInvoiceLnk, 10);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    
	    Thread.sleep(2000);

	    WebElement client = wait.until(driver -> {

	        List<WebElement> clients = driver.findElements(
	            By.xpath("//table/tbody/tr/td[contains(@class,'font-medium')]")
	        );

	        for(WebElement e : clients)
	        {
	            if(e.getText().equals(clientName))
	            {
	                return e;
	            }
	        }

	        return null;
	    });

	    w.moveToElementAndClick(driver, client);

	    w.waitTillElementToBeClickable(driver, markAsSentInvoiceBtn, 10);
	    w.moveToElementAndClick(driver, markAsSentInvoiceBtn);

	    w.waitTillElementToBeClickable(driver, recordPaymentBtn, 10);
	    recordPaymentBtn.click();

	    w.waitTillElementToBeClickable(driver, methodSlctrDD, 10);
	    w.selectDropdownByText(methodSlctrDD, paymentMethod);

	    w.waitTillElementToBeClickable(driver, cnfrmPaymentBtn, 10);
	    cnfrmPaymentBtn.click();

	}


	
	public boolean verifyServiceCharged(String service) throws InterruptedException
	{
		Thread.sleep(2000);
		return serviceCheckfield.getText().contains(service);
	}
	
	
	public void registerPaymentfromVisit(String paymentMethod) throws InterruptedException
	{
	    w = new WebdriverUtility();

	    w.waitTillElementToBeClickable(driver, newInvoiceLnk, 10);
	    Thread.sleep(2000);

	    w.waitTillElementToBeClickable(driver, markAsSentInvoiceBtn, 10);
	    w.moveToElementAndClick(driver, markAsSentInvoiceBtn);

	    w.waitTillElementToBeClickable(driver, recordPaymentBtn, 10);
	    recordPaymentBtn.click();

	    w.waitTillElementToBeClickable(driver, methodSlctrDD, 10);
	    w.selectDropdownByText(methodSlctrDD, paymentMethod);

	    w.waitTillElementToBeClickable(driver, cnfrmPaymentBtn, 10);
	    cnfrmPaymentBtn.click();

	    Thread.sleep(2000);
	}
	
}
