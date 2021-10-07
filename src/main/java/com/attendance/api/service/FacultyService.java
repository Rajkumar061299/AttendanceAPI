package com.attendance.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.attendance.api.POJO.User;
import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Service
public class FacultyService {
	JsonResponseClass jsonResponse = new JsonResponseClass();
	ApiErrorResponse apiResponse;

	@SuppressWarnings("deprecation")
	public JsonResponseClass addAttendance(String studentID, String date, String attendanceStatus,
			HttpServletResponse response) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey("User", studentID);

		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, key);
		Query query1 = new Query("User").setFilter(keyFilter);
		PreparedQuery prepardQuery1 = datastore.prepare(query1);
		if (prepardQuery1.countEntities() == 0) {
			apiResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, "404",
					"providing invalid studentID in the respone Body. please check the studentID",
					"Resources Not Found", (LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setError(apiResponse);
			response.setStatus(404);
			return jsonResponse;

		}
		Entity studentEntity = prepardQuery1.asSingleEntity();

		String name = studentEntity.getProperty("name").toString();
		String email = studentEntity.getProperty("email").toString();

		String studentAttendance_id = UUID.randomUUID().toString();

//		AttendanceDetails details = new AttendanceDetails();
//		
//		Key k = new KeyFactory.Builder("User",studentID).addChild("AttendanceDetails", studentAttendance_id)
//				.getKey();
//		
//		details.attendanceId = k.getName().toString();
//		

		Entity e = new Entity("AttendanceDetails", studentAttendance_id, key);
		e.setProperty("name", name);
		e.setProperty("date", date);
		e.setProperty("email", email);
		e.setProperty("attendanceStatus", attendanceStatus);

		Filter propertyFilter = new FilterPredicate("date", FilterOperator.EQUAL, date);
		Filter propertyFilter1 = new FilterPredicate("name", FilterOperator.EQUAL, name);

		Filter compositeFilter = CompositeFilterOperator.and(propertyFilter, propertyFilter1);

		Query query = new Query("AttendanceDetails").setFilter(compositeFilter);
		PreparedQuery prepardQuery = datastore.prepare(query);
		Entity result = prepardQuery.asSingleEntity();
		if (result == null) {
			datastore.put(e);
			apiResponse = new ApiErrorResponse(HttpStatus.CREATED, "201",
					"This student " + name + " were provided attendance successfully",

					"Attendance were provided", (LocalDateTime.now(ZoneOffset.UTC)));
			response.setStatus(201);
			jsonResponse.setData(apiResponse);
			return jsonResponse;
		} else {

			apiResponse = new ApiErrorResponse(HttpStatus.CONFLICT, "409",
					"This studnet " + name + " were already updated",

					"You already updated", (LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setError(apiResponse);
			response.setStatus(409);
			return jsonResponse;

		}
	}

	public JsonResponseClass getAnStudentDetails(HttpServletResponse response) {
		List<User> listOfStudents = new ArrayList<>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter = new FilterPredicate("role", FilterOperator.EQUAL, "student");
		Query query = new Query("User").setFilter(propertyFilter);
		PreparedQuery prepardQuery = datastore.prepare(query);
		Iterator<Entity> iterator = prepardQuery.asIterator();
		while (iterator.hasNext()) {
			Entity e = iterator.next();
			listOfStudents.add(new User(e.getKey().getName().toString(), e.getProperty("email").toString(),
					e.getProperty("name").toString()));
		}
		if (listOfStudents.size() == 0) {
			apiResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, "404",
					"admin won't update a student details. Please contact Admin or Please try after some time till ",
					"no student details were updated", (LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setError(apiResponse);
			response.setStatus(404);
			return jsonResponse;
		}

		jsonResponse.setData(listOfStudents);
		return jsonResponse;
	}

}
