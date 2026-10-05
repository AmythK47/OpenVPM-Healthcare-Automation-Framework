package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class CreatePatientsPage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public CreatePatientsPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@placeholder='Search clients by name or email...']")
	private WebElement clientSearchTF;

	@FindBy(id = "name")
	private WebElement patientNameTF;
	
	@FindBy(id = "species")
	private WebElement speciesDD;
	
	@FindBy(id = "breed")
	private WebElement breedTF;
	
	@FindBy(id = "sex")
	private WebElement sexDD;
	
	@FindBy(id = "color")
	private WebElement colorTF;
	
	@FindBy(id = "dob")
	private WebElement dateOfBirthTF;
	
	@FindBy(id = "microchipNumber")
	private WebElement microchipTF;
	
	@FindBy(xpath = "//button[contains(.,'Create Patient')]")
	private WebElement createPatientBtn;
	
	@FindBy(xpath = "//button[contains(.,'Cancel')]")
	private WebElement cancelBtn;
	
	@FindBy(xpath = "//button[@type='button']/span[@class='font-medium']")
	private WebElement selectClientBtn;

	public WebElement getClientSearchTF() {
		return clientSearchTF;
	}

	public WebElement getPatientNameTF() {
		return patientNameTF;
	}

	public WebElement getSpeciesDD() {
		return speciesDD;
	}

	public WebElement getBreedTF() {
		return breedTF;
	}

	public WebElement getSexDD() {
		return sexDD;
	}

	public WebElement getColorTF() {
		return colorTF;
	}

	public WebElement getDateOfBirthTF() {
		return dateOfBirthTF;
	}

	public WebElement getMicrochipTF() {
		return microchipTF;
	}

	public WebElement getCreatePatientBtn() {
		return createPatientBtn;
	}

	public WebElement getCancelBtn() {
		return cancelBtn;
	}
	
	@FindBy(xpath="//h3[text()='Basic Information']")
	private WebElement verifyCreation;
	
	public void createPatient(String clientName, String patientName, String species, String breed, String sex, String dob, String color)
	{
		HomePage h = new HomePage(driver);
		h.getPatientsLnk().click();
		
		PatientPage p = new PatientPage(driver);
		p.getNewPatientBtn().click();
		
		clientSearchTF.clear();
		clientSearchTF.sendKeys(clientName);
		
		selectClientBtn.click();
		
		patientNameTF.clear();
		patientNameTF.sendKeys(patientName);
		
		w = new WebdriverUtility();
		w.selectDropdownByvalue(speciesDD, species);
		
		breedTF.clear();
		breedTF.sendKeys(breed);
		
		w.selectDropdownByvalue(sexDD, sex);
		
		dateOfBirthTF.clear();
		dateOfBirthTF.sendKeys(dob);
		
		colorTF.clear();
		colorTF.sendKeys(color);
		
		w.waitTillElementToBeClickable(driver, createPatientBtn, 10);
		createPatientBtn.click();
		
		w.waitTillVisibilityOfElement(driver, verifyCreation, 10);
		
	}
	
	

}
