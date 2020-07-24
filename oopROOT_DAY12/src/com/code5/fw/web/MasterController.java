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
import com.code5.fw.exception.SessionException;
import com.code5.fw.trace.Trace;

/**
 * @author seuk
 *
 */
public class MasterController extends HttpServlet {

	/**
	 * 
	 */
	private static Trace trace = Trace.getTrace();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String JSP = null;

		try {

			// TODO
			Trace.getTrace().init(request);

			String DBMS_NAME_WAS = InitProperty.DBMS_NAME_WAS();
			Transaction transaction = Transaction.getTransaction(DBMS_NAME_WAS);
			TransactionContext.setThread(transaction);

			Box box = new BoxHttp(request);
			Box.setThread(box);

			String KEY = request.getPathInfo().substring(1);

			// TODO
			box.setUrl(KEY);

			String JSP_KEY = execute(KEY);

			MasterControllerD dao = new MasterControllerD();
			Box view = dao.getView(JSP_KEY);
			JSP = view.s("JSP");

			TransactionContext.getThread().commit();

		} catch (SessionException ex) {

			JSP = "/WEB-INF/classes/com/code5/biz/com003/jsp/com00330.jsp";

		} catch (Exception exception) {

			TransactionContext.getThread().rollback();

			request.setAttribute("exception", exception);
			JSP = "/WEB-INF/classes/com/code5/fw/jsp/error500.jsp";

		} finally {

			trace.write("JSP [" + JSP + "]");
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSP);
			dispatcher.forward(request, response);

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

		trace.write("URL [" + KEY + "]");

		MasterControllerD dao = new MasterControllerD();

		Box controller = dao.getController(KEY);

		boolean checkUrlAuth = checkUrlAuth(controller);
		if (!checkUrlAuth) {
			throw new Exception("사용할 수 없는 서비스 입니다.");
		}

		String CLASS_NAME = controller.s("CLASS_NAME");
		String METHOD_NAME = controller.s("METHOD_NAME");

		trace.write("METHOD_NAME [" + METHOD_NAME + "]");

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

		if (!"Y".equals(SESSION_CHECK_YN)) {
			return true;
		}

		Box box = Box.getThread();
		SessionB user = box.getSessionB();
		if (user == null) {
			throw new SessionException();
		}

		String AUTH = controller.s("AUTH");

		if ("".equals(AUTH)) {
			return true;
		}

		if (AUTH.indexOf(user.getAuth()) >= 0) {
			return true;
		}

		return false;

	}

	/**
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

	@Override
	public void destroy() {
		Trace.getTrace().flush();
		super.destroy();
	}
}
