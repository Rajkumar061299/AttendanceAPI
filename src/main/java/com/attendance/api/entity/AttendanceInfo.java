package com.attendance.api.entity;

public class AttendanceInfo {

	private String date;
	private String attendanceStatus;
	private String studentID;

	public AttendanceInfo(String date, String attendanceStatus) {
		this.date = date;
		this.attendanceStatus = attendanceStatus;
	}

	public AttendanceInfo() {

	}

	public String getDate() {
		return date;
	}

	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

}
