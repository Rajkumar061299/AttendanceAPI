package com.attendance.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.attendance.api.POJO.LoginForm;
import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Service
public class SignInService {

	JsonResponseClass jsonResponse = new JsonResponseClass();
	ApiErrorResponse apiResponse;

	public JsonResponseClass validate(LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {

		String email = loginForm.getEmail();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter = new FilterPredicate("email", FilterOperator.EQUAL, email);
		Query query = new Query("User").setFilter(propertyFilter);
		PreparedQuery prepardQuery = datastore.prepare(query);
		Entity user = prepardQuery.asSingleEntity();

		if (user != null) {
			String name = user.getProperty("name").toString();

			if (user.getProperty("role").equals("faculty")) {
				apiResponse = new ApiErrorResponse(HttpStatus.OK, "200",
						"The " + name + " faculty successfully logged. you authorized to operation on a resources.",
						"sucessfully faculty logged", (LocalDateTime.now(ZoneOffset.UTC)));
				jsonResponse.setData(apiResponse);
				return jsonResponse;

			} else {
				apiResponse = new ApiErrorResponse(HttpStatus.OK, "200",
						"The " + name + " student successfully logged. you authorized to operation on a resources.",
						"sucessfully student logged", (LocalDateTime.now(ZoneOffset.UTC)));
				jsonResponse.setData(apiResponse);
				return jsonResponse;
			}
		}
		
		apiResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, "404",
				"please contact admin to register your credentials",
				"user was not found", (LocalDateTime.now(ZoneOffset.UTC)));
		jsonResponse.setData(apiResponse);

		return jsonResponse;
	}
}
