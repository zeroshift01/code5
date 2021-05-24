package com.code5.fw.data;

import java.util.Map;

/**
 * @author zero
 *
 */
public class InitYaml {

	private static boolean IS_INIT = false;

	private static String WEB_APP_DIR = null;
	private static String BASE_DIR = null;
	private static int WEB_PORT = 0;

	/**
	 * @return
	 */
	public static String WEB_APP_DIR() {
		return WEB_APP_DIR;
	}

	/**
	 * @return
	 */
	public static String BASE_DIR() {
		return BASE_DIR;
	}

	/**
	 * @return
	 */
	public static int WEB_PORT() {
		return WEB_PORT;
	}

	/**
	 * @return
	 */
	public static boolean IS_INIT() {
		return IS_INIT;
	}

	static {

		try {

			Map<String, Object> map = YamlReader.getMap("init", "CODE5.WAS");

			WEB_APP_DIR = (String) map.get("WEB_APP_DIR");
			BASE_DIR = (String) map.get("BASE_DIR");
			WEB_PORT = (Integer) map.get("WEB_PORT");

			IS_INIT = true;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
