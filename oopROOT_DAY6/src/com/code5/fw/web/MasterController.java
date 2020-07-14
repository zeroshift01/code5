package com.code5.fw.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.biz.comm002.Comm002;
import com.code5.fw.db.Transaction;

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

		try {

			// [1]
			Transaction transaction = Transaction.getTransaction("com.code5.fw.db.Transaction_SQLITE_POOL");
			TransactionContext.setThread(transaction);

			Box box = new BoxHttp(request);
			Box.setThread(box);

			Comm002 comm002 = new Comm002();
			String jsp = comm002.comm00201();

			RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
			dispatcher.forward(request, response);

			// [2]
			TransactionContext.getThread().commit();

		} catch (Exception ex) {

			// [3]
			TransactionContext.getThread().rollback();
			throw new ServletException(ex);

		} finally {

			// [4]
			TransactionContext.removeThread();
			Box.removeThread();
		}

	}

}
