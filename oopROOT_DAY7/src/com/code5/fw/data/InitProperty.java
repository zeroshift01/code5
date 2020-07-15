package com.code5.fw.data;

import java.io.InputStream;
import java.util.Properties;

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

			Properties properties = new Properties();
			InputStream input = properties.getClass().getClassLoader().getResourceAsStream("init");
			properties.load(input);
			input.close();

			DBMS_NAME_DEFAULT = properties.getProperty("DBMS_NAME_DEFAULT");

			DBMS_NAME_WAS = properties.getProperty("DBMS_NAME_WAS");

		} catch (Exception ex) {
			// [1]
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

}
