package com.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.email.DBScheduler;

public class TestJob implements Job {

    @Override
    public void execute(final JobExecutionContext ctx)
            throws JobExecutionException {

        System.out.println("Executing Job");
        try {
        	 DBScheduler dbs = new DBScheduler();
     		dbs.callScheduler();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}