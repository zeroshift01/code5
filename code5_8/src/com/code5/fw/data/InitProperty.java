package com.code5.fw.data;

import java.net.InetAddress;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.code5.fw.trace.TraceRunner;

/**
 * @author seuk
 * 
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
		init();

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
		init();
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
		init();
	}

	/**
	 * 
	 */
	private static void init() throws Exception {

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
		TRANSACTION_JOB = getString("TRANSACTION_JOB", resourceBundle);

		TraceRunner.getTraceRunner().init();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private static String getString(String key, ResourceBundle resourceBundle) throws Exception {

		String s = resourceBundle.getString(HOST + "." + CNTR + "." + key);
		if (s != null) {
			return s;
		}

		s = resourceBundle.getString(HOST + "." + key);
		if (s != null) {
			return s;
		}

		return resourceBundle.getString(key);
	}

	private static String TRANSACTION_WAS = null;
	private static String TRANSACTION_JOB = null;

	/**
	 * @return
	 */
	public static String TRANSACTION_WAS() {
		return TRANSACTION_WAS;
	}

	/**
	 * @return
	 */
	public static String TRANSACTION_JOB() {
		return TRANSACTION_JOB;
	}

}
