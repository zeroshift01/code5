package com.code5.fw.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.data.InitYaml;
import com.code5.fw.db.Transaction;

/**
 * @author zero
 *
 */
public class MasterController extends HttpServlet {

	// transationWas, 실행시점에 사용할 Connection 객체 결정
	// 개발환경일 경우 Transaction_SQLITE_JDBC_CODE5_DEV

	// 운영환경일 경우 Transaction_SQLITE_POOL
	// Connection POOL 을 통해 생성비용이 큰 객체를 재사용
	// 개발환경에선 불필요

	// Exception 이 발생했을때 rollback 규칙을 프레임워크 코드로 정의

	/**
	 * 
	 */
	private String transationWas = null;

	/**
	 *
	 */
	public void init() throws ServletException {
		transationWas = InitYaml.get().s("TRANSACTION.WAS");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Box box = new BoxHttp(request);
		BoxContext.set(box);

		Transaction transaction = Transaction.createTransaction(transationWas);
		TransactionContext.set(transaction);

		try {

			String jspUrl = "서비스로직 수행 결과";

			RequestDispatcher dispatcher = request.getRequestDispatcher(jspUrl);
			dispatcher.forward(request, response);

			TransactionContext.commit();

		} catch (Exception ex) {

			ex.printStackTrace();
			try {
				TransactionContext.rollback();
			} catch (SQLException exx) {
				exx.printStackTrace();
				throw new ServletException(exx);
			}

		} finally {

			TransactionContext.remove();
			BoxContext.remove();
		}

	}

}
