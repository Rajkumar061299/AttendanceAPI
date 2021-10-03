package com.attendance.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.attendance.api.entity.AttendanceInfo;
import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;
import com.attendance.api.service.FacultyService;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

	@Autowired
	FacultyService facultyService;
	JsonResponseClass jsonResponse = new JsonResponseClass();
	ApiErrorResponse apiResponse;

	@PostMapping("/addAttendance/{email}")
	public @ResponseBody JsonResponseClass addAttendance(HttpServletResponse response, HttpServletRequest request,
			@PathVariable String email, @RequestBody AttendanceInfo attendance) {
		String studentID = attendance.getStudentID();
		if (studentID.equals("")) {

			apiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "400",
					"Missing studnetID in the respone Body. please provide the Email header", "missing studentID",
					(LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setError(apiResponse);
			response.setStatus(400);
			return jsonResponse;
		}
		if (email.equals((String) request.getSession().getAttribute("email"))) {
			return facultyService.addAttendance(studentID, attendance.getDate(), attendance.getAttendanceStatus(),
					response);

		}
		apiResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, "401",
				"Not a valid user.Please provide a Authorization or contact system admin.",
				"can't access this resources", (LocalDateTime.now(ZoneOffset.UTC)));
		jsonResponse.setError(apiResponse);
		response.setStatus(401);
		return jsonResponse;

	}

	@GetMapping("/list/{email}")

	public @ResponseBody JsonResponseClass getAnStudentDetails(HttpServletResponse response, HttpServletRequest request,
			@PathVariable String email) {

		if (email.equals((String) request.getSession().getAttribute("email"))) {
			return facultyService.getAnStudentDetails(response);
		}
		apiResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, "401",
				"Not a valid user.Please provide a Authorization or contact system admin.",
				"can't access this resources", (LocalDateTime.now(ZoneOffset.UTC)));
		jsonResponse.setError(apiResponse);
		response.setStatus(401);
		return jsonResponse;

	}

}
