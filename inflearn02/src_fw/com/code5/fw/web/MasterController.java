package com.code5.fw.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.board.Board;
import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;

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

		Box box = new BoxHttp(request);
		BoxContext.set(box);

		try {

			String pathInfo = request.getPathInfo();

			String jspUrl = null;

			if ("/callList".equals(pathInfo)) {

				Board board = new Board();
				jspUrl = board.callList();

			} else if ("/callWrite".contentEquals(pathInfo)) {

				Board board = new Board();
				jspUrl = board.callWrite();

			} else if ("/exeWrite".contentEquals(pathInfo)) {

				Board board = new Board();
				jspUrl = board.exeWrite();

			}

			if (jspUrl == null) {
				throw new ServletException();
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(jspUrl);
			dispatcher.forward(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			BoxContext.remove();
		}

	}

}
