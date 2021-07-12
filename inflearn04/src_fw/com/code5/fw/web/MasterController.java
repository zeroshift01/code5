package com.code5.fw.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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

		// IoC
		// 1. 컴파일 없이
		// 2. 서비스 실행에 필요한 자원을 준비하고
		// 3. 서비스를 실행 후
		// 4. 작업을 마무리

		Box box = new BoxHttp(request);
		BoxContext.set(box);

		Transaction transaction = Transaction.createTransaction(transationWas);
		TransactionContext.set(transaction);

		try {

			String pathInfo = request.getPathInfo();
			String KEY = pathInfo.substring(1);

			String JSP = execute(KEY);

			RequestDispatcher dispatcher = request.getRequestDispatcher(JSP);
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

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 * 
	 * 
	 */
	public static String execute(String KEY) throws Exception {

		MasterControllerD dao = new MasterControllerD();

		// DI
		// 어떤 기능을 사용하는지 알려준다.
		// KEY -> FW_CONTROLLER -> CLASS_NAME.METHOD_NAME

		Box controller = dao.getController(KEY);
		String CLASS_NAME = controller.s("CLASS_NAME");
		String METHOD_NAME = controller.s("METHOD_NAME");

		// 1. 클래스 확인
		// (개발자의 코드를 확인하고)
		@SuppressWarnings("rawtypes")
		Class newClass = Class.forName(CLASS_NAME);

		// 2. 객체로 생성
		// (실행시점에 기능과 데이터가 결정된)
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Constructor constructor = newClass.getConstructor();

		Object instance = constructor.newInstance();

		if (!(instance instanceof BizController)) {
			throw new Exception();
		}

		// 3. 객체의 기능을 실행
		// (서비스를 실행)
		Method method = instance.getClass().getDeclaredMethod(METHOD_NAME);

		String JSP_KEY = (String) method.invoke(instance);
		return JSP_KEY;

	}

}
