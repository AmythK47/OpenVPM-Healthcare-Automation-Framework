package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class HomePage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public HomePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[contains(.,'New')]")
	private WebElement newBtn;
	
	@FindBy(xpath = "//a[contains(.,'Dashboard')]")
	private WebElement dashboardLnk;
	
	@FindBy(xpath = "//a[contains(.,'Patients')]")
	private WebElement patientsLnk;
	
	@FindBy(xpath = "//a[contains(.,'Clients')]")
	private WebElement clientsLnk;
	
	@FindBy(xpath = "//a[contains(.,'Schedule')]")
	private WebElement scheduleLnk;
	
	@FindBy(xpath = "//a[contains(.,'Records')]")
	private WebElement recordsLnk;
	
	@FindBy(xpath = "//a[contains(.,'Lab Inbox')]")
	private WebElement labInboxLnk;
	
	@FindBy(xpath = "//a[contains(.,'Billing')]")
	private WebElement billingLnk;
	
	@FindBy(xpath = "//a[contains(.,'Inventory')]")
	private WebElement inventoryLnk;
	
	@FindBy(xpath = "//a/span[text()='Inbox']")
	private WebElement inboxLnk;
	
	@FindBy(xpath = "//a[contains(.,'Recalls')]")
	private WebElement recallsLnk;
	
	@FindBy(xpath = "//a[contains(.,'Care Reminders')]")
	private WebElement careRemindersLnk;
	
	@FindBy(xpath = "//a[contains(.,'Imported History')]")
	private WebElement importedHistoryLnk;
	
	@FindBy(xpath = "//a[contains(.,'Whiteboard')]")
	private WebElement whiteboardLnk;
	
	@FindBy(xpath = "//a[contains(.,'Controlled Substances')]")
	private WebElement cntrldSubstnceLnk;
	
	@FindBy(xpath = "//a[contains(.,'Reports')]")
	private WebElement reportsLnk;
	
	@FindBy(xpath = "//a[contains(.,'Settings')]")
	private WebElement settingsLnk;
	
	@FindBy(xpath = "//button[contains(@aria-label,'Sign out')]")
	private WebElement signOutBtn;
	
	public void signOut()
	{
		w = new WebdriverUtility();
		w.moveToElementAndClick(driver, signOutBtn);
	}


	public WebElement getNewBtn() {
		return newBtn;
	}

	public WebElement getDashboardLnk() {
		return dashboardLnk;
	}

	public WebElement getPatientsLnk() {
		return patientsLnk;
	}

	public WebElement getClientsLnk() {
		return clientsLnk;
	}

	public WebElement getScheduleLnk() {
		return scheduleLnk;
	}

	public WebElement getRecordsLnk() {
		return recordsLnk;
	}

	public WebElement getLabInboxLnk() {
		return labInboxLnk;
	}

	public WebElement getBillingLnk() {
		return billingLnk;
	}

	public WebElement getInventoryLnk() {
		return inventoryLnk;
	}

	public WebElement getInboxLnk() {
		return inboxLnk;
	}

	public WebElement getRecallsLnk() {
		return recallsLnk;
	}

	public WebElement getCareRemindersLnk() {
		return careRemindersLnk;
	}

	public WebElement getImportedHistoryLnk() {
		return importedHistoryLnk;
	}

	public WebElement getWhiteboardLnk() {
		return whiteboardLnk;
	}

	public WebElement getCntrldSubstnceLnk() {
		return cntrldSubstnceLnk;
	}

	public WebElement getReportsLnk() {
		return reportsLnk;
	}

	public WebElement getSettingsLnk() {
		return settingsLnk;
	}

	public WebElement getSignOutBtn() {
		return signOutBtn;
	}

}
