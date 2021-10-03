package com.attendance.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

	public JsonResponseClass validate(String email, String password, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResponseClass jsonResponse = new JsonResponseClass();
		ApiErrorResponse apiResponse;

		if (email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			request.getSession().setAttribute("email", "admin");
			apiResponse = new ApiErrorResponse(HttpStatus.OK, "200",
					"admin successfully logged. you authorized to operation on a resources.",
					"sucessfully admin logged", (LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setData(apiResponse);
			return jsonResponse;

		} else {

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Filter propertyFilter = new FilterPredicate("email", FilterOperator.EQUAL, email);
			Query query = new Query("User").setFilter(propertyFilter);
			PreparedQuery prepardQuery = datastore.prepare(query);
			Entity user = prepardQuery.asSingleEntity();
			if (user != null) {

				if (user.getProperty("password").equals(password)) {
					request.getSession().setAttribute("userId", user.getKey().getName().toString());
					request.getSession().setAttribute("email", email);

					if (user.getProperty("role").equals("faculty")) {
						apiResponse = new ApiErrorResponse(HttpStatus.OK, "200",
								"faculty successfully logged. you authorized to operation on a resources.",
								"sucessfully faculty logged", (LocalDateTime.now(ZoneOffset.UTC)));
						jsonResponse.setData(apiResponse);
						return jsonResponse;

					} else {
						apiResponse = new ApiErrorResponse(HttpStatus.OK, "200",
								"student successfully logged. you authorized to operation on a resources.",
								"sucessfully student logged", (LocalDateTime.now(ZoneOffset.UTC)));
						jsonResponse.setData(apiResponse);
						return jsonResponse;
					}
				} else {

					apiResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, "401",
							"password was not correct .Please provide a exact password or contact system admin.",
							"can't access this resources", (LocalDateTime.now(ZoneOffset.UTC)));
					jsonResponse.setError(apiResponse);
					response.setStatus(401);
					return jsonResponse;
				}
			} else {
				apiResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, "404",
						"please contact admin to register your credentials", "no user were found",
						(LocalDateTime.now(ZoneOffset.UTC)));
				response.setStatus(404);
				jsonResponse.setError(apiResponse);
				return jsonResponse;
			}

		}
	}

}
