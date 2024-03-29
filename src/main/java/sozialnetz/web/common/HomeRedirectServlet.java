/*
 * Copyright (c) 2008 IT - ITBA -- All rights reserved
 */
package sozialnetz.web.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeRedirectServlet extends HttpServlet {
	private static final String HOME_URL_PARAM = "homeUrl";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String target = this.getInitParameter(HOME_URL_PARAM);
		if (target == null) {
			throw new UnavailableException(
					"No home has been defined. Set property");
		}
		System.out.println("TARGET!!!!!!! ___" + target);
		resp.sendRedirect(target);
	}
}
