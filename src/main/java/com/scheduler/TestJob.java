package com.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.email.Constants;
import com.email.GMailServer;
import com.email.ReadPropertiesFile;

public class TestJob implements Job {

    @Override
    public void execute(final JobExecutionContext ctx)
            throws JobExecutionException {

        System.out.println("Starting Executing Job....");
		try {
			ReadPropertiesFile.readConfig();
	        GMailServer sender = new GMailServer(Constants.setFrom, Constants.setPassword);

			sender.sendMail("Subject", "This is done by tomcate", Constants.setFrom, Constants.emailTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Email Sent Succesfully...");
        
    }
}