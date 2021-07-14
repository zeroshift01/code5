package com.code5.fw.web;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.data.InitYaml;
import com.code5.fw.data.SessionB;
import com.code5.fw.db.Transaction;
import com.code5.fw.trace.Trace;
import com.code5.fw.trace.TraceRunner;

/**
 * @author zero
 *
 */
public class MasterController extends HttpServlet implements Reload {

	/**
	 * 
	 */
	private static String WEB_APP_DIR = InitYaml.get().getWebAppDir();

	/**
	 * 
	 */
	private static boolean IS_CACHE = InitYaml.get().isCache();

	/**
	 * 
	 */
	private String characterSet = InitYaml.get().getCharacterSet();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static Trace trace = new Trace(MasterController.class);

	/**
	 * @param view
	 * @return
	 */
	private static String convertUrlForView(String view) {

		if ("".equals(view)) {
			return view;
		}

		if (view.startsWith("/")) {
			return view;
		}

		Box box = BoxContext.get();
		ServiceB serviceB = (ServiceB) box.get(Box.KEY_SERVICEB);

		if (view.endsWith("jsp")) {
			view = serviceB.classUrl + File.separatorChar + "jsp" + File.separatorChar + view;
		} else if (view.endsWith("html")) {
			view = serviceB.classUrl + File.separatorChar + "html" + File.separatorChar + view;
		}

		if (File.separatorChar == '\\') {
			view = view.replace("\\", "/");
		}

		return view;

	}

