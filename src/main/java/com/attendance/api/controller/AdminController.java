package com.attendance.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.attendance.api.POJO.AddNewUser;
import com.attendance.api.POJO.UpdateInfo;
import com.attendance.api.response.JsonResponseClass;
import com.attendance.api.service.AdminService;

@Controller
@RequestMapping("api/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@PostMapping("/add")
	public @ResponseBody JsonResponseClass addUser(@RequestBody AddNewUser user, HttpServletRequest request,
			HttpServletResponse response) {

		response.setStatus(201);
		return adminService.addUser(user.getName(), user.getPassword(), user.getEmail(), user.getRole());

	}

	@PutMapping("/update")
	public @ResponseBody JsonResponseClass updateTheAttendance(HttpServletResponse response,
			@RequestBody UpdateInfo info, HttpServletRequest request) {
		return adminService.updateTheAttendance(info.getEmail(), info.getDate(), info.getAttendanceStatus(), response);
	}

	@GetMapping("/display")

	public @ResponseBody JsonResponseClass displayParticularAttendance(HttpServletResponse response,
			@RequestParam("email") String email, HttpServletRequest request, @RequestParam("date") String date) {

		String[] arrayOfEmail = email.split(",");

		List<String> list = Arrays.stream(arrayOfEmail).collect(Collectors.toList());
		return adminService.displayParticularAttendance(date, list, response);
	}

}
