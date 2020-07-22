package com.code5.fw.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.fw.data.InitProperty;
import com.code5.fw.data.SessionB;
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

			String DBMS_NAME_WAS = InitProperty.DBMS_NAME_WAS();
			Transaction transaction = Transaction.getTransaction(DBMS_NAME_WAS);
			TransactionContext.setThread(transaction);

			Box box = new BoxHttp(request);
			Box.setThread(box);

			String KEY = request.getPathInfo().substring(1);

			String JSP_KEY = execute(KEY);

			MasterControllerD dao = new MasterControllerD();
			Box view = dao.getView(JSP_KEY);
			String JSP = view.s("JSP");

			RequestDispatcher dispatcher = request.getRequestDispatcher(JSP);
			dispatcher.forward(request, response);

			TransactionContext.getThread().commit();

		} catch (Exception ex) {

			TransactionContext.getThread().rollback();
			throw new ServletException(ex);

		} finally {

			TransactionContext.removeThread();
			Box.removeThread();
		}

	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String execute(String KEY) throws Exception {

		MasterControllerD dao = new MasterControllerD();

		Box controller = dao.getController(KEY);

		// [1]
		boolean checkUrlAuth = checkUrlAuth(controller);
		if (!checkUrlAuth) {
			throw new Exception();
		}

		String CLASS_NAME = controller.s("CLASS_NAME");
		String METHOD_NAME = controller.s("METHOD_NAME");

		@SuppressWarnings("rawtypes")
		Class newClass = Class.forName(CLASS_NAME);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Constructor constructor = newClass.getConstructor();

		Object instance = constructor.newInstance();

		Method method = instance.getClass().getDeclaredMethod(METHOD_NAME);
		String JSP_KEY = (String) method.invoke(instance);
		return JSP_KEY;

	}

	/**
	 * @param controller
	 * @return
	 * @throws Exception
	 */
	private static boolean checkUrlAuth(Box controller) throws Exception {

		
		String SESSION_CHECK_YN = controller.s("SESSION_CHECK_YN");
		
		// [2]
		if (!"Y".equals(SESSION_CHECK_YN)) {
			return true;
		}

		Box box = Box.getThread();
		SessionB user = box.getSessionB();
		if (user == null) {
			// [3]
			throw new Exception();
		}

		// [4]
		String AUTH = controller.s("AUTH");

		if ("".equals(AUTH)) {
			return true;
		}

		if (AUTH.indexOf(user.getAuth()) >= 0) {
			return true;
		}

		// [5]
		return false;

	}

	/**
	 * 
	 * [6]
	 * 
	 * @param KEY
	 * @return
	 * @throws Exception
	 */
	public static boolean checkUrlAuth(String KEY) throws Exception {

		MasterControllerD dao = new MasterControllerD();

		Box controller = dao.getController(KEY);

		return checkUrlAuth(controller);

	}
}
