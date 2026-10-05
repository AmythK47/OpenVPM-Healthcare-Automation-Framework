package com.openvpm.scheduleTest;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.openvpm.baseTest.BaseClass;
import com.openvpm.genericUtility.ExcelUtility;
import com.openvpm.genericUtility.JavaUtility;
import com.openvpm.genericUtility.WebdriverUtility;
import com.openvpm.objectRepository.CreateClientsPage;
import com.openvpm.objectRepository.CreatePatientsPage;
import com.openvpm.objectRepository.HomePage;
import com.openvpm.objectRepository.PatientDashboardPage;
import com.openvpm.objectRepository.RecordsPage;
import com.openvpm.objectRepository.SOAPDocPage;
import com.openvpm.objectRepository.SchedulePage;
import com.openvpm.objectRepository.VaccinationAddPage;
import com.openvpm.objectRepository.VisitPage;

@Listeners(com.openvpm.listenerUtility.ListenerImplementation.class)
public class ScheduleTest extends BaseClass {
	
	@Test
	public void createAppointmentTest() throws Exception
	{
		
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String patientName = e.readDataFromExcelFile("Patients", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 1, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 1, 2);
		String room = e.readDataFromExcelFile("Schedule", 1, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 1, 4);
		
		//String date = j.getCurrentDate();
		String date = j.getReqDate(7);
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		String actText = sp.getAppointmentCreatedPopup().getText();
		String expctdText = "Appointment created";
		Assert.assertEquals(actText, expctdText);
	}
	
	
	@Test (groups = "smoke")
	public void appointmentVerificationTest() throws Exception
	{
		
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String patientName = e.readDataFromExcelFile("Patients", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 2, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 1, 2);
		String room = e.readDataFromExcelFile("Schedule", 3, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 4, 4);
		
		//String date = j.getCurrentDate();
		String date = j.getReqDate(7);
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		boolean verify = sp.verifyAppointment(doctor, patientName);
		
		Assert.assertEquals(verify, true);

	}
	
	@Test(groups="smoke")
	public void addSOAPNotes() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String patientName = e.readDataFromExcelFile("Patients", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 1, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 3, 2);
		String room = e.readDataFromExcelFile("Schedule", 1, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 3, 4);
		String date = j.getCurrentDate();
		
		String subjective = "subjectiveDemo";
		String objective = "objectiveDemo";
		String assessment = "assessmentDemo";
		String plan = "planDemo";
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		
		VisitPage v = new VisitPage(driver);
		v.checkInAndStartExam(doctor, patientName);
		
		SOAPDocPage spd = new SOAPDocPage(driver);
		spd.addSOAPNotes(subjective, objective, assessment, plan);
		
		RecordsPage r = new RecordsPage(driver);
		boolean verify =  r.verifySOAP(patientName, assessment);
		
		Assert.assertEquals(verify, true);
	}

	@Test(groups="integration")
	public void createAppointmentNewPatientTest() throws Exception
	{
		
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		String cFirstName = e.readDataFromExcelFile("Clients", 1, 0);
		String cLastName = e.readDataFromExcelFile("Clients", 1, 1);
		String clientName = cFirstName +" "+ cLastName;
		String patientName = e.readDataFromExcelFile("Patients", 1, 0) + j.posRandomNumber();
		String species = e.readDataFromExcelFile("Patients", 1, 1);
		String breed = e.readDataFromExcelFile("Patients", 1, 2);
		String sex = e.readDataFromExcelFile("Patients", 1, 3);
		String dob = e.readDataFromExcelFile("Patients", 1, 4);
		String color = e.readDataFromExcelFile("Patients", 1, 5);
				
		//create Patient
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	
		
		WebdriverUtility w = new WebdriverUtility();
		HomePage h = new HomePage(driver);
		w.waitTillVisibilityOfElement(driver, h.getScheduleLnk(), 10);
		h.getScheduleLnk().click();
		
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 1, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 1, 2);
		String room = e.readDataFromExcelFile("Schedule", 1, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 1, 4);
		
		//String date = j.getCurrentDate();
		String date = j.getReqDate(7);
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		String actText = sp.getAppointmentCreatedPopup().getText();
		String expctdText = "Appointment created";
		Assert.assertEquals(actText, expctdText);
	}
	
	@Test(groups="integration")
	public void soapNotesPatientDashboardVerify() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String patientName = e.readDataFromExcelFile("Patients", 3, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 1, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 2, 2);
		String room = e.readDataFromExcelFile("Schedule", 1, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 2, 4);
		String date = j.getCurrentDate();
		
		String subjective = "subjectiveDemo";
		String objective = "objectiveDemo";
		String assessment = "assessmentDemo";
		String plan = "planDemo";
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		VisitPage v = new VisitPage(driver);
		v.checkInAndStartExam(doctor, patientName);
		
		SOAPDocPage spd = new SOAPDocPage(driver);
		spd.addSOAPNotes(subjective, objective, assessment, plan);
		
		
		
		PatientDashboardPage pp = new PatientDashboardPage(driver);
		boolean verify = pp.confirmSOAPInDashboard(patientName, assessment);
		
		Assert.assertEquals(verify, true);
	}
	
	@Test(groups="system")
	public void vaccinationReport() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		int random = j.posRandomNumber();
		
		//read Data from Excel
		//for client
		String firstName = e.readDataFromExcelFile("Clients",4 , 0);
		String lastName = e.readDataFromExcelFile("Clients", 4, 1) + random;
		String email = e.readDataFromExcelFile("Clients", 4, 2);
		String phone = e.readDataFromExcelFile("Clients", 4, 3);
		
		//for patient
		String clientName = firstName +" "+ lastName;
		String patientName = e.readDataFromExcelFile("Patients", 6, 0) +random;
		String species = e.readDataFromExcelFile("Patients", 6, 1);
		String breed = e.readDataFromExcelFile("Patients", 6, 2);
		String sex = e.readDataFromExcelFile("Patients", 6, 3);
		String dob = e.readDataFromExcelFile("Patients", 6, 4);
		String color = e.readDataFromExcelFile("Patients", 6, 5);
		
		//for appointment
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 3, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 3, 2);
		String room = e.readDataFromExcelFile("Schedule", 3, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 9, 4);
		String date = j.getCurrentDate();
		
		//for SOAP notes
		String subjective = "subjectiveDemo";
		String objective = "objectiveDemo";
		String assessment = "assessmentDemo";
		String plan = "planDemo";
		
		//for vaccination
		String vaccinantion = e.readDataFromExcelFile("Patients", 2, 6);
		String productName = e.readDataFromExcelFile("Patients", 2, 7);
		
		//create Client
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
		
		//create Patient
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	

		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		//checkIn and start exam
		VisitPage v = new VisitPage(driver);
		v.checkInAndStartExam(doctor, patientName);
		
		//add Soap notes
		SOAPDocPage spd = new SOAPDocPage(driver);
		spd.addSOAPNotes(subjective, objective, assessment, plan);
		
		//add vaccination
		VaccinationAddPage vap = new VaccinationAddPage(driver);
		vap.addVaccination(vaccinantion, productName);
		
		PatientDashboardPage pp = new PatientDashboardPage(driver);
		boolean verify = pp.confirmVaccinationinDashboard(patientName, vaccinantion);
		
		Assert.assertEquals(verify, true);
	}
	
	
}
