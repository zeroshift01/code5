package com.code5.fw.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.biz.welcome.Welcome;

/**
 * @author zero
 *
 */
public class MasterController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO [1]
		Box box = new BoxHttp(request);

		// TODO [2]
		BoxContext.setThread(box);

		try {

			Welcome welcome = new Welcome();

			// TODO [3]
			String jsp = welcome.execute();

			RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);

			dispatcher.forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			// TODO [4]
			BoxContext.removeThread();
		}

	}

}
