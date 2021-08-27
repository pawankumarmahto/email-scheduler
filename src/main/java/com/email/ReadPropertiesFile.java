package com.email;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesFile
{
	public  static void readConfig() throws Exception
	{
		try
		{
		    Properties pro = new Properties();
		    InputStream iStream = null;
		    try {
		        // Loading properties file from the classpath
		        iStream = new ReadPropertiesFile().getClass().getClassLoader()
		                                 .getResourceAsStream("email.properties");
		        if(iStream == null){
		         throw new IOException("File not found");
		        }
		        pro.load(iStream);
		       } catch (IOException e) {
		        e.printStackTrace();
		       }finally {
		    	   try {
		    	        if(iStream != null){
		    	         iStream.close();
		    	        }
	    	       	} catch (IOException e) {
		    	        e.printStackTrace();
	    	       	}
	    	     }
		    Constants.delay = pro.getProperty("delay");
		    Constants.timetoquery = pro.getProperty("timetoquery");
		    Constants.setFrom = pro.getProperty("setFrom");
		    Constants.setPassword = pro.getProperty("setPassword");
		    Constants.emailTO = pro.getProperty("emailTO");	  		   
		}
		catch(Exception e) {
            throw new Exception(e);
		}

	}

}