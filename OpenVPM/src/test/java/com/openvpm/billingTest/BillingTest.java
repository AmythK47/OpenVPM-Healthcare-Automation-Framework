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
	
	
	@Test(groups="smoke")
	public void registerPaymentTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		//read data from excel
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
	
	@Test(groups="integration")
	public void createInvoiceDuringVisitTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String patientName = e.readDataFromExcelFile("Patients", 4, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 1, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 1, 2);
		String room = e.readDataFromExcelFile("Schedule", 2, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 9, 4);
		String date = j.getCurrentDate();
		String serviceName = e.readDataFromExcelFile("Billing", 1, 0);
		
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
		
		v.createInvoiceduringVisit(serviceName);
		
		WebdriverUtility w = new WebdriverUtility();
		w.waitTillVisibilityOfElement(driver, v.getOpenInvoiceBtn(), 10);
		v.getOpenInvoiceBtn().click();
		
		BillingPage b = new BillingPage(driver);
		boolean verify = b.verifyServiceCharged(serviceName);
		Assert.assertEquals(verify, true);
	}
	
	//passed
	@Test(groups="system")
	public void verifyInvoicePaidinDashboardTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read Data from Excel
		String location = e.readDataFromExcelFile("Schedule", 1, 0);
		String patientName = e.readDataFromExcelFile("Patients", 4, 0);
		String appointmentType = e.readDataFromExcelFile("Schedule", 1, 1);
		String doctor = e.readDataFromExcelFile("Schedule", 4, 2);
		String room = e.readDataFromExcelFile("Schedule", 5, 3);
		String startTime = e.readDataFromExcelFile("Schedule", 4, 4);
		String date = j.getCurrentDate();
		String serviceName = e.readDataFromExcelFile("Billing", 2, 0);
		String paymentMethod = e.readDataFromExcelFile("Billing", 1, 1);
		
		String subjective = "subjectiveDemo";
		String objective = "objectiveDemo";
		String assessment = "assessmentDemo";
		String plan = "planDemo";
		
		DashboardPage d = new DashboardPage(driver);
		WebdriverUtility w = new WebdriverUtility();
		String preRevenue = d.recordRevenue();
		System.out.println("pre - " + preRevenue);
		
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
		System.out.println("pre - " + postRevenue);
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
