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
	private static String UPLOAD_FILE_URL = null;

	/**
	 * 
	 */
	private static String LOG_FILE_DIR_ROOT = null;

	/**
	 * 
	 */
	private static String IS_LOG = null;

	static {

		try {

			ResourceBundle resourceBundle = ResourceBundle.getBundle("init");

			DBMS_NAME_DEFAULT = resourceBundle.getString("DBMS_NAME_DEFAULT");

			DBMS_NAME_WAS = resourceBundle.getString("DBMS_NAME_WAS");

			LOG_FILE_DIR_ROOT = resourceBundle.getString("LOG_FILE_DIR_ROOT");

			IS_LOG = resourceBundle.getString("IS_LOG");

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
	public static String UPLOAD_FILE_URL() {
		return UPLOAD_FILE_URL;
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
	public static boolean IS_LOG() {

		if ("true".equals(IS_LOG)) {
			return true;
		}

		return false;
	}

}
