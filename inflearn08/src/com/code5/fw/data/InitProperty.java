package com.code5.fw.data;

import java.net.InetAddress;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.code5.fw.trace.TraceRunner;

/**
 * @author zero
 *
 */
public class InitProperty {

	/**
	 * 운영환경여부
	 */
	private static boolean IS_PRODUCT = false;

	/**
	 * 
	 */
	private static String LOG_DIR_PATTERN = null;
	private static String LOG_FILE_PATTERN = null;

	/**
	 * 
	 */
	private static boolean IS_WRITE_LOG = false;

	/**
	 * 
	 */
	private static String TRACE_CONFIG_URL = null;

	/**
	 * 
	 */
	private static String UPLOAD_FILE_DIR_TEMP_URL = null;

	/**
	 * 
	 */
	private static String UPLOAD_FILE_DIR_URL = null;

	static {

		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public static String TRACE_CONFIG_URL() {
		return TRACE_CONFIG_URL;
	}

	/**
	 * @return
	 */
	public static String LOG_FILE_PATTERN() {
		return LOG_FILE_PATTERN;
	}

	/**
	 * @return
	 */
	public static String LOG_DIR_PATTERN() {
		return LOG_DIR_PATTERN;
	}

	/**
	 * @return
	 */
	public static boolean IS_WRITE_LOG() {
		return IS_WRITE_LOG;
	}

	/**
	 * @return
	 */
	public static String UPLOAD_FILE_DIR_TEMP_URL() {
		return UPLOAD_FILE_DIR_TEMP_URL;
	}

	/**
	 * @return
	 */
	public static String UPLOAD_FILE_DIR_URL() {
		return UPLOAD_FILE_DIR_URL;
	}

	/**
	 * @return
	 */
	public static boolean IS_PRODUCT() {
		return IS_PRODUCT;
	}

	private static boolean IS_INIT = false;

	private static boolean IS_INIT_OK = false;

	private static boolean IS_MULTI = false;

	private static String CNTR = "";
	private static String HOST = "";

	/**
	 * @return
	 */
	public static boolean IS_MULTI() {
		return IS_MULTI;
	}

	public static String CNTR() {
		return CNTR;
	}

	public static String HOST() {
		return HOST;
	}

	/**
	 * @param request
	 */
	public static void init(HttpServletRequest request) throws Exception {

		if (IS_INIT) {
			return;
		}

		IS_INIT = true;

		IS_MULTI = true;
		CNTR = "p" + request.getServerPort();

		TraceRunner.getTraceRunner().init();
	}

	/**
	 * @param obj
	 * @throws Exception
	 */
	public static void init(Object obj) throws Exception {
		String cntr = obj.getClass().getName();
		init(cntr);

		TraceRunner.getTraceRunner().init();
	}

	/**
	 * @param obj
	 * @throws Exception
	 */
	public static void init(Class<?> cl) throws Exception {
		String cntr = cl.getName();
		init(cntr);

		TraceRunner.getTraceRunner().init();
	}

	/**
	 * @param cntr
	 */
	public static void init(String cntr) throws Exception {

		if (IS_INIT) {
			return;
		}

		IS_INIT = true;

		IS_MULTI = false;
		CNTR = cntr;

		TraceRunner.getTraceRunner().init();
	}

	/**
	 * @param cntr
	 */
	public static void init(String cntr, boolean isMulti) throws Exception {

		if (IS_INIT) {
			return;
		}

		IS_INIT = true;

		IS_MULTI = isMulti;
		CNTR = cntr;

		TraceRunner.getTraceRunner().init();
	}

	static {
		try {

			IS_INIT_OK = false;

			String hostName = InetAddress.getLocalHost().getHostName();
			if (hostName.contains(".")) {
				throw new Exception("error hostName [" + hostName + "]");
			}

			HOST = hostName;

			ResourceBundle resourceBundle = ResourceBundle.getBundle("init");

			TRACE_CONFIG_URL = getString("TRACE_CONFIG_URL", resourceBundle);

			LOG_DIR_PATTERN = getString("LOG_DIR_PATTERN", resourceBundle);
			LOG_FILE_PATTERN = getString("LOG_FILE_PATTERN", resourceBundle);

			String IS_WRITE_LOG_S = getString("IS_WRITE_LOG", resourceBundle);
			IS_WRITE_LOG = false;
			if ("true".equals(IS_WRITE_LOG_S)) {
				IS_WRITE_LOG = true;
			}

			UPLOAD_FILE_DIR_TEMP_URL = getString("UPLOAD_FILE_DIR_TEMP_URL", resourceBundle);
			UPLOAD_FILE_DIR_URL = getString("UPLOAD_FILE_DIR_URL", resourceBundle);

			String IS_PRODUCT_S = getString("IS_PRODUCT", resourceBundle);
			IS_PRODUCT = false;
			if ("true".equals(IS_PRODUCT_S)) {
				IS_PRODUCT = true;
			}

			TRANSACTION_WAS = getString("TRANSACTION_WAS", resourceBundle);
			TRANSACTION_DEFAULT = getString("TRANSACTION_DEFAULT", resourceBundle);

			IS_INIT_OK = true;

		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

	/**
	 * @param key
	 * @param resourceBundle
	 * @return
	 * @throws Exception
	 */
	private static String _getString(String key, ResourceBundle resourceBundle) throws Exception {
		if (!resourceBundle.containsKey(key)) {
			return null;
		}

		return resourceBundle.getString(key);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private static String getString(String key, ResourceBundle resourceBundle) throws Exception {

		String s = _getString(HOST + "." + CNTR + "." + key, resourceBundle);
		if (s != null) {
			return s;
		}

		s = _getString(HOST + "." + key, resourceBundle);
		if (s != null) {
			return s;
		}

		return _getString(key, resourceBundle);
	}

	private static String TRANSACTION_WAS = null;
	private static String TRANSACTION_DEFAULT = null;

	/**
	 * @return
	 */
	public static String TRANSACTION_WAS() {
		return TRANSACTION_WAS;
	}

	/**
	 * @return
	 */
	public static String TRANSACTION_DEFAULT() {
		return TRANSACTION_DEFAULT;
	}

	/**
	 * @return
	 */
	public static boolean IS_INIT_OK() {
		return IS_INIT_OK;
	}

}
