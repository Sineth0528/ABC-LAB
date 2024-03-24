package com.kushan.abclab.model;

public class UserLoginModel {

	private String UserName;
	private String Password;
	private String Email;
	
	public UserLoginModel() {
	}
	
	public UserLoginModel(String userName, String password) {
		UserName = userName;
		Password = password;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getEmail() {
		return Email;
		
	}

	public void setEmail(String email) {
		Email = email;
	}
	
	
	
}
