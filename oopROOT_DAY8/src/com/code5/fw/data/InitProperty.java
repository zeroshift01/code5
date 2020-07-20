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

	static {

		try {

			ResourceBundle resourceBundle = ResourceBundle.getBundle("init");

			DBMS_NAME_DEFAULT = resourceBundle.getString("DBMS_NAME_DEFAULT");

			DBMS_NAME_WAS = resourceBundle.getString("DBMS_NAME_WAS");

		} catch (Exception ex) {
			// [1]
			ex.printStackTrace();
		}

		DBMS_NAME_DEFAULT = "com.code5.fw.db.Transaction_SQLITE_JDBC";
		DBMS_NAME_WAS = "com.code5.fw.db.Transaction_SQLITE_JDBC";

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

}
