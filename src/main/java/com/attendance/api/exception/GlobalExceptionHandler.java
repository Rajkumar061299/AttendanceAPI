package com.attendance.api.exception;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.attendance.api.response.JsonResponseClass;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	  @ExceptionHandler(RuntimeException.class) public @ResponseBody
	  JsonResponseClass handleException(HttpServletResponse response) {
	  
	  response.setStatus(500);
	  JsonResponseClass jsonResponse = new JsonResponseClass(); ApiErrorResponse
	  apiResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "500",
	  "Please try after some time or contact system admin.",
	  "Server encountered an problem", (LocalDateTime.now(ZoneOffset.UTC)));
	  jsonResponse.setError(apiResponse); return jsonResponse;
	  
	  }
	  
	  @ExceptionHandler(UnAuthorizedException.class) public @ResponseBody
	  JsonResponseClass handleUnauthorizedException(HttpServletResponse response) {
	  
	  response.setStatus(401);
	  JsonResponseClass jsonResponse = new JsonResponseClass(); ApiErrorResponse
	  apiResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, "401",
	  "unauthorized user. please provide the valid credentials",
	  "User can't access the resources", (LocalDateTime.now(ZoneOffset.UTC)));
	  jsonResponse.setError(apiResponse); return jsonResponse;
	  
	  }
	 
}