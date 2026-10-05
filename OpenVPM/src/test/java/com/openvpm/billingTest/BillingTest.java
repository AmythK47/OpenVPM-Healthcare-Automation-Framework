package com.openvpm.billingTest;

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
import com.openvpm.objectRepository.BillingPage;
import com.openvpm.objectRepository.CreateClientsPage;
import com.openvpm.objectRepository.CreatePatientsPage;
import com.openvpm.objectRepository.DashboardPage;
import com.openvpm.objectRepository.HomePage;
import com.openvpm.objectRepository.NewInvoicePage;
import com.openvpm.objectRepository.SOAPDocPage;
import com.openvpm.objectRepository.SchedulePage;
import com.openvpm.objectRepository.VisitPage;


@Listeners(com.openvpm.listenerUtility.ListenerImplementation.class)
public class BillingTest extends BaseClass {

	
	@Test
	public void newInvoiceTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read data from excel
		String firstName = e.readDataFromExcelFile("Clients", 2, 0) ;
		String lastName = e.readDataFromExcelFile("Clients", 2, 1) + j.posRandomNumber();
		String email = e.readDataFromExcelFile("Clients", 2, 2);
		String phone = e.readDataFromExcelFile("Clients", 2, 3);
		String clientName = firstName +" "+ lastName;
		String serviceName = e.readDataFromExcelFile("Billing", 1, 0);
		
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
		