	/**
	 * @param request
	 * @param response
	 * @param box
	 * @param view
	 */
	private void forward(HttpServletRequest request, HttpServletResponse response, Box fwView) {

		try {

			String view = fwView.s("VIEW");
			String tmpl = fwView.s("TMPL");

			trace.write("view [" + view + "]");
			trace.write("tmpl [" + tmpl + "]");

			String dispatcherView = tmpl;
			if ("".equals(dispatcherView)) {
				dispatcherView = view;
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(dispatcherView);

			dispatcher.forward(request, response);

		} catch (Exception ex) {
			trace.write(ex);
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding(this.characterSet);

		Box box = new BoxHttp(request);
		BoxContext.set(box);

		Transaction transaction = Transaction.createTransaction(transationWas);
		TransactionContext.set(transaction);

		try {

			setBox(request, box);

			String serviceKey = box.s(Box.KEY_SERVICE);

			trace.write("serviceKey [" + serviceKey + "]");

			String jspKey = executeService(serviceKey);

			trace.write("jspKey [" + jspKey + "]");

			Box view = getView(jspKey);

			box.setXssConvert(true);

			forward(request, response, view);

			transaction.commit();

		} catch (Exception ex) {

			try {
				transaction.rollback();
			} catch (SQLException exx) {
				throw new ServletException(exx);
			}

			forwardException(ex, request, response, box);

		} finally {

			closeAOP();

		}

	}

	/**
	 * @param ex
	 * @param request
	 * @param response
	 * @param box
	 * @throws ServletException
	 */
	private void forwardException(Exception ex, HttpServletRequest request, HttpServletResponse response, Box box)
			throws ServletException {

		try {

			String errKey = "err";

			Box controller = box.getBox(Box.KEY_FW_CONTROLLER);
			String ERR_JSP_KEY = controller.s("ERR_JSP_KEY");
			if (!"".equals(ERR_JSP_KEY)) {
				errKey = ERR_JSP_KEY;
			}

			Box errView = getView(errKey);

			for (int i = 0; i < 10; i++) {

				if (!(ex instanceof InvocationTargetException)) {
					break;
				}
				ex = (Exception) ex.getCause();

			}

			box.put(Box.KEY_EXCEPTION, ex);
			forward(request, response, errView);
			return;

		} catch (Exception exx) {
			throw new ServletException(exx);
		}
	}

	/**
	 * @param KEY
	 * @return
	 * @throws Exception
	 */
	private static ServiceB getServiceB(String KEY) throws Exception {

		if (!IS_CACHE) {

			ServiceB serviceB = SERVICEB_MAP.get(KEY);
			if (serviceB != null) {
				return serviceB;
			}

		}

		MasterControllerD dao = new MasterControllerD();
		Box controller = dao.getController(KEY);

		String CLASS_NAME = controller.s("CLASS_NAME");
		String METHOD_NAME = controller.s("METHOD_NAME");

		trace.write("execute [" + CLASS_NAME + "][" + METHOD_NAME + "]");

		@SuppressWarnings("rawtypes")
		Class newClass = Class.forName(CLASS_NAME);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Constructor constructor = newClass.getConstructor();

		Object ins = constructor.newInstance();

		if (!(ins instanceof BizController)) {
			throw new AuthException();
		}

		BizController biz = (BizController) ins;

		String resource = CLASS_NAME.replaceAll("\\.", "\\/") + ".class";

		ClassLoader cl = MasterController.class.getClassLoader();
		String path = cl.getResource(resource).getPath();

		String classUrl = new File(path).getParent();

		if (!classUrl.startsWith(WEB_APP_DIR)) {
			throw new AuthException();
		}

		classUrl = classUrl.replace(WEB_APP_DIR, "");

		Method method = biz.getClass().getDeclaredMethod(METHOD_NAME);

		ServiceAnnotation sa = (ServiceAnnotation) method.getAnnotation(ServiceAnnotation.class);

		if (sa == null) {

			sa = new ServiceAnnotation() {

				@Override
				public Class<? extends Annotation> annotationType() {
					return ServiceAnnotation.class;
				}

				@Override
				public boolean isLogin() {
					return true;
				}

				@Override
				public String errJspKey() {
					return null;
				}

				@Override
				public String auth() {
					return "";
				}

				/**
				 *
				 */
				public boolean isInternal() {
					return false;
				}
			};

		}

		ServiceB serviceB = new ServiceB();

		serviceB.biz = biz;
		serviceB.method = method;
		serviceB.sa = sa;
		serviceB.classUrl = classUrl;

		SERVICEB_MAP.put(KEY, serviceB);

		return serviceB;

	}

	/**
	 * @param KEY
	 * @return
	 * @throws Exception
	 */
	public static String executeService(String KEY) throws Exception {

		ServiceB serviceB = getServiceB(KEY);

		Object biz = serviceB.biz;
		Method method = serviceB.method;
		ServiceAnnotation sa = serviceB.sa;

		Box box = BoxContext.get();
		box.put(Box.KEY_SERVICEB, serviceB);

		boolean checkUrlAuth = checkUrlAuth(sa);
		if (!checkUrlAuth) {

			SessionB user = box.getSessionB();

			if (!user.isLogin()) {
				throw new LoginException();
			}

			throw new AuthException();
		}

		if (biz instanceof BizControllerStartExecute) {
			String JSP_KEY = ((BizControllerStartExecute) biz).start();
			if (JSP_KEY != null) {
				return JSP_KEY;
			}
		}

		String jspKey = (String) method.invoke(biz);
		return jspKey;

	}

	/**
	 * @param sa
	 * @return
	 * @throws Exception
	 */
	private static boolean checkUrlAuth(ServiceAnnotation sa) throws Exception {

		Box box = BoxContext.get();
		SessionB user = box.getSessionB();

		boolean isLogin = sa.isLogin();
		String auth = sa.auth();

		if (sa.isInternal()) {
			String checkKey = box.s(Box.KEY_SERVICE);
			Box checkBox = box.getBox(Box.KEY_FW_CONTROLLER);
			String checkKey2 = checkBox.s("KEY");

			if (checkKey.equals(checkKey2)) {
				return false;
			}

		}

		if (isLogin) {
			if (!user.isLogin()) {
				return false;
			}
		}

		if ("".equals(auth)) {
			return true;
		}

		if (auth.indexOf(user.getAuth()) >= 0) {
			return true;
		}

		trace.write("checkUrlAuth false [" + auth + "][" + user.getAuth() + "]");

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
		ServiceB serviceB = getServiceB(KEY);
		ServiceAnnotation sa = serviceB.sa;
		return checkUrlAuth(sa);

	}

	/**
	 * @param request
	 * @return
	 */
	protected void setBox(HttpServletRequest request, Box box) throws Exception {

		String KEY = request.getPathInfo().substring(1);
		box.put(Box.KEY_SERVICE, KEY);

		String remoteAddr = request.getRemoteAddr();
		box.put(Box.KEY_REMOTE_ADDR, remoteAddr);

		Object session = request.getSession().getAttribute(Box.KEY_SESSIONB);

		if (session == null) {
			SessionB user = new SessionB(remoteAddr);
			box.setSessionB(user);
			return;
		}

		if (session instanceof SessionB) {
			box.put(Box.KEY_SESSIONB, session);
			return;
		}

	}

	/**
	 * 
	 */
	private String transationWas = InitYaml.get().s("TRANSACTION.WAS");

	@Override
	public void destroy() {
		TraceRunner.getTraceRunner().flush();
		super.destroy();
	}

	/**
	 * 
	 */
	protected void closeAOP() {
		TransactionContext.remove();
		BoxContext.get();
	}

	/**
	 *
	 */
	public void init() throws ServletException {
		reload();
		Admin.addReload(this);
	}

	/**
	 * 
	 */
	public void reload() {

		this.transationWas = InitYaml.get().s("TRANSACTION.WAS");

		SERVICEB_MAP.clear();
		FW_VIEW_MAP.clear();
	}

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, ServiceB> SERVICEB_MAP = new ConcurrentHashMap<String, ServiceB>();

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, Box> FW_VIEW_MAP = new ConcurrentHashMap<String, Box>();

	/**
	 * @param KEY
	 * @return
	 * @throws Exception
	 */
	private static Box getView(String KEY) throws Exception {

		if (IS_CACHE) {

			Box fwView = FW_VIEW_MAP.get(KEY);
			if (fwView != null) {
				return fwView;
			}
		}

		MasterControllerD dao = new MasterControllerD();
		Box fwView = dao.getView(KEY);

		String view = fwView.s("VIEW");
		String tmpl = fwView.s("TMPL");

		fwView.put("ORG_VIEW", view);
		fwView.put("ORG_TEMP", tmpl);

		view = convertUrlForView(view);
		tmpl = convertUrlForView(tmpl);

		fwView.put("VIEW", view);
		fwView.put("TMPL", tmpl);

		BoxContext.get().put(Box.KEY_FW_VIEW, fwView);

		FW_VIEW_MAP.put(KEY, fwView);

		return fwView;

	}
}
