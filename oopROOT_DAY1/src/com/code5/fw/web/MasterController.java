package com.code5.fw.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.biz.board.web.BoardC;

/**
 * @author seuk
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

	String pathInfo = request.getPathInfo().substring(1);
	String mode = request.getParameter("mode");
	String jsp = "error.jsp";

	if ("BoardC".equals(pathInfo)) {

		BoardC boardC = new BoardC();

		if ("listContents".equals(mode)) {

			jsp = boardC.listContents(request);

		} else if ("loadContent".equals(mode)) {

			jsp = boardC.loadContent(request);

		} else if ("saveContent".equals(mode)) {

			jsp = boardC.saveContent(request);

		}
	}

	RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
	dispatcher.forward(request, response);

}

}
