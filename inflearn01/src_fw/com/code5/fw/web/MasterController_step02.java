package com.code5.fw.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.welcome.Welcome;

/**
 * @author zero
 *
 */
public class MasterController_step02 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Welcome welcome = new Welcome();

		String jspUrl = welcome.execute(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher(jspUrl);
		dispatcher.forward(request, response);
	}

}
