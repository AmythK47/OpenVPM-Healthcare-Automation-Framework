package com.openvpm.listenerUtility;

import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.openvpm.genericUtility.ThreadlocalUtility;

public class ListenerImplementation implements ITestListener, ISuiteListener {
	
	public ExtentSparkReporter spark;
	public ExtentReports report;
	public ExtentTest test;
	
	public void onStart(ISuite suite)
	{
		//Report Configuration
		String suiteName = suite.getName();
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		
		spark = new ExtentSparkReporter("./AdvancedReports/"+suiteName+"_"+time+".html");
		spark.config().setDocumentTitle("OpenVPM");
		spark.config().setReportName(suiteName);
		spark.config().setTheme(Theme.DARK);
		
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows 11");
		report.setSystemInfo("Device", "ASUS TUF F15");
		
	}
	
	public void onTestStart(ITestResult res)
	{
		//create report
		String name = res.getMethod().getMethodName();
		test = report.createTest(name);
		ThreadlocalUtility.setTest(test);
		ThreadlocalUtility.getTest().log(Status.INFO, name+" ----> TEST STARTED");
	}
	
	public void onTestSuccess(ITestResult res)
	{
		//success info log
		String name = res.getMethod().getMethodName();
		ThreadlocalUtility.getTest().log(Status.PASS, name+" ----> TEST SUCCESSFULL");
	}
	
	public void onTestFailure(ITestResult res)
	{
		//screenshot configuration
		String name = res.getMethod().getMethodName();
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		
		TakesScreenshot tks = (TakesScreenshot)ThreadlocalUtility.getDriver();
		String filePath = tks.getScreenshotAs(OutputType.BASE64);
		ThreadlocalUtility.getTest().addScreenCaptureFromBase64String(filePath, name+"_"+time);
		
		ThreadlocalUtility.getTest().log(Status.FAIL, name+" ----> TEST FAILED");
	}
	
	public void onTestSkipped(ITestResult res)
	{
		//skipped info log
		String name = res.getMethod().getMethodName();
		ThreadlocalUtility.getTest().log(Status.SKIP, name+" ----> TEST SKIPPED");
		
	}

	public void onFinish(ISuite suite)
	{
		//report backup
		report.flush();
		String suiteName = suite.getName();
		ThreadlocalUtility.getTest().log(Status.INFO, suiteName+" ----> SUITE COMPLETED");
		
	}
	
}
