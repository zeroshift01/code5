package com.code5.fw.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.data.SessionB;
import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_JDBC;
import com.code5.fw.trace.Trace;
import com.code5.fw.trace.TraceRunner;

/**
 * @author seuk
 *
 */
public class MasterController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static Trace trace = new Trace(MasterController.class);

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Box box = createBox(request);

		BoxContext.setThread(box);

		Transaction transaction = new Transaction_SQLITE_JDBC();
		TransactionContext.setThread(transaction);

		try {

			String KEY = box.s(Box.KEY_SERVICE);

			trace.write("KEY [" + KEY + "]");

			String JSP_KEY = execute(KEY);

			trace.write("JSP_KEY [" + JSP_KEY + "]");

			MasterControllerD dao = new MasterControllerD();
			Box view = dao.getView(JSP_KEY);
			String JSP = view.s("JSP");

			trace.write("VIEW [" + JSP + "]");

			RequestDispatcher dispatcher = request.getRequestDispatcher(JSP);

			dispatcher.forward(request, response);

			transaction.commit();

		} catch (Exception ex) {

			transaction.rollback();
			trace.writeErr(ex);

		} finally {

			transaction.closeConnection();
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

		MasterControllerD dao = new MasterControllerD();

		Box controller = dao.getController(KEY);

		boolean checkUrlAuth = checkUrlAuth(controller);
		if (!checkUrlAuth) {
			throw new Exception("사용할 수 없는 서비스 입니다.");
		}

		String CLASS_NAME = controller.s("CLASS_NAME");
		String METHOD_NAME = controller.s("METHOD_NAME");

		trace.write("execute [" + CLASS_NAME + "][" + METHOD_NAME + "]");

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
	 * 
	 * 
	 */
	private static boolean checkUrlAuth(Box controller) throws Exception {

		String SESSION_CHECK_YN = controller.s("SESSION_CHECK_YN");

		if (!"Y".equals(SESSION_CHECK_YN)) {
			return true;
		}

		Box box = BoxContext.getThread();
		SessionB user = box.getSessionB();
		if (user == null) {
			throw new Exception();
		}

		String AUTH = controller.s("AUTH");

		if ("".equals(AUTH)) {
			return true;
		}

		if (AUTH.indexOf(user.getAuth()) >= 0) {
			return true;
		}

		trace.write("checkUrlAuth false [" + AUTH + "][" + user.getAuth() + "]");

		return false;

	}

	/**
	 * 
	 * @param KEY
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean checkUrlAuth(String KEY) throws Exception {

		MasterControllerD dao = new MasterControllerD();

		Box controller = dao.getController(KEY);

		return checkUrlAuth(controller);

	}

	/**
	 * @param request
	 * @return
	 */
	private Box createBox(HttpServletRequest request) {

		Box box = new BoxHttp(request);

		String KEY = request.getPathInfo().substring(1);
		box.put(Box.KEY_SERVICE, KEY);

		box.put(Box.KEY_REMOTE_ADDR, request.getRemoteAddr());

		Object sessionB = request.getSession().getAttribute(Box.KEY_SESSIONB);
		if (sessionB instanceof SessionB) {
			box.put(Box.KEY_SESSIONB, sessionB);
		}

		return box;

	}
	

	@Override
	public void destroy() {
		TraceRunner.getTraceRunner().flush();
		super.destroy();
	}
}
