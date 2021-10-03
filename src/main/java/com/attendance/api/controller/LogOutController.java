package com.attendance.api.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;

@Controller
public class LogOutController {

	@GetMapping("/logout")
	public @ResponseBody JsonResponseClass logout(HttpServletRequest request) throws IOException {
		request.getSession().invalidate();
		JsonResponseClass jsonResponse = new JsonResponseClass();
		ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.OK, "200",
				"You successfully logged out. Thank you", "successfully logged out",
				(LocalDateTime.now(ZoneOffset.UTC)));
		jsonResponse.setData(apiResponse);
		return jsonResponse;
	}

}
