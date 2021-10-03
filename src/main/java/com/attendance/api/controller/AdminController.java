package com.attendance.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.attendance.api.entity.AddNewUser;
import com.attendance.api.entity.UpdateInfo;
import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;
import com.attendance.api.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	JsonResponseClass jsonResponse = new JsonResponseClass();
	ApiErrorResponse apiResponse;

	@PostMapping("/add")
	public @ResponseBody JsonResponseClass addUser(@RequestBody AddNewUser user, HttpServletRequest request,
			HttpServletResponse response) {

		if ("admin".equals((String) request.getSession().getAttribute("email"))) {
			response.setStatus(201);
			return adminService.addUser(user.getName(), user.getPassword(), user.getEmail(), user.getRole());
		}

		apiResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, "401",
				"Not a valid user.Please provide a Authorization or contact system admin.",
				"can't access this resources", (LocalDateTime.now(ZoneOffset.UTC)));
		response.setStatus(401);
		jsonResponse.setError(apiResponse);
		return jsonResponse;

	}

	@PutMapping("/update")
	public @ResponseBody JsonResponseClass updateTheAttendance(HttpServletResponse response,
			@RequestBody UpdateInfo info
			, HttpServletRequest request) {
		if ("admin".equals((String) request.getSession().getAttribute("email"))) {
			return adminService.updateTheAttendance(info.getEmail(), info.getDate(),
					info.getAttendanceStatus(), response);
		}

		System.out.println(info.getAttendanceStatus());
		apiResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, "401",
				"Not a valid user.Please provide a Authorization or contact system admin.",
				"can't access this resources", (LocalDateTime.now(ZoneOffset.UTC)));
		jsonResponse.setError(apiResponse);
		response.setStatus(401);
		return jsonResponse;

	}

	@GetMapping("/display")

	public @ResponseBody JsonResponseClass displayParticularAttendance(HttpServletResponse response,
			@RequestParam("email") String email, HttpServletRequest request, @RequestParam("date") String date) {

		String[] arrayOfEmail = email.split(" ");

		List<String> list = Arrays.stream(arrayOfEmail).collect(Collectors.toList());
		if ("admin".equals((String) request.getSession().getAttribute("email"))) {
			return adminService.displayParticularAttendance(date, list, response);
		} else {
			apiResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, "401",
					"Not a valid user.Please provide a Authorization or contact system admin.",
					"can't access this resources", (LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setError(apiResponse);
			response.setStatus(401);
			return jsonResponse;
		}

	}

}
