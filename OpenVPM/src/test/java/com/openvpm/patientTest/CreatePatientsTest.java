package com.openvpm.patientTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.openvpm.baseTest.BaseClass;
import com.openvpm.genericUtility.ExcelUtility;
import com.openvpm.genericUtility.JavaUtility;
import com.openvpm.genericUtility.WebdriverUtility;
import com.openvpm.objectRepository.CreateClientsPage;
import com.openvpm.objectRepository.CreatePatientsPage;
import com.openvpm.objectRepository.DashboardPage;
import com.openvpm.objectRepository.SchedulePage;

@Listeners(com.openvpm.listenerUtility.ListenerImplementation.class)
public class CreatePatientsTest extends BaseClass {
	
	@Test
	public void createPatientTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		String cFirstName = e.readDataFromExcelFile("Clients", 1, 0);
		String cLastName = e.readDataFromExcelFile("Clients", 1, 1);
		String clientName = cFirstName +" "+ cLastName;
		String patientName = e.readDataFromExcelFile("Patients", 1, 0);
		String species = e.readDataFromExcelFile("Patients", 1, 1);
		String breed = e.readDataFromExcelFile("Patients", 1, 2);
		String sex = e.readDataFromExcelFile("Patients", 1, 3);
		String dob = e.readDataFromExcelFile("Patients", 1, 4);
		String color = e.readDataFromExcelFile("Patients", 1, 5);
		
		
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	
	}

	//passed - optimized
	@Test(groups="system")
	public void createClientPatientAppointmentTest() throws Exception
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
		String doctor = e.readDataFromExcelFile("Schedule", 1, 2);
		String room = e.readDataFromExcelFile("Schedule", 2, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 7, 4);
		String date = j.getReqDate(10);
		
		//create Client
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
		
		//create Patient
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		String actText = sp.getAppointmentCreatedPopup().getText();
		String expctdText = "Appointment created";
		Assert.assertEquals(actText, expctdText);
		
		boolean verify = sp.verifyAppointment(doctor, patientName);
		Assert.assertEquals(verify, true);
	}
}
