package com.attendance.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.attendance.api.POJO.LoginForm;
import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;
import com.attendance.api.service.SignInService;

//@Controller
public class SignInController {

	@Autowired
	SignInService signInService;

//	@PostMapping("/signin")
	public @ResponseBody JsonResponseClass validateUser(@RequestBody LoginForm loginForm, HttpServletRequest request,
			HttpServletResponse response) {

		return signInService.validate(loginForm, request, response);

	}

//	@PostMapping("/signin/admin")
	public @ResponseBody JsonResponseClass validateAdmin(@RequestBody LoginForm loginForm, HttpServletRequest request,
			HttpServletResponse response) {

		JsonResponseClass jsonResponse = new JsonResponseClass();
		ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.OK, "200",
				"admin successfully logged. you authorized to operation on a resources.", "sucessfully admin logged",
				(LocalDateTime.now(ZoneOffset.UTC)));
		jsonResponse.setData(apiResponse);
		return jsonResponse;

	}

}
