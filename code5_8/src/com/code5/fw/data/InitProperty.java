package com.code5.fw.data;

import java.util.ResourceBundle;

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

			ResourceBundle resourceBundle = ResourceBundle.getBundle("init");

			TRACE_CONFIG_URL = resourceBundle.getString("TRACE_CONFIG_URL");

			LOG_DIR_PATTERN = resourceBundle.getString("LOG_DIR_PATTERN");
			LOG_FILE_PATTERN = resourceBundle.getString("LOG_FILE_PATTERN");

			String IS_WRITE_LOG_S = resourceBundle.getString("IS_WRITE_LOG");
			IS_WRITE_LOG = false;
			if ("true".equals(IS_WRITE_LOG_S)) {
				IS_WRITE_LOG = true;
			}

			UPLOAD_FILE_DIR_TEMP_URL = resourceBundle.getString("UPLOAD_FILE_DIR_TEMP_URL");
			UPLOAD_FILE_DIR_URL = resourceBundle.getString("UPLOAD_FILE_DIR_URL");

			String IS_PRODUCT_S = resourceBundle.getString("IS_PRODUCT");
			IS_PRODUCT = false;
			if ("true".equals(IS_PRODUCT_S)) {
				IS_PRODUCT = true;
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

}
