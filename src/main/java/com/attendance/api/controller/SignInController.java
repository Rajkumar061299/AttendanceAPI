package com.attendance.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.attendance.api.entity.LoginForm;
import com.attendance.api.response.JsonResponseClass;
import com.attendance.api.service.SignInService;

@Controller
public class SignInController {

	@Autowired
	SignInService signInService;
	
	@PostMapping("/signin")
	
	public @ResponseBody JsonResponseClass validate
	(@RequestBody LoginForm loginForm, 
			HttpServletRequest request, HttpServletResponse response ) {
		
		return signInService.validate(loginForm.getEmail(), loginForm.getPassword(), request, response);

	}

}
