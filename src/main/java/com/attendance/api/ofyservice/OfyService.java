package com.attendance.api.ofyservice;

import com.attendance.api.entity.AttendanceDetails;
import com.attendance.api.entity.User;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {

	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(AttendanceDetails.class);

	}

	public static ObjectifyFactory factory() {

		return ObjectifyService.factory();

	}

	public static Objectify ofy() {

		return ObjectifyService.ofy();
	}

}
