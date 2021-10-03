package com.attendance.api.entity;

public class UpdateInfo {

	private String email;
	private String date;
	private String attendanceStatus;

	public String getEmail() {
		return email;
	}

	public String getDate() {
		return date;
	}

	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public UpdateInfo(String email, String date, String attendanceStatus) {
		this.email = email;
		this.date = date;
		this.attendanceStatus = attendanceStatus;
	}

	public UpdateInfo() {
	}

}
