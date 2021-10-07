package com.attendance.api.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Entity
public class AttendanceDetails {

	@Load public Ref<User> user;    
	
	@Id
	public String attendanceId;

	public String name;
	public String date;
	public String email;
    public String attendanceStatus;

    
	public AttendanceDetails(String attendanceId, String name, String date, String email, String attendanceStatus) {

		this.attendanceId = attendanceId;
		this.name = name;
		this.date = date;
		this.email = email;
		this.attendanceStatus = attendanceStatus;
	}


	public AttendanceDetails() {
	}

}
