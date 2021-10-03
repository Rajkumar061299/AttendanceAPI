package com.attendance.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.attendance.api.entity.AttendanceInfo;
import com.attendance.api.entity.UpdateInfo;
import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@Service
public class StudentService {

	JsonResponseClass jsonResponse = new JsonResponseClass();

	public JsonResponseClass display(HttpServletRequest request, HttpServletResponse response, String email) {
		List<UpdateInfo> list = new ArrayList<>();
		String userID = (String) request.getSession().getAttribute("userId");
		Key id = KeyFactory.createKey("User", userID);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query query = new Query("AttendanceDetails").setAncestor(id);
		PreparedQuery prepardQuery = datastore.prepare(query);
		Iterator<Entity> lisOfAttendance = prepardQuery.asIterator();
		while (lisOfAttendance.hasNext()) {
			Entity e = lisOfAttendance.next();
			list.add(
					new UpdateInfo(email, e.getProperty("date").toString(), e.getProperty("attendanceStatus").toString()));
		

		}
		jsonResponse.setData(list);

		if (list.size() == 0) {
			response.setStatus(404);
			ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, "404",
					"Please try after some time till faculty won't provide a Attendance", "no attendance were updated",
					(LocalDateTime.now(ZoneOffset.UTC)));
			jsonResponse.setError(apiResponse);
			return jsonResponse;
		}
		return jsonResponse;

	}
}
