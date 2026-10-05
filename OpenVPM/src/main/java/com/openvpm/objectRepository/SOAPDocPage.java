package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class SOAPDocPage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public SOAPDocPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//label[text()='Subjective']/../descendant::p[@class='mb-2']")
	private WebElement subjectiveTxtArea;
	
	@FindBy(xpath = "//label[text()='Objective']/../descendant::p[@class='mb-2']")
	private WebElement objectiveTxtArea;
	
	@FindBy(xpath = "//label[text()='Assessment']/../descendant::p[@class='mb-2']")
	private WebElement assessmentTxtArea;

	@FindBy(xpath = "//label[text()='Plan']/../descendant::p[@class='mb-2']")
	private WebElement planTxtArea;
	
	@FindBy(xpath = "//button[.='Finalize SOAP note']")
	private WebElement finalizeNoteBtn;
	
	@FindBy(xpath = "//button[.='Cancel']")
	private WebElement cancelBtn;
	
	@FindBy(xpath = "//a[.='Write SOAP note']")
	private WebElement soapNoteBtn;
	
	
	//create SOAP note
	public void addSOAPNotes(String subjective, String objective, String assessment, String plan) {
		VisitPage v = new VisitPage(driver);
		v.getSoapNoteBtn().click();
		w = new WebdriverUtility();
		w.waitTillElementToBeClickable(driver, subjectiveTxtArea, 10);
		
		subjectiveTxtArea.clear();
		subjectiveTxtArea.sendKeys(subjective);
		
		objectiveTxtArea.clear();
		objectiveTxtArea.sendKeys(objective);
		
		assessmentTxtArea.clear();
		assessmentTxtArea.sendKeys(assessment);
		
		planTxtArea.clear();
		planTxtArea.sendKeys(plan);
		
		w.waitTillElementToBeClickable(driver, finalizeNoteBtn, 10);
		finalizeNoteBtn.click();
		
		w.acceptAlert(driver);
		
		w.waitTillVisibilityOfElement(driver, v.getReviewcloseOutBtn(), 10);
		
	}
	
}
