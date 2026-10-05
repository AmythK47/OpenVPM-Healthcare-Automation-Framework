package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PatientPage {
	public WebDriver driver;
	
	public PatientPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[.='New Patient']")
	private WebElement newPatientBtn;
	
	@FindBy(xpath = "//input[@placeholder='Search patients or owners...']")
	private WebElement searchPatient;
	
	@FindBy(xpath = "//table/tbody/tr/td[last() - 4]")
	private WebElement selectPatientBtn;

	public WebElement getNewPatientBtn() {
		return newPatientBtn;
	}

	public WebElement getSearchPatient() {
		return searchPatient;
	}

	public WebElement getSelectPatientBtn() {
		return selectPatientBtn;
	}



}
