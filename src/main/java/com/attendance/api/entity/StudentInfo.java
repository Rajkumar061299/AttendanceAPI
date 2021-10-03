package com.attendance.api.entity;

public class StudentInfo {
	
	private String name;
	private String attendanceStatus;
	public StudentInfo(String name, String attendanceStatus) {
		this.name = name;
		this.attendanceStatus = attendanceStatus;
	}
	public StudentInfo() {
		super();
	}
	public String getName() {
		return name;
	}
	public String getAttendanceStatus() {
		return attendanceStatus;
	}
	@Override
	public String toString() {
		return "StudentInfo [name=" + name + ", attendanceStatus=" + attendanceStatus + "]";
	}
	
	
	

}
