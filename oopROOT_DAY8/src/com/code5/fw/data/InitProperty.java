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
	private static String DBMS_NAME = null;

	static {

		try {

			ResourceBundle resourceBundle = ResourceBundle.getBundle("init");

			DBMS_NAME = resourceBundle.getString("DBMS_NAME");

		} catch (Exception ex) {
			// [1]
			ex.printStackTrace();
		}

	}

	/**
	 * @return
	 */
	public static String DBMS_NAME() {
		return DBMS_NAME;
	}

}
