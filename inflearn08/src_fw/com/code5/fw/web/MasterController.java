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
import com.code5.fw.data.BoxLocal;
import com.code5.fw.data.InitYaml;
import com.code5.fw.data.MakeRnd;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;
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
	public String getRND() {
		return RND;
	}

	/**
	 * 
	 */
	private String RND = MakeRnd.createRnd(8);

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

		Box box = BoxContext.getThread();
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
		BoxContext.setThread(box);

		Transaction transaction = Transaction.createTransaction(transationWas);
		TransactionContext.setThread(transaction);

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
	 * @param hm
	 * @param KEY
	 * @return
	 */
	private static Object getCache(ConcurrentHashMap<String, ?> hm, String KEY) {

		if (!IS_CACHE) {
			return null;
		}
		return hm.get(KEY);
	}

	/**
	 * @param controller
	 * @return
	 * @throws Exception
	 */
	private static ServiceB createService(Box controller) throws Exception {

		String KEY = controller.s("KEY");
		String CLASS_NAME = controller.s("CLASS_NAME");
		String METHOD_NAME = controller.s("METHOD_NAME");

		trace.write("execute [" + CLASS_NAME + "][" + METHOD_NAME + "]");

		BizController biz = (BizController) getCache(BIZ_CONTROLLER_MAP, KEY);

		if (biz == null) {

			@SuppressWarnings("rawtypes")
			Class newClass = Class.forName(CLASS_NAME);

			@SuppressWarnings({ "rawtypes", "unchecked" })
			Constructor constructor = newClass.getConstructor();

			Object ins = constructor.newInstance();

			if (!(ins instanceof BizController)) {
				throw new AuthException();
			}

			biz = (BizController) ins;

			BIZ_CONTROLLER_MAP.put(KEY, (BizController) biz);

		}

		String classUrl = (String) getCache(CLASS_URL_MAP, KEY);

		if (classUrl == null) {

			String resource = CLASS_NAME.replaceAll("\\.", "\\/") + ".class";

			ClassLoader cl = MasterController.class.getClassLoader();
			String path = cl.getResource(resource).getPath();

			classUrl = new File(path).getParent();

			if (!classUrl.startsWith(WEB_APP_DIR)) {
				throw new AuthException();
			}

			classUrl = classUrl.replace(WEB_APP_DIR, "");

			CLASS_URL_MAP.put(KEY, classUrl);

		}

		Method method = (Method) getCache(METHOD_MAP, KEY + "." + METHOD_NAME);

		if (method == null) {

			method = biz.getClass().getDeclaredMethod(METHOD_NAME);

			METHOD_MAP.put(KEY + "." + METHOD_NAME, method);
		}

		ServiceAnnotation sa = (ServiceAnnotation) getCache(SERVICE_ANNOTATION_MAP, KEY + "." + METHOD_NAME);

		if (sa == null) {

			sa = (ServiceAnnotation) method.getAnnotation(ServiceAnnotation.class);

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

			SERVICE_ANNOTATION_MAP.put(KEY + "." + METHOD_NAME, sa);
		}

		ServiceB serviceB = new ServiceB();

		serviceB.biz = biz;
		serviceB.method = method;
		serviceB.sa = sa;
		serviceB.classUrl = classUrl;
		return serviceB;

	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String executeService(String key) throws Exception {

		Box controller = getController(key);

		ServiceB serviceB = createService(controller);

		Object biz = serviceB.biz;
		Method method = serviceB.method;
		ServiceAnnotation sa = serviceB.sa;

		Box box = BoxContext.getThread();
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

		Box box = BoxContext.getThread();
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

		Box controller = getController(KEY);
		ServiceB serviceB = createService(controller);
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
		TransactionContext.removeThread();
		BoxContext.removeThread();
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

		SERVICE_ANNOTATION_MAP.clear();
		METHOD_MAP.clear();
		BIZ_CONTROLLER_MAP.clear();
		CLASS_URL_MAP.clear();
		FW_VIEW_MAP.clear();

		this.RND = MakeRnd.createRnd(8);
	}

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, String> CLASS_URL_MAP = new ConcurrentHashMap<String, String>();

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, BizController> BIZ_CONTROLLER_MAP = new ConcurrentHashMap<String, BizController>();

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, Method> METHOD_MAP = new ConcurrentHashMap<String, Method>();

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, ServiceAnnotation> SERVICE_ANNOTATION_MAP = new ConcurrentHashMap<String, ServiceAnnotation>();

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, Box> FW_VIEW_MAP = new ConcurrentHashMap<String, Box>();

	/**
	 * 
	 */
	private static Sql SQL = new Sql(MasterController.class);

	/**
	 * @param KEY
	 * @return
	 * @throws Exception
	 */
	private static Box getController(String KEY) throws Exception {

		Box box = new BoxLocal();
		box.put("KEY", KEY);
		Table table = SQL.getTableByCache("MASTERCONTROLLER_01", box);
		if (table.size() != 1) {
			throw new Exception("controller [" + KEY + "] 를 확인해주세요.");

		}

		Box controller = table.getBox();
		BoxContext.getThread().put(Box.KEY_FW_CONTROLLER, controller);
		return controller;
	}

	/**
	 * @param KEY
	 * @return
	 * @throws Exception
	 */
	private static Box getView(String KEY) throws Exception {

		Box fwView = FW_VIEW_MAP.get(KEY);

		if (fwView != null) {
			return fwView;
		}

		Box box = new BoxLocal();
		box.put("KEY", KEY);

		Table table = SQL.getTableByCache("MASTERCONTROLLER_02", box);
		if (table.size() != 1) {
			throw new Exception("view [" + KEY + "] 를 확인해주세요.");

		}

		fwView = table.getBox();

		String view = fwView.s("VIEW");
		String tmpl = fwView.s("TMPL");

		fwView.put("ORG_VIEW", view);
		fwView.put("ORG_TEMP", tmpl);

		view = convertUrlForView(view);
		tmpl = convertUrlForView(tmpl);

		fwView.put("VIEW", view);
		fwView.put("TMPL", tmpl);

		BoxContext.getThread().put(Box.KEY_FW_VIEW, fwView);

		FW_VIEW_MAP.put(KEY, fwView);

		return fwView;

	}
}
