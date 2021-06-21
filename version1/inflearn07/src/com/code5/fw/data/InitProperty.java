package com.code5.fw.data;

import java.util.ResourceBundle;

/**
 * @author zero
 *
 */
public class InitProperty {

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

	static {

		try {

			ResourceBundle resourceBundle = ResourceBundle.getBundle("init");

			TRACE_CONFIG_URL = resourceBundle.getString("TRACE_CONFIG_URL");

			LOG_DIR_PATTERN = resourceBundle.getString("LOG_DIR_PATTERN");
			LOG_FILE_PATTERN = resourceBundle.getString("LOG_FILE_PATTERN");

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

}
