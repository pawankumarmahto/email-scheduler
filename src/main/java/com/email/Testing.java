package com.email;

import java.util.TimerTask;

public class Testing extends TimerTask {

	public void run() {

		GMailServer sender = new GMailServer(Constants.setFrom, Constants.setPassword);

		try {
			sender.sendMail("bhai ho gaya", "This is done by tomcate", Constants.setFrom, Constants.emailTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Email Sent Succesfully...");

	}
}