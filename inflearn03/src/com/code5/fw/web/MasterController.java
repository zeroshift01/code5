package com.code5.fw.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.biz.welcome.Welcome;
import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_POOL;

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
		BoxContext.setThread(box);

		Transaction transaction = new Transaction_SQLITE_POOL();
		TransactionContext.setThread(transaction);

		try {

			Welcome welcome = new Welcome();

			String jsp = welcome.execute();

			TransactionContext.commit();

			RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
			dispatcher.forward(request, response);

		} catch (Exception ex) {

			ex.printStackTrace();

			TransactionContext.rollback();

		} finally {

			TransactionContext.removeThread();
			
			BoxContext.removeThread();
		}

	}

}
