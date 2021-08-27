package com.model;

import java.sql.Timestamp;

public class Student  {
	
	private String name;
	private String email;
	private Timestamp lastSentMail;
	private String status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getLastSentMail() {
		return lastSentMail;
	}
	public void setLastSentMail(Timestamp lastSentMail) {
		this.lastSentMail = lastSentMail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
