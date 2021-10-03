package com.attendance.api.entity;

public class User {

	private String name;
	private String email;
	private String userId;

	public User(String userId, String email, String name) {
		this.userId = userId;
		this.email = email;
		this.name = name;
	}
	
	public User() {}
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
