package com.openvpm.objectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class VaccinationAddPage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public VaccinationAddPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name = "vaccineName")
	private WebElement vaccineNameTF;
	
	@FindBy(name = "productName")
	private WebElement productNameTF;
	
	@FindBy(name = "nextDueDate")
	private WebElement nxtDueDateTF;
	
	@FindBy(name = "lotNumber")
	private WebElement lotNoTF;
	
	@FindBy(name = "manufacturer")
	private WebElement manufacturerNameTF;
	
	@FindBy(xpath = "//button[text()='Save']")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement cancelBtn;
	
	@FindBy(xpath = "//tbody/tr/td[@class='px-4 py-3 font-medium']")
	private WebElement verifyVaccination;

	@FindBy(xpath = "//a[text()='Back to visit']")
	private WebElement backToVisitBtn;
	
	
	
	public void addVaccination(String vaccineName, String productName)
	{
		VisitPage v = new VisitPage(driver);
		w = new WebdriverUtility();
		w.waitTillVisibilityOfElement(driver, v.getVaccntnLnk(), 10);
		v.getVaccntnLnk().click();
		
		w.waitTillVisibilityOfElement(driver, vaccineNameTF, 10);
		vaccineNameTF.sendKeys(vaccineName);
		
		w.waitTillElementToBeClickable(driver, productNameTF, 10);
		productNameTF.sendKeys(productName);
		
		w.waitTillElementToBeClickable(driver, saveBtn, 10);
		saveBtn.click();
		
		w.waitTillVisibilityOfElement(driver, verifyVaccination, 10);
	
	}



	public WebElement getVaccineNameTF() {
		return vaccineNameTF;
	}



	public WebElement getProductNameTF() {
		return productNameTF;
	}



	public WebElement getNxtDueDateTF() {
		return nxtDueDateTF;
	}



	public WebElement getLotNoTF() {
		return lotNoTF;
	}



	public WebElement getManufacturerNameTF() {
		return manufacturerNameTF;
	}



	public WebElement getSaveBtn() {
		return saveBtn;
	}



	public WebElement getCancelBtn() {
		return cancelBtn;
	}



	public WebElement getVerifyVaccination() {
		return verifyVaccination;
	}



	public WebElement getBackToVisitBtn() {
		return backToVisitBtn;
	}

}
