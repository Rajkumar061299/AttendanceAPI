package com.attendance.api.filter;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.geronimo.mail.util.Base64;

public class AuthFilter implements Filter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String authToken = req.getHeader(AUTHORIZATION_HEADER_KEY);

		if (authToken != null) {

			authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");

			byte[] bytes = Base64.decode(authToken);

			String decodedString = new String(bytes);

			StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");

			String email = tokenizer.nextToken();
			String password = tokenizer.nextToken();
			if (email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
				chain.doFilter(request, response);
			}
		} else {
			res.setStatus(401);
			response.getWriter().print(""

					+ "{\r\n" + "    \"error\": {\r\n" + "        \"status\": \"UNAUTHORIZED\",\r\n"
					+ "        \"status_code\": \"401\",\r\n"
					+ "        \"message\": \"You missing authentication header. please provide a header.\",\r\n"
					+ "        \"detail\": \"User can't access this resources\",\r\n"

					+ "    },\r\n" + "    \"data\": null\r\n" + "}");
		}

	}

	@Override
	public void destroy() {

	}

}
