package com.openvpm.scheduleTest;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.openvpm.baseTest.BaseClass;
import com.openvpm.genericUtility.ExcelUtility;
import com.openvpm.genericUtility.JavaUtility;
import com.openvpm.genericUtility.ThreadlocalUtility;
import com.openvpm.genericUtility.WebdriverUtility;
import com.openvpm.objectRepository.CreateClientsPage;
import com.openvpm.objectRepository.CreatePatientsPage;
import com.openvpm.objectRepository.DashboardPage;
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
	
	//passed-optimized
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
		String room = e.readDataFromExcelFile("Schedule", 7, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 12, 4);
		
		String date = j.getReqDate(5);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Read Data from Excel");
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Appointment Created");
		
		boolean verify = sp.verifyAppointment(doctor, patientName);
		Assert.assertEquals(verify, true);

	}
	//passed-optimized
	@Test(groups="smoke")
	public void addSOAPNotes() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		int random = j.posRandomNumber();
		
		//read Data from Excel
		//for client
		String firstName = e.readDataFromExcelFile("Clients",3 , 0);
		String lastName = e.readDataFromExcelFile("Clients", 3, 1) + random;
		String email = e.readDataFromExcelFile("Clients", 3, 2);
		String phone = e.readDataFromExcelFile("Clients", 3, 3);
		
		//for patient
		String clientName = firstName +" "+ lastName;
		String patientName = e.readDataFromExcelFile("Patients", 10, 0) +random;
		String species = e.readDataFromExcelFile("Patients", 10, 1);
		String breed = e.readDataFromExcelFile("Patients", 10, 2);
		String sex = e.readDataFromExcelFile("Patients", 10, 3);
		String dob = e.readDataFromExcelFile("Patients", 10, 4);
		String color = e.readDataFromExcelFile("Patients", 10, 5);
		
		//for appointment
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 3, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 2, 2);
		String room = e.readDataFromExcelFile("Schedule", 5, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 15, 4);
		String date = j.getCurrentDate();
		
		//for SOAP notes
		String subjective = "subjectiveDemo4";
		String objective = "objectiveDemo4";
		String assessment = "assessmentDemo4";
		String plan = "planDemo4";
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Read Data from Excel");
		
		//create Client
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Client Created");
		
		//create Patient
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Patient Created");
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Appointment Created");
		
		VisitPage v = new VisitPage(driver);
		v.checkInAndStartExam(doctor, patientName);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Open Visit , Cheked in and Started Exam");
		
		SOAPDocPage spd = new SOAPDocPage(driver);
		spd.addSOAPNotes(subjective, objective, assessment, plan);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Added SOAP Notes");
		
		RecordsPage r = new RecordsPage(driver);
		boolean verify =  r.verifySOAP(patientName, assessment);
		
		Assert.assertEquals(verify, true);
	}

	//passed - optimized
	@Test(groups="integration")
	public void createAppointmentNewPatientTest() throws Exception
	{
		
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		int random = j.posRandomNumber();
		
		//read Data from Excel
		//for client
		String firstName = e.readDataFromExcelFile("Clients", 4, 0);
		String lastName = e.readDataFromExcelFile("Clients", 4, 1) + random;
		String email = e.readDataFromExcelFile("Clients", 4, 2);
		String phone = e.readDataFromExcelFile("Clients", 4, 3);
		
		//for patient
		String clientName = firstName +" "+ lastName;
		String patientName = e.readDataFromExcelFile("Patients", 9, 0) +random;
		String species = e.readDataFromExcelFile("Patients", 9, 1);
		String breed = e.readDataFromExcelFile("Patients", 9, 2);
		String sex = e.readDataFromExcelFile("Patients", 9, 3);
		String dob = e.readDataFromExcelFile("Patients", 9, 4);
		String color = e.readDataFromExcelFile("Patients", 9, 5);
		
		//for appointment
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 2, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 1, 2);
		String room = e.readDataFromExcelFile("Schedule", 6, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 14, 4);
		String date = j.getReqDate(8);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Read Data from Excel");
		
		//create Client
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Client Created");
		
		WebdriverUtility w = new WebdriverUtility();
		HomePage h = new HomePage(driver);
		w.waitTillVisibilityOfElement(driver, h.getScheduleLnk(), 10);
		
		//create Patient
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Patient Created");
		
		HomePage hp = new HomePage(driver);
		w.waitTillVisibilityOfElement(driver, hp.getScheduleLnk(), 10);
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Appointment Created");
		
		String actText = sp.getAppointmentCreatedPopup().getText();
		String expctdText = "Appointment created";
		Assert.assertEquals(actText, expctdText);
	}
	
	//passed-optimized
	@Test(groups="integration")
	public void soapNotesPatientDashboardVerify() throws Exception
	{
		
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		int random = j.posRandomNumber();
		
		//read Data from Excel
		//for client
		String firstName = e.readDataFromExcelFile("Clients", 5, 0);
		String lastName = e.readDataFromExcelFile("Clients", 5, 1) + random;
		String email = e.readDataFromExcelFile("Clients", 5, 2);
		String phone = e.readDataFromExcelFile("Clients", 5, 3);
		
		//for patient
		String clientName = firstName +" "+ lastName;
		String patientName = e.readDataFromExcelFile("Patients", 8, 0) +random;
		String species = e.readDataFromExcelFile("Patients", 8, 1);
		String breed = e.readDataFromExcelFile("Patients", 8, 2);
		String sex = e.readDataFromExcelFile("Patients", 8, 3);
		String dob = e.readDataFromExcelFile("Patients", 8, 4);
		String color = e.readDataFromExcelFile("Patients", 8, 5);
		
		//for appointment
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 2, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 3, 2);
		String room = e.readDataFromExcelFile("Schedule", 7, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 13, 4);
		String date = j.getReqDate(8);
		
		String subjective = "subjectiveDemo6";
		String objective = "objectiveDemo6";
		String assessment = "assessmentDemo6";
		String plan = "planDemo6";
		ThreadlocalUtility.getTest().log(Status.INFO, "read Data From Excel");
		
		//create Client
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Client Created");
		
		WebdriverUtility w = new WebdriverUtility();
		HomePage h = new HomePage(driver);
		w.waitTillVisibilityOfElement(driver, h.getScheduleLnk(), 10);
		
		//create Patient
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Patient Created");
		
		HomePage hp = new HomePage(driver);
		w.waitTillVisibilityOfElement(driver, hp.getScheduleLnk(), 10);
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Appointment Created");
		
		VisitPage v = new VisitPage(driver);
		v.checkInAndStartExam(doctor, patientName);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Open visit and check in , start exam");
		
		SOAPDocPage spd = new SOAPDocPage(driver);
		spd.addSOAPNotes(subjective, objective, assessment, plan);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Added SOAP Notes");
		
		PatientDashboardPage pp = new PatientDashboardPage(driver);
		boolean verify = pp.confirmSOAPInDashboard(patientName, assessment);
		
		Assert.assertEquals(verify, true);
	}
	
	//passed - optimized
	@Test(groups="system")
	public void vaccinationReport() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		int random = j.posRandomNumber();
		
		//read Data from Excel
		//for client
		String firstName = e.readDataFromExcelFile("Clients", 6, 0);
		String lastName = e.readDataFromExcelFile("Clients", 6, 1) + random;
		String email = e.readDataFromExcelFile("Clients", 6, 2);
		String phone = e.readDataFromExcelFile("Clients", 6, 3);
		
		//for patient
		String clientName = firstName +" "+ lastName;
		String patientName = e.readDataFromExcelFile("Patients", 7, 0) +random;
		String species = e.readDataFromExcelFile("Patients", 7, 1);
		String breed = e.readDataFromExcelFile("Patients", 7, 2);
		String sex = e.readDataFromExcelFile("Patients", 7, 3);
		String dob = e.readDataFromExcelFile("Patients", 7, 4);
		String color = e.readDataFromExcelFile("Patients", 7, 5);
		
		//for appointment
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 3, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 2, 2);
		String room = e.readDataFromExcelFile("Schedule", 9, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 14, 4);
		String date = j.getCurrentDate();
		
		//for SOAP notes
		String subjective = "subjectiveDemo8";
		String objective = "objectiveDemo8";
		String assessment = "assessmentDemo8";
		String plan = "planDemo8";
		
		//for vaccination
		String vaccinantion = e.readDataFromExcelFile("Patients", 2, 6);
		String productName = e.readDataFromExcelFile("Patients", 2, 7);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Read Data from Excel");
		
		//create Client
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Client Created");
		
		//create Patient
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Patient Created");

		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "New Appointment Created");
		
		//checkIn and start exam
		VisitPage v = new VisitPage(driver);
		v.checkInAndStartExam(doctor, patientName);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "open visit check in and start exam");
		
		//add Soap notes
		SOAPDocPage spd = new SOAPDocPage(driver);
		spd.addSOAPNotes(subjective, objective, assessment, plan);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Added SOAP Notes");
		
		//add vaccination
		VaccinationAddPage vap = new VaccinationAddPage(driver);
		vap.addVaccination(vaccinantion, productName);
		
		ThreadlocalUtility.getTest().log(Status.INFO, "Added Vaccination");
		
		PatientDashboardPage pp = new PatientDashboardPage(driver);
		boolean verify = pp.confirmVaccinationinDashboard(patientName, vaccinantion);
		
		Assert.assertEquals(verify, true);
	}
	
	
}
