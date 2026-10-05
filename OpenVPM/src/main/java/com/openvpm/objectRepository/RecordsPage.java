package com.openvpm.objectRepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class RecordsPage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public RecordsPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[contains(@placeholder,'Search patients')]")
	private WebElement searchPatientsTF;
	
	@FindBy(xpath = "//button/descendant::span[@class='font-medium']")
	private WebElement slctPatientBtn;
	
	@FindBy(xpath = "//button[.='SOAP Notes']")
	private WebElement soapTab;
	
	@FindBy(xpath = "//button[.='Vaccinations']")
	private WebElement vaccinationsTab;

	@FindBy(xpath = "//button[.='Prescriptions']")
	private WebElement prescriptionsTab;
	
	@FindBy(xpath = "//button[.='Problems']")
	private WebElement problemsTab;
	
	@FindBy(xpath = "//button[.='Lab Results']")
	private WebElement labResultsTab;
	
	@FindBy(xpath = "//button[.='Procedures']")
	private WebElement proceduresTab;
	
	@FindBy(xpath = "//div[contains(@id,'soap-note-')]/descendant::p[contains(@class,'text-sm text-muted')]")
	private List<WebElement> verifySOAPlist;
	
	public boolean verifySOAP(String patientName, String assessment)
	{
		HomePage h = new HomePage(driver);
		h.getRecordsLnk().click();
		
		w = new WebdriverUtility();
		w.waitTillVisibilityOfElement(driver, searchPatientsTF, 10);
		searchPatientsTF.clear();
		searchPatientsTF.sendKeys(patientName);
		slctPatientBtn.click();
		
		w.waitTillVisibilityOfElement(driver, soapTab, 10);
		
		for(WebElement e : verifySOAPlist)
		{
			if(e.getText().contains(assessment))
				return true;
			
		}
		
		return false;
	}
	
	
}
