package com.code5.fw.data;

import java.util.ResourceBundle;

/**
 * @author seuk
 * 
 *
 */
public class InitProperty {

	/**
	 * 
	 */
	private static String DBMS_NAME_WAS = null;

	/**
	 * 
	 */
	private static String DBMS_NAME_DEFAULT = null;

	/**
	 * 
	 */
	private static String LOG_FILE_DIR_ROOT = null;

	/**
	 * 
	 */
	private static boolean IS_WRITE_LOG = false;

	/**
	 * 
	 */
	private static String TRACE_CONFIG_URL = null;

	static {

		try {

			ResourceBundle resourceBundle = ResourceBundle.getBundle("init");

			DBMS_NAME_DEFAULT = resourceBundle.getString("DBMS_NAME_DEFAULT");

			DBMS_NAME_WAS = resourceBundle.getString("DBMS_NAME_WAS");

			// TODO [1]
			TRACE_CONFIG_URL = resourceBundle.getString("TRACE_CONFIG_URL");

			// TODO [2]
			LOG_FILE_DIR_ROOT = resourceBundle.getString("LOG_FILE_DIR_ROOT");

			// TODO [3]
			String IS_WRITE_LOG_S = resourceBundle.getString("IS_WRITE_LOG");
			IS_WRITE_LOG = false;
			if ("true".equals(IS_WRITE_LOG_S)) {
				IS_WRITE_LOG = true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public static String DBMS_NAME_WAS() {
		return DBMS_NAME_WAS;
	}

	/**
	 * @return
	 */
	public static String DBMS_NAME_DEFAULT() {
		return DBMS_NAME_DEFAULT;
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
	public static String LOG_FILE_DIR_ROOT() {
		return LOG_FILE_DIR_ROOT;
	}

	/**
	 * @return
	 */
	public static boolean IS_WRITE_LOG() {
		return IS_WRITE_LOG;
	}

}
