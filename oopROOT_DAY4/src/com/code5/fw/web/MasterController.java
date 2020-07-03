package com.code5.fw.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * @return
	 */
	private String execSubController() throws Exception {

		MasterControllerD dao = new MasterControllerD();
		String[] str = dao.getSubController();
		String className = str[0];
		String methodName = str[1];

		@SuppressWarnings("rawtypes")
		Class newClass = Class.forName(className);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Constructor constructor = newClass.getConstructor();

		SubController instance = (SubController) constructor.newInstance();

		instance.start();

		String startAndReturn = instance.startAndReturn();
		if (startAndReturn != null) {
			return startAndReturn;
		}

		Method method = instance.getClass().getDeclaredMethod(methodName);
		String invoke = (String) method.invoke(instance);

		instance.end();

		return invoke;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Box box = new BoxHttp(request);
		Box.setThread(box);

		try {

			String pathInfo = request.getPathInfo().substring(1);

			box.put("pathInfo", pathInfo);

			String jsp = execSubController();

			RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
			dispatcher.forward(request, response);

		} catch (Exception ex) {
			throw new ServletException(ex);
		} finally {
			Box.removeThread();
		}

	}

}
