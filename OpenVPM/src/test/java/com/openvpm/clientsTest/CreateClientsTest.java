package com.openvpm.clientsTest;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.openvpm.baseTest.BaseClass;
import com.openvpm.genericUtility.ExcelUtility;
import com.openvpm.genericUtility.JavaUtility;
import com.openvpm.objectRepository.CreateClientsPage;

@Listeners(com.openvpm.listenerUtility.ListenerImplementation.class)
public class CreateClientsTest extends BaseClass {
	
	@Test
	public void createClientTest() throws Exception
	{
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		//read data from excel
		String firstName = e.readDataFromExcelFile("Clients", 2, 0);
		String lastName = e.readDataFromExcelFile("Clients", 2, 1);
		String email = e.readDataFromExcelFile("Clients", 2, 2);
		String phone = e.readDataFromExcelFile("Clients", 2, 3);
		
		CreateClientsPage cc = new CreateClientsPage(driver);
		cc.createClient(firstName, lastName, email, phone);
	}

}
