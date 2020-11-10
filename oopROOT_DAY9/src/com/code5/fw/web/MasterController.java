package com.code5.fw.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_JDBC;

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

		Box box = new BoxHttp(request);
		BoxContext.setThread(box);

		// [1]
		Transaction transaction = new Transaction_SQLITE_JDBC();
		TransactionContext.setThread(transaction);

		try {

			Welcome welcome = new Welcome();

			String jsp = welcome.service();

			RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);

			dispatcher.forward(request, response);

			// [2]
			transaction.commit();

		} catch (Exception ex) {

			// [3]
			transaction.rollback();
			ex.printStackTrace();

		} finally {

			// [4]
			transaction.close();

			TransactionContext.removeThread();
			BoxContext.removeThread();
		}

	}

}
