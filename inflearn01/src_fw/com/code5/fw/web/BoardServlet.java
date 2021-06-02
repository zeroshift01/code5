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
public class BoardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String MODE = request.getParameter("MODE");
		PrintWriter out = response.getWriter();

		if ("callList".equals(MODE)) {
			callList(request, out);
			return;
		}

		if ("callWrite".equals(MODE)) {
			callWrite(request, out);
			return;
		}

		if ("exeWrite".equals(MODE)) {
			exeWrite(request, out);
			return;
		}

		out.println("");

	}

	/**
	 * @param request
	 * @param response
	 */
	void callList(HttpServletRequest request, PrintWriter out) {

		String FIND_STR = request.getParameter("FIND_STR");
		String FIND_OPT = request.getParameter("FIND_OPT");

		String boardData = FIND_OPT + "=" + FIND_STR;
		out.print(boardData);
	}

	/**
	 * @param request
	 * @param response
	 */
	void callWrite(HttpServletRequest request, PrintWriter out) {
	}

	/**
	 * @param request
	 * @param response
	 */
	void exeWrite(HttpServletRequest request, PrintWriter out) {

		out.println("exeWrite ok");

		callList(request, out);
	}

}
