package com.attendance.api.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {

	@Id
	public String user_id;
	public String name;
	public String email;
	public String password;
	public String role;

	public User(String user_id, String name, String email, String password, String role) {
		this.user_id = user_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public User() {
	}

}
