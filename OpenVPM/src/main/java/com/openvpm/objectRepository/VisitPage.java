package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class VisitPage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public VisitPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[.='Check in']")
	private WebElement checkInBtn;
	
	@FindBy(xpath = "//button[.='Start exam']")
	private WebElement startExamBtn;
	
	@FindBy(xpath = "//a[.='Write SOAP note']")
	private WebElement soapNoteBtn;
	
	@FindBy(xpath = "//button[.='Review closeout']")
	private WebElement reviewcloseOutBtn;
	
	@FindBy(xpath = "//button[.='Search services...']")
	private WebElement searchServiceBtn;
	
	@FindBy(xpath = "//input[@aria-label='Search services']")
	private WebElement searchServiceTF;
	
	@FindBy(xpath = "//button[@role='option']")
	private WebElement selectServiceBtn;
	
	@FindBy(xpath = "//button[.='Add']")
	private WebElement addServiceBtn;
	
	@FindBy(xpath = "//button[.='Create visit invoice']")
	private WebElement createVisitInvoiceBtn;
	
	@FindBy(xpath = "//a[.='Open invoice']")
	private WebElement openInvoiceBtn;
	
	
	@FindBy(id = "closeout-no-instructions")
	private WebElement closeoutInstructionTF;
	
	@FindBy(id = "closeout-prescriptions")
	private WebElement prescriptionDD;
	
	@FindBy(id = "closeout-follow-up")
	private WebElement followUpDD;
	
	@FindBy(xpath = "//button[.='Finalize clinical handoff']")
	private WebElement finalizeHandoffBtn;
	
	//appointment options
	@FindBy(xpath="//a[.='Open visit']")
	private WebElement openVisitBtn;
	
	
	public WebElement getOpenVisitBtn() {
		return openVisitBtn;
	}

	
	public WebElement getReviewcloseOutBtn() {
		return reviewcloseOutBtn;
	}

	
	public WebElement getCheckInBtn() {
		return checkInBtn;
	}


	public WebElement getStartExamBtn() {
		return startExamBtn;
	}


	public WebElement getSoapNoteBtn() {
		return soapNoteBtn;
	}

	
	public void checkInAndStartExam(String doctor, String patientName) throws InterruptedException
	{
		w = new WebdriverUtility();
		HomePage h = new HomePage(driver);
		
		w.waitTillElementToBeClickable(driver, h.getScheduleLnk(), 10);
		
		SchedulePage s = new SchedulePage(driver);
		w.selectDropdownByText(s.getSlctDoctorDD(), doctor);
		
		
		for(WebElement e : s.getDayCalVerify())
		{
			if(e.getText().contains(patientName))
			{
				e.click();
				break;
			}
		}
		
		Thread.sleep(2000);
		
		w.waitTillElementToBeClickable(driver, getOpenVisitBtn(), 10);
		w.moveToElementAndClick(driver, getOpenVisitBtn());
	
		
		w.waitTillElementToBeClickable(driver, checkInBtn, 10);
		checkInBtn.click();
		
		w.waitTillElementToBeClickable(driver, startExamBtn, 10);
		startExamBtn.click();
	}

	public void createInvoiceduringVisit(String service)
	{
		w = new WebdriverUtility();
		w.waitTillElementToBeClickable(driver, searchServiceBtn, 10);
		searchServiceBtn.click();
		
		w.waitTillVisibilityOfElement(driver, searchServiceTF, 10);
		searchServiceTF.sendKeys(service);
		
		w.waitTillElementToBeClickable(driver, selectServiceBtn, 10);
		selectServiceBtn.click();
		
		w.waitTillElementToBeClickable(driver, addServiceBtn, 10);
		addServiceBtn.click();
		
		w.waitTillElementToBeClickable(driver, createVisitInvoiceBtn, 10);
		createVisitInvoiceBtn.click();
		
	}
	
	
	public void finalizeHandout(String closeoutInst) 
	{
		w = new WebdriverUtility();
		w.waitTillElementToBeClickable(driver, closeoutInstructionTF, 10);
		closeoutInstructionTF.sendKeys(closeoutInst);
		
		w.waitTillElementToBeClickable(driver, prescriptionDD, 10);
		w.selectDropdownByIndex(prescriptionDD, 2);
		
		w.waitTillElementToBeClickable(driver, followUpDD, 10);
		w.selectDropdownByvalue(followUpDD, "none");
		
		w.waitTillElementToBeClickable(driver, finalizeHandoffBtn, 10);
		finalizeHandoffBtn.click();
	}


	public WebElement getSearchServiceBtn() {
		return searchServiceBtn;
	}


	public WebElement getSearchServiceTF() {
		return searchServiceTF;
	}


	public WebElement getSelectServiceBtn() {
		return selectServiceBtn;
	}


	public WebElement getAddServiceBtn() {
		return addServiceBtn;
	}


	public WebElement getCreateVisitInvoiceBtn() {
		return createVisitInvoiceBtn;
	}


	public WebElement getOpenInvoiceBtn() {
		return openInvoiceBtn;
	}


	public WebElement getCloseoutInstructionTF() {
		return closeoutInstructionTF;
	}


	public WebElement getPrescriptionDD() {
		return prescriptionDD;
	}


	public WebElement getFollowUpDD() {
		return followUpDD;
	}

	@FindBy(xpath="//a[.='Vaccination']")
	private WebElement vaccntnLnk;

	public WebElement getFinalizeHandoffBtn() {
		return finalizeHandoffBtn;
	}


	public WebElement getVaccntnLnk() {
		return vaccntnLnk;
	}
	
	
	
	
}
