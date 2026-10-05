package com.openvpm.objectRepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.openvpm.genericUtility.WebdriverUtility;

public class SchedulePage {
	
	public WebDriver driver;
	public WebdriverUtility w;
	
	public SchedulePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[text()='New Appointment']")
	private WebElement newAppointmentBtn;
	
	@FindBy(id = "new-appointment-location")
	private WebElement locationSelectDD;
	
	@FindBy(xpath = "//input[@placeholder='Search patients or owners...']")
	private WebElement patientSelectTF;
	
	@FindBy(xpath = "//input[contains(@placeholder,'Search patients')]/../div/button")
	private WebElement patientNameBtn;
	
	@FindBy(xpath = "//label[contains(text(),'Appointment Type')]/../select")
	private WebElement appntmntTypDD;

	@FindBy(xpath = "//label[contains(text(),'Doctor')]/../select")
	private WebElement dctrSlctDD;
	
	@FindBy(xpath = "//label[contains(text(),'Room')]/../select")
	private WebElement roomSlctDD;
	
	@FindBy(xpath = "//label[contains(text(),'Date')]/../input")
	private WebElement dateInputTF;
	
	@FindBy(xpath = "//label[contains(text(),'Start Time')]/../select")
	private WebElement timeDD;
	
	@FindBy(xpath = "//label[contains(text(),'Duration (minutes)')]/../input")
	private WebElement durationTF;
	
	@FindBy(xpath = "//textarea")
	private WebElement notesTA;
	
	@FindBy(xpath = "//button[text()='Save']")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement cancelBtn;
	
	@FindBy(xpath = "//div[@data-tour='schedule-calendar']/descendant::div[@class='overflow-x-auto']/descendant::button/descendant::span[@class='truncate']")
	private List<WebElement> dayCalVerify;
	
	@FindBy(xpath = "//div[@data-tour='schedule-calendar']/descendant::div[@class='grid grid-cols-7']/descendant::button/descendant::span[contains(@class,'truncate')]")
	private List<WebElement> mnthlyCalVerify;
	
	@FindBy(xpath = "//li/descendant::div[contains(text(),'Appointment created')]")
	private WebElement appointmentCreatedPopup;
	
	@FindBy(xpath="//button[text()='Day']")
	private WebElement dailyCalBtn;
	
	@FindBy(xpath="//button[text()='Week']")
	private WebElement weeklyCalBtn;
	
	@FindBy(xpath="//button[text()='Month']")
	private WebElement monthlyCalBtn;
	
	@FindBy(xpath="//select[@class]")
	private WebElement slctDoctorfilterDD;
	


	public WebElement getDailyCalBtn() {
		return dailyCalBtn;
	}


	public WebElement getWeeklyCalBtn() {
		return weeklyCalBtn;
	}


	public WebElement getMonthlyCalBtn() {
		return monthlyCalBtn;
	}


	public WebElement getSlctDoctorDD() {
		return slctDoctorfilterDD;
	}


	public WebElement getNewAppointmentBtn() {
		return newAppointmentBtn;
	}


	public WebElement getLocationSelectDD() {
		return locationSelectDD;
	}


	public WebElement getPatientSelectTF() {
		return patientSelectTF;
	}


	public WebElement getPatientNameBtn() {
		return patientNameBtn;
	}


	public WebElement getAppntmntTypDD() {
		return appntmntTypDD;
	}


	public WebElement getDctrSlctDD() {
		return dctrSlctDD;
	}


	public WebElement getRoomSlctDD() {
		return roomSlctDD;
	}


	public WebElement getDateInputTF() {
		return dateInputTF;
	}


	public WebElement getTimeDD() {
		return timeDD;
	}


	public WebElement getDurationTF() {
		return durationTF;
	}


	public WebElement getNotesTA() {
		return notesTA;
	}


	public WebElement getSaveBtn() {
		return saveBtn;
	}


	public WebElement getCancelBtn() {
		return cancelBtn;
	}


	public List<WebElement> getDayCalVerify() {
		return dayCalVerify;
	}


	public List<WebElement>  getMnthlyCalVerify() {
		return mnthlyCalVerify;
	}


	public WebElement getAppointmentCreatedPopup() {
		return appointmentCreatedPopup;
	}


	//create appointment
	public void newAppointment(String location, String patientName, String appointmentType, String doctor, String room, String date, String time)
	{
		HomePage h = new HomePage(driver);
		h.getScheduleLnk().click();
		
		w = new WebdriverUtility();
		w.waitTillElementToBeClickable(driver, newAppointmentBtn, 10);
		
		newAppointmentBtn.click();
		
		w.selectDropdownByText(locationSelectDD, location);
		
		patientSelectTF.clear();
		patientSelectTF.sendKeys(patientName);
		
		patientNameBtn.click();
		
		w.selectDropdownByText(appntmntTypDD, appointmentType);
		
		w.selectDropdownByText(dctrSlctDD, doctor);
		
		w.selectDropdownByText(roomSlctDD, room);
		
		dateInputTF.clear();
		dateInputTF.sendKeys(date);
		
		w.selectDropdownByvalue(timeDD, time);
		
		saveBtn.click();	
		
	}
	
	
	public boolean verifyAppointment(String doctor, String patientName) {
		
		w = new WebdriverUtility();
		w.waitTillElementToBeClickable(driver, monthlyCalBtn, 15);
		w.moveToElementAndClick(driver, monthlyCalBtn);		
		
		w = new WebdriverUtility();
		w.selectDropdownByText(slctDoctorfilterDD, doctor);

		for(WebElement e : mnthlyCalVerify)
		{
			if(e.getText().equals(patientName))
				return true;
		}
		
		return false;
	}
	
	
	
}