		NewInvoicePage np = new NewInvoicePage(driver);
		np.newInvoice(clientName, serviceName, serviceName);
	}
	
	//passed - optimized
	@Test(groups="smoke")
	public void registerPaymentTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		String firstName = e.readDataFromExcelFile("Clients", 2, 0) ;
		String lastName = e.readDataFromExcelFile("Clients", 2, 1) + j.posRandomNumber();
		String email = e.readDataFromExcelFile("Clients", 2, 2);
		String phone = e.readDataFromExcelFile("Clients", 2, 3);
		String clientName = firstName +" "+ lastName;
		String serviceName = e.readDataFromExcelFile("Billing", 1, 0);
		String paymentMethod = e.readDataFromExcelFile("Billing", 1, 1);
		
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);

		NewInvoicePage np = new NewInvoicePage(driver);
		np.newInvoice(clientName, serviceName, serviceName);
		
		BillingPage bp = new BillingPage(driver);
		bp.registerPayment(clientName, paymentMethod);
		
		boolean verify = bp.verifyPayment();
		Assert.assertEquals(verify, true);
	}
	
	//passed - optimized
	@Test(groups="integration")
	public void createInvoiceDuringVisitTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		int random = j.posRandomNumber();
		
		//read Data from Excel
		//for client
		String firstName = e.readDataFromExcelFile("Clients",1 , 0);
		String lastName = e.readDataFromExcelFile("Clients", 1, 1) + random;
		String email = e.readDataFromExcelFile("Clients", 1, 2);
		String phone = e.readDataFromExcelFile("Clients", 1, 3);
		
		//for patient
		String clientName = firstName +" "+ lastName;
		String patientName = e.readDataFromExcelFile("Patients", 12, 0) +random;
		String species = e.readDataFromExcelFile("Patients", 12, 1);
		String breed = e.readDataFromExcelFile("Patients", 12, 2);
		String sex = e.readDataFromExcelFile("Patients", 12, 3);
		String dob = e.readDataFromExcelFile("Patients", 12, 4);
		String color = e.readDataFromExcelFile("Patients", 12, 5);
		
		//for appointment
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 2, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 2, 2);
		String room = e.readDataFromExcelFile("Schedule", 1, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 1, 4);
		String date = j.getCurrentDate();
		
		//for SOAP notes
		String subjective = "subjectiveDemo1";
		String objective = "objectiveDemo1";
		String assessment = "assessmentDemo1";
		String plan = "planDemo1";
		
		//create Client
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
		
		//create Patient
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		VisitPage v = new VisitPage(driver);
		v.checkInAndStartExam(doctor, patientName);
		
		SOAPDocPage spd = new SOAPDocPage(driver);
		spd.addSOAPNotes(subjective, objective, assessment, plan);
		
		String serviceName = e.readDataFromExcelFile("Billing", 1, 0);
		v.createInvoiceduringVisit(serviceName);
		
		WebdriverUtility w = new WebdriverUtility();
		w.waitTillVisibilityOfElement(driver, v.getOpenInvoiceBtn(), 10);
		v.getOpenInvoiceBtn().click();
		
		BillingPage b = new BillingPage(driver);
		boolean verify = b.verifyServiceCharged(serviceName);
		Assert.assertEquals(verify, true);
	}
	
	//passed - optimized
	@Test(groups="system")
	public void verifyInvoicePaidinDashboardTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		int random = j.posRandomNumber();
		
		//read Data from Excel
		//for client
		String firstName = e.readDataFromExcelFile("Clients",2 , 0);
		String lastName = e.readDataFromExcelFile("Clients", 2, 1) + random;
		String email = e.readDataFromExcelFile("Clients", 2, 2);
		String phone = e.readDataFromExcelFile("Clients", 2, 3);
		
		//for patient
		String clientName = firstName +" "+ lastName;
		String patientName = e.readDataFromExcelFile("Patients", 11, 0) +random;
		String species = e.readDataFromExcelFile("Patients", 11, 1);
		String breed = e.readDataFromExcelFile("Patients", 11, 2);
		String sex = e.readDataFromExcelFile("Patients", 11, 3);
		String dob = e.readDataFromExcelFile("Patients", 11, 4);
		String color = e.readDataFromExcelFile("Patients", 11, 5);
		
		//for appointment
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 3, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 3, 2);
		String room = e.readDataFromExcelFile("Schedule", 3, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 8, 4);
		String date = j.getCurrentDate();
		String paymentMethod = e.readDataFromExcelFile("Billing", 1, 1);
		String serviceName = e.readDataFromExcelFile("Billing", 1, 0);
		
		//for SOAP notes
		String subjective = "subjectiveDemo2";
		String objective = "objectiveDemo2";
		String assessment = "assessmentDemo2";
		String plan = "planDemo2";
		
		//record pre revenue
		DashboardPage d = new DashboardPage(driver);
		WebdriverUtility w = new WebdriverUtility();
		String preRevenue = d.recordRevenue();
		System.out.println("pre - " + preRevenue);
		
		//create Client
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
		
		//create Patient
		CreatePatientsPage cp = new CreatePatientsPage(driver);
		cp.createPatient(clientName, patientName, species, breed, sex, dob, color);	
		
		//create Appointment
		SchedulePage sp = new SchedulePage(driver);
		sp.newAppointment(location, patientName, appointmentType, doctor, room, date, startTime);
		
		
		VisitPage v = new VisitPage(driver);
		v.checkInAndStartExam(doctor, patientName);
		
		SOAPDocPage spd = new SOAPDocPage(driver);
		spd.addSOAPNotes(subjective, objective, assessment, plan);
		
		v.createInvoiceduringVisit(serviceName);
		
		v.finalizeHandout("None Required");
		
		w.waitTillVisibilityOfElement(driver, v.getOpenInvoiceBtn(), 10);
		v.getOpenInvoiceBtn().click();
		
		BillingPage b = new BillingPage(driver);
		b.registerPaymentfromVisit(paymentMethod);
		
		DashboardPage dp = new DashboardPage(driver);
		String postRevenue = dp.recordRevenue();
		System.out.println("post - " + postRevenue);
		Assert.assertNotEquals(preRevenue, postRevenue);

	}
	
	//dummy for above
	@Test
	public void registerPaymentAndVerifyDashoardTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read data from excel
		String firstName = e.readDataFromExcelFile("Clients", 2, 0) ;
		String lastName = e.readDataFromExcelFile("Clients", 2, 1) + j.posRandomNumber();
		String email = e.readDataFromExcelFile("Clients", 2, 2);
		String phone = e.readDataFromExcelFile("Clients", 2, 3);
		String clientName = firstName +" "+ lastName;
		String serviceName = e.readDataFromExcelFile("Billing", 1, 0);
		String paymentMethod = e.readDataFromExcelFile("Billing", 1, 1);
		
		DashboardPage d = new DashboardPage(driver);
		WebdriverUtility w = new WebdriverUtility();
		String preRevenue = d.recordRevenue();
		System.out.println("pre - " + preRevenue);
		
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);

		NewInvoicePage np = new NewInvoicePage(driver);
		np.newInvoice(clientName, serviceName, serviceName);
		
		BillingPage bp = new BillingPage(driver);
		bp.registerPayment(clientName, paymentMethod);
		
		boolean verify = bp.verifyPayment();
		Assert.assertEquals(verify, true);
		
		DashboardPage dp = new DashboardPage(driver);
		String postRevenue = dp.recordRevenue();
		System.out.println("pre - " + postRevenue);
		Assert.assertNotEquals(preRevenue, postRevenue);
	}
	
}
