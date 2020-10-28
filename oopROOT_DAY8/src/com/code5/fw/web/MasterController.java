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

		Transaction transaction = TransactionContext.getThread();

		try {

			Welcome welcome = new Welcome();

			String jsp = welcome.service();

			RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);

			dispatcher.forward(request, response);

			transaction.commit();

		} catch (Exception ex) {

			transaction.rollback();
			ex.printStackTrace();

		} finally {

			transaction.close();

			TransactionContext.removeThread();
			BoxContext.removeThread();
		}

	}

}
