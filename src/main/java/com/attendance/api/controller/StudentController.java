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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;
import com.attendance.api.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentService studentService;

	@GetMapping("display/{email}")
	public @ResponseBody JsonResponseClass display(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("email") String email) {
		if (email.equals(request.getSession().getAttribute("email"))) {
			return studentService.display(request, response,email);

		}
		JsonResponseClass jsonResponse = new JsonResponseClass();
		ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, "401",
				"Not a valid user.Please provide a Authorization or contact system admin.",
				"can't access this resources", (LocalDateTime.now(ZoneOffset.UTC)));
		response.setStatus(401);
		jsonResponse.setError(apiResponse);
		return jsonResponse;
	}
}
