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
import com.code5.fw.exception.BizException;
import com.code5.fw.exception.SessionException;

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

		String JSP = "/WEB-INF/classes/com/code4/fw/jsp/error500.jsp";

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
			JSP = view.s("JSP");

			TransactionContext.getThread().commit();

		} catch (BizException bizException) {

			request.setAttribute("bizException", bizException);
			JSP = "/WEB-INF/classes/com/code4/fw/jsp/errorBizException.jsp";

		} catch (SessionException ex) {

			JSP = "/WEB-INF/classes/com/code4/biz/com003/jsp/com00330.jsp";

		} catch (Exception ex) {

			ex.printStackTrace();

			TransactionContext.getThread().rollback();

			JSP = "/WEB-INF/classes/com/code4/fw/jsp/error500.jsp";

		} finally {

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

		MasterControllerD dao = new MasterControllerD();

		Box controller = dao.getController(KEY);
		String CLASS_NAME = controller.s("CLASS_NAME");
		String METHOD_NAME = controller.s("METHOD_NAME");

		String SESSION_CHECK_YN = controller.s("SESSION_CHECK_YN");

		if ("Y".equals(SESSION_CHECK_YN)) {

			Box box = Box.getThread();
			SessionB user = box.getSessionB();
			if (user == null) {
				throw new SessionException();
			}

		}

		@SuppressWarnings("rawtypes")
		Class newClass = Class.forName(CLASS_NAME);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Constructor constructor = newClass.getConstructor();

		Object instance = constructor.newInstance();

		Method method = instance.getClass().getDeclaredMethod(METHOD_NAME);

		String JSP_KEY = null;
		try {
			JSP_KEY = (String) method.invoke(instance);
		} catch (Exception ex) {
			throw new BizException(ex);
		}

		return JSP_KEY;

	}

}
