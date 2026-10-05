package com.openvpm.objectRepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class PatientDashboardPage {
	
	public WebDriver driver;
	
	public PatientDashboardPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "patient-tab-records")
	private WebElement medicalRecordsTab;
	
	@FindBy(xpath = "//div[contains(@id,'soap-note')]/dl/descendant::dt[text()='Assessment']/../dd")
	private List<WebElement> soapCnfrmList;
	
	@FindBy(id = "patient-tab-vaccinations")
	private WebElement vaccinationsTab;
	
	@FindBy(xpath = "//table/tbody/tr/td[contains(@class,'font-medium')]")
	private List<WebElement> vaccnCnfrmList;
	
	public boolean confirmSOAPInDashboard(String patientName, String assessment)
	{
		HomePage h = new HomePage(driver);
		h.getPatientsLnk().click();
		
		PatientPage p = new PatientPage(driver);
		WebdriverUtility w = new WebdriverUtility();
		w.waitTillVisibilityOfElement(driver, p.getSearchPatient(), 10);
		p.getSearchPatient().sendKeys(patientName);
		
		w.waitTillVisibilityOfElement(driver, p.getSelectPatientBtn(), 10);
		p.getSelectPatientBtn().click();
		
		w.waitTillElementToBeClickable(driver, medicalRecordsTab, 10);
		medicalRecordsTab.click();
		
		w.waitTillVisibilityOfElement(driver, soapCnfrmList.get(0), 10);
		
		for(WebElement e : soapCnfrmList)
		{
			if(e.getText().contains(assessment))
				return true;
		}
		return false;
	}
	
	public boolean confirmVaccinationinDashboard(String patientName, String vaccName)
	{
		HomePage h = new HomePage(driver);
		h.getPatientsLnk().click();
		
		PatientPage p = new PatientPage(driver);
		WebdriverUtility w = new WebdriverUtility();
		w.waitTillVisibilityOfElement(driver, p.getSearchPatient(), 10);
		p.getSearchPatient().sendKeys(patientName);
		
		w.waitTillVisibilityOfElement(driver, p.getSelectPatientBtn(), 10);
		p.getSelectPatientBtn().click();
		
		w.waitTillElementToBeClickable(driver, vaccinationsTab, 10);
		vaccinationsTab.click();
		
		w.waitTillVisibilityOfElement(driver, vaccnCnfrmList.get(0), 10);
		
		for(WebElement e : vaccnCnfrmList)
		{
			if(e.getText().contains(vaccName))
				return true;
		}
		return false;
	}
}
