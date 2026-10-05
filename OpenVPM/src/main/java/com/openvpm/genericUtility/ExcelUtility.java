package com.openvpm.genericUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * This class contains methods to read data from excel file, write data back to excel file
 * Apache POI dependency has been used here
 * 
 * @author Amit
 */

public class ExcelUtility {
	
	FileInputStream fis1;
	
	public String readDataFromExcelFile(String sheetName, int rowNum, int cellNum) throws Exception
	{
		fis1 = new FileInputStream("./src/test/resources/testData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		Cell cell = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum);
		//can also use to string method
		DataFormatter df = new DataFormatter();
		return df.formatCellValue(cell);
		
	}
	
	public int getRowCount(String sheetName) throws Exception
	{
	    fis1 = new FileInputStream("./src/test/resources/testData.xlsx");
	    Workbook wb = WorkbookFactory.create(fis1);
	    int rowCount = wb.getSheet(sheetName).getLastRowNum();
	    wb.close();

	    return rowCount;
	}
	
	public int getLastRowNumbers(String sheetName) throws Exception
	{
		fis1 = new FileInputStream("./src/test/resources/testData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		wb.close();
		return wb.getSheet(sheetName).getPhysicalNumberOfRows();
	}
	
	public int getLastCellNumber(String sheetName, int rowNum) throws Exception
	{
		fis1 = new FileInputStream("./src/test/resources/testData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		wb.close();
		return wb.getSheet(sheetName).getRow(rowNum).getPhysicalNumberOfCells();
	}

	
	public void modifyExistingCellValue(String sheetName, int rowNum, int cellNum, String value) throws Exception
	{
		fis1 = new FileInputStream("./src/test/resources/testData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).setCellValue(value);
		
		FileOutputStream fos = new FileOutputStream("./src/test/resources/testData.xlsx");
		wb.write(fos);
		
		wb.close();
	}
	
	public void writeInNewCellValue(String sheetName, int rowNum, int cellNum, String value) throws Exception 
	{
		fis1 = new FileInputStream("./src/test/resources/testData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		wb.getSheet(sheetName).getRow(rowNum).createCell(cellNum).setCellValue(value);
		
		FileOutputStream fos = new FileOutputStream("./src/test/resources/testData.xlsx");
		wb.write(fos);
		
		wb.close();
	}
	
	public void createNewSheet(String sheetName, int rowNum, int cellNum, String value) throws Exception
	{
		fis1 = new FileInputStream("./src/test/resources/testData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		wb.createSheet(sheetName).createRow(rowNum).createCell(cellNum).setCellValue(value);
		
		FileOutputStream fos = new FileOutputStream("./src/test/resources/testData.xlsx");
		wb.write(fos);
		
		wb.close();
	}

}
