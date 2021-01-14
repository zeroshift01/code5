package com.code5.fw.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.data.InitProperty;
import com.code5.fw.data.MakeRnd;
import com.code5.fw.data.SessionB;
import com.code5.fw.db.Transaction;
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
	public static String getRND() {
		return RND;
	}

	/**
	 * 
	 */
	private static String RND = MakeRnd.createRnd(8);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static Trace trace = new Trace(MasterController.class);

	/**
	 * @param request
	 * @param response
	 * @param box
	 * @param THIS_JSP
	 */
	private void forward(HttpServletRequest request, HttpServletResponse response, Box box, String JSP_KEY)
			throws ServletException {

		try {

			MasterControllerD dao = new MasterControllerD();
			Box view = dao.getView(JSP_KEY);
			box.put(Box.KEY_FW_VIEW, view);

			String TMPL_JSP = view.s("TMPL_JSP");
			String JSP = view.s("JSP");

			trace.write("TMPL_JSP [" + TMPL_JSP + "]");
			trace.write("VIEW [" + JSP + "]");

			String THIS_JSP = TMPL_JSP;
			if ("".equals(THIS_JSP)) {
				THIS_JSP = JSP;
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(THIS_JSP);
			box.setXssConvert(true);
			dispatcher.forward(request, response);

		} catch (Exception ex) {
			trace.write(ex);
			throw new ServletException(ex);
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			InitProperty.init(request);

			if (!InitProperty.IS_INIT_OK()) {
				throw new ServletException();
			}

		} catch (Exception ex) {
			throw new ServletException(ex);
		}

		Box box = createBox(request);

		String tx = InitProperty.TRANSACTION_WAS();
		Transaction transaction = Transaction.createTransaction(tx);
		TransactionContext.setThread(transaction);

		try {

			String KEY = box.s(Box.KEY_SERVICE);

			trace.write("KEY [" + KEY + "]");

			String JSP_KEY = execute(KEY);

			trace.write("JSP_KEY [" + JSP_KEY + "]");

			forward(request, response, box, JSP_KEY);

			transaction.commit();

		} catch (Exception ex) {

			transaction.rollback();
			trace.writeErr(ex);

			box.put(Box.KEY_EXCEPTION, ex);
			forward(request, response, box, "errView");

		} finally {

			endService();

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
		BoxContext.getThread().put(Box.KEY_FW_CONTROLLER, controller);

		boolean checkUrlAuth = checkUrlAuth(controller);
		if (!checkUrlAuth) {
			throw new AuthException();
		}

		String CLASS_NAME = controller.s("CLASS_NAME");
		String METHOD_NAME = controller.s("METHOD_NAME");

		trace.write("execute [" + CLASS_NAME + "][" + METHOD_NAME + "]");

		BizController bizController = cacheBizControllerMap.get(KEY);

		if (bizController != null) {
			Method method = bizController.getClass().getDeclaredMethod(METHOD_NAME);
			String JSP_KEY = (String) method.invoke(bizController);
			return JSP_KEY;
		}

		@SuppressWarnings("rawtypes")
		Class newClass = Class.forName(CLASS_NAME);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Constructor constructor = newClass.getConstructor();

		Object instance = constructor.newInstance();

		if (!(instance instanceof BizController)) {
			throw new AuthException();
		}

		if (instance instanceof BizControllerStartExecute) {
			String JSP_KEY = ((BizControllerStartExecute) instance).start();
			if (JSP_KEY != null) {
				return JSP_KEY;
			}
		}

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
			throw new LoginException();
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
	protected Box createBox(HttpServletRequest request) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		Box box = new BoxHttp(request);
		BoxContext.setThread(box);

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

	/**
	 * 
	 */
	protected void endService() {

	}

	/**
	 * 
	 */
	public static void reload() {
		MasterController.cacheBizControllerMap.clear();
	}

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, BizController> cacheBizControllerMap = new ConcurrentHashMap<String, BizController>();
}
