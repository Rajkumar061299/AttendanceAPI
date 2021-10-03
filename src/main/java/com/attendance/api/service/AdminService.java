package com.attendance.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.attendance.api.entity.StudentInfo;
import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Service
public class AdminService {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	JsonResponseClass jsonResponse = new JsonResponseClass();
	ApiErrorResponse apiResponse;

	public JsonResponseClass addUser(String name, String password, String email, String user) {
		
		String user_id = UUID.randomUUID().toString();
		Entity entity = new Entity("User", user_id);
		entity.setProperty("name", name);
		entity.setProperty("email", email);
		entity.setProperty("password", password);
		entity.setProperty("role", user);
		datastore.put(entity);
		apiResponse = new ApiErrorResponse(HttpStatus.CREATED, "201", "Faculty " + name + " were created successfully",
				"faculty were created", (LocalDateTime.now(ZoneOffset.UTC)));
		jsonResponse.setData(apiResponse);

		if (user.equalsIgnoreCase("student")) {
			apiResponse = new ApiErrorResponse(HttpStatus.CREATED, "201",
					"Student " + name + " were created successfully", "Student were created",
					(LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setData(apiResponse);

		}
		return jsonResponse;
	}

	public JsonResponseClass updateTheAttendance(String email, String date, String attendanceStatus,
			HttpServletResponse response) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter propertyFilter = new FilterPredicate("email", FilterOperator.EQUAL, email);
		Query query = new Query("User").setFilter(propertyFilter);
		PreparedQuery prepardQuery = datastore.prepare(query);
		Entity user = prepardQuery.asSingleEntity();
		if (user != null) {
			String studentId = user.getKey().getName().toString();

			Filter propertyFilter1 = new FilterPredicate("date", FilterOperator.EQUAL, date);

			Query query1 = new Query("AttendanceDetails").setFilter(propertyFilter1).setAncestor(user.getKey());
			PreparedQuery prepardQuery1 = datastore.prepare(query1);

			Entity result = prepardQuery1.asSingleEntity();

			Key k = new KeyFactory.Builder("User", studentId).addChild("AttendanceDetails", result.getKey().getName())
					.getKey();

			Entity e;
			try {
				e = datastore.get(k);
				String name = (String) user.getProperty("name");
				e.setProperty("name", name);
				e.setProperty("date", date);
				e.setProperty("attendanceStatus", attendanceStatus);
				datastore.put(e);
				apiResponse = new ApiErrorResponse(HttpStatus.NO_CONTENT, "204",
						"Student: " + name + " date: " + date + " status: " + attendanceStatus + " were updated",
						"updated successfully", (LocalDateTime.now(ZoneOffset.UTC)));
				response.setStatus(204);
				jsonResponse.setError(apiResponse);
			} catch (EntityNotFoundException ex) {
				apiResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "500", ex.getLocalizedMessage(),
						"server Error", (LocalDateTime.now(ZoneOffset.UTC)));
				jsonResponse.setError(apiResponse);
				response.setStatus(500);
			}
		}
		return jsonResponse;
	}

	public JsonResponseClass displayParticularAttendance(String date, List<String> list, HttpServletResponse response) {
		List<StudentInfo> listOfStudents = new ArrayList<>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter propertyFilter = new FilterPredicate("email", FilterOperator.IN, list);
		Filter propertyFilter1 = new FilterPredicate("date", FilterOperator.EQUAL, date);

		Filter compositeFilter = CompositeFilterOperator.and(propertyFilter, propertyFilter1);
		Query query = new Query("AttendanceDetails").setFilter(compositeFilter);
		PreparedQuery prepardQuery = datastore.prepare(query);
		Iterator<Entity> iterator = prepardQuery.asIterator();
		List<String> queriedEmail = new ArrayList<>();

		boolean flag = false;
		while (iterator.hasNext()) {
			flag = true;
			Entity e = iterator.next();
			queriedEmail.add(e.getProperty("email").toString());
			listOfStudents.add(
					new StudentInfo(e.getProperty("name").toString(), e.getProperty("attendanceStatus").toString()));
		}
		
		if (queriedEmail.size() != list.size()) {
			Set <Object> list1 = new HashSet<>(list);
			Set <Object> queriedEmail1 = new HashSet<>(queriedEmail);
			Set<Object> copyList = new HashSet<>(list1);

			copyList.removeAll(queriedEmail1);
			
			apiResponse = new ApiErrorResponse(HttpStatus.MULTI_STATUS, "207",
					queriedEmail+" were found and " +copyList.toString()+" were not found",
					"some data were found", (LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setData(listOfStudents);
			jsonResponse.setError(apiResponse);
			response.setStatus(207);
			return jsonResponse;

		}
		jsonResponse.setData(listOfStudents);
		if (flag == false) {
			apiResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, "404",
					"faculty won't update a student attendance details.Please try after some time ",
					"no student attendance details were updated", (LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setError(apiResponse);
			response.setStatus(404);
		}
		return jsonResponse;
	}

}
