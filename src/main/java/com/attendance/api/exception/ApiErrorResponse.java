package com.attendance.api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ApiErrorResponse {

	private HttpStatus status;
	private String status_code;
	private String message;
	private String detail;
	private LocalDateTime timeStamp;

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setstatus_code(String status_code) {
		this.status_code = status_code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getstatus_code() {
		return status_code;
	}

	public String getMessage() {
		return message;
	}

	public String getDetail() {
		return detail;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public ApiErrorResponse(HttpStatus status, String status_code, String message, String detail,
			LocalDateTime timeStamp) {
		this.status = status;
		this.status_code = status_code;
		this.message = message;
		this.detail = detail;
		this.timeStamp = timeStamp;
	}

	public ApiErrorResponse() {
	}

	}
