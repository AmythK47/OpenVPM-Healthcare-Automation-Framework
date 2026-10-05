package com.openvpm.genericUtility;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtility {

	public String readDataFromPropertiesFile(String Key) throws Exception
	{
		FileInputStream fis = new FileInputStream("./src/test/resources/commonData.properties");

		Properties p = new Properties();

		p.load(fis);
		
		return p.getProperty(Key);
	}

}
