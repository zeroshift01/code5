package com.code5.fw.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.login.Login;
import com.biz.mng001.Mng001;
import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_JDBC;

/**
 * @author seuk
 *
 */
public class MasterController_1 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Box box = new BoxHttp(request);
		BoxContext.setThread(box);

		Transaction transaction = new Transaction_SQLITE_JDBC();
		TransactionContext.setThread(transaction);

		try {

			String KEY = request.getPathInfo().substring(1);

			String JSP_KEY = execute(KEY);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSP_KEY);

			dispatcher.forward(request, response);

			transaction.commit();

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

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String execute(String KEY) throws Exception {

		if ("loginView".equals(KEY)) {

			return (new Login()).loginView();

		}

		if ("login".equals(KEY)) {

			return (new Login()).login();

		}

		if ("mng00110".equals(KEY)) {

			return (new Mng001()).mng00110();

		}

		if ("mng00111".equals(KEY)) {

			return (new Mng001()).mng00111();
		}

		// 정의되지 않은 서비스
		throw new Exception();

	}

}
