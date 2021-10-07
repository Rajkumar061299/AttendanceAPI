package com.attendance.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.attendance.api.POJO.UpdateInfo;
import com.attendance.api.exception.ApiErrorResponse;
import com.attendance.api.response.JsonResponseClass;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Service
public class StudentService {

	private static Logger logger = Logger.getLogger(StudentService.class.getName());

	JsonResponseClass jsonResponse = new JsonResponseClass();

	public JsonResponseClass display(HttpServletRequest request, HttpServletResponse response, String email) {

		List<UpdateInfo> list = new ArrayList<>();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query1 = new Query("User").setFilter(new FilterPredicate("email", FilterOperator.EQUAL, email));
		PreparedQuery prepardQuery1 = datastore.prepare(query1);
		Entity user = prepardQuery1.asSingleEntity();
		Key id = null;
		if (user != null)
			id = KeyFactory.createKey("User", user.getKey().getName().toString());
		Query query = new Query("AttendanceDetails").setAncestor(id);
		PreparedQuery prepardQuery = datastore.prepare(query);

		Iterator<Entity> lisOfAttendance = prepardQuery.asIterator();

		/*
		 * String userID = ""; Query<User> query =
		 * OfyService.ofy().load().type(User.class).filter("email =", email); for (User
		 * student : query) userID = student.user_id; logger.log(Level.INFO, "UserID");
		 * logger.log(Level.INFO, userID);
		 */
		/*
		 * AttendanceDetails attendanceDetails = new AttendanceDetails();
		 * 
		 * attendanceDetails.user = Ref.create(Key.create(User.class, userID));
		 * 
		 * try { OfyService.ofy().save().entity(attendanceDetails).now(); } catch
		 * (Exception e) { logger.log(Level.WARNING, "Exception :: ", e); }
		 * 
		 * AttendanceDetails fetch =
		 * OfyService.ofy().load().entity(attendanceDetails).now();
		 * System.out.println("5"); User user = fetch.user.get();
		 */

		/*
		 * Key<User> rootKey = Key.create(User.class, userID);
		 * 
		 * List<AttendanceDetails> query2 =
		 * OfyService.ofy().load().type(AttendanceDetails.class).ancestor(rootKey).list(
		 * ); logger.log(Level.INFO, query2.toString()); jsonResponse.setData(list);
		 */
		/*
		 * User user =
		 * OfyService.ofy().load().group(AttendanceDetails.class).key(rootKey).now();
		 * 
		 * System.out.println(user.email+"=====" +user.name);
		 */

//		Iterable<AttendanceDetails> iterable = query1.iterable();

//		query1.forEach(list.add(new UpdateInfo(email, this.,
//				  attendance.attendanceStatus))
//	);
		// erable.forEach((student) -> System.out.println(student))
		// iterable.forEach((attendance) -> list.add(new UpdateInfo(email,
		// attendance.date,
		// attendance.attendanceStatus)));

//		
//		for(AttendanceDetails attendance : iterable) {
//			System.out.println(attendance.date);
//			list.add(new UpdateInfo(email, attendance.date,
//					  attendance.attendanceStatus));		
//		}

		while (lisOfAttendance.hasNext()) {
			Entity e = lisOfAttendance.next();

			list.add(new UpdateInfo(email, e.getProperty("date").toString(),
					e.getProperty("attendanceStatus").toString()));

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
