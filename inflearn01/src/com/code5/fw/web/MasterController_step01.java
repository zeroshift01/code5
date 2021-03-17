package com.code5.fw.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zero
 *
 */
public class MasterController_step01 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String NAME = request.getParameter("NAME");

		String MSG = "WELCOME = " + NAME;

		PrintWriter out = response.getWriter();
		out.print(MSG);
	}

}
