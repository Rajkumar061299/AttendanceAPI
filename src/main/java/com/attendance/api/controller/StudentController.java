package com.attendance.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.attendance.api.response.JsonResponseClass;
import com.attendance.api.service.StudentService;

@Controller
@RequestMapping("api/student")
public class StudentController {
	@Autowired
	StudentService studentService;

	@GetMapping("display")
	public @ResponseBody JsonResponseClass display(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("email") String email) {
		return studentService.display(request, response, email);

	}
}
